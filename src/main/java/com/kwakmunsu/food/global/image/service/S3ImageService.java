package com.kwakmunsu.food.global.image.service;


import com.kwakmunsu.food.global.exception.FoodAwsS3Exception;
import com.kwakmunsu.food.global.response.FoodErrorCode;
import java.io.IOException;
import java.util.*;
import software.amazon.awssdk.core.sync.RequestBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Service
public class S3ImageService {

    private final S3Client s3Client;
    private final String bucket;
    private final String imageBaseUri;

    private static final List<String> WHITE_LIST = List.of("jpg", "jpeg", "png", "webp", "heic");
    public S3ImageService(
            S3Client s3Client,
            @Value("${cloud.aws.s3.bucketName}") String bucket,
            @Value("${cloud.aws.s3.image-base-uri}") String imageBaseUri
    ) {
        this.s3Client = s3Client;
        this.bucket = bucket;
        this.imageBaseUri = imageBaseUri;
    }
    // 다중 이미지 업로드
    public List<String> uploadImages(List<MultipartFile> files) {
        List<String> images = new ArrayList<>();
        for (MultipartFile file : files) {
            images.add(uploadImage(file));
        }
        return images;
    }

    public String uploadImage(MultipartFile file) {
        validate(file);

        String fileName = createFileName(file);
        this.uploadImageToS3(file, fileName, s3Client);
        String fileUrl = imageBaseUri + fileName;
        return fileUrl;
    }

    public void deleteImage(String imageUrl) {
        String fileName = extractFileNameFromUrl(imageUrl);
        try {

            // 삭제할 객체의 키(파일 이름) 지정
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucket)  // S3 버킷 이름
                    .key(fileName)   // 삭제할 파일의 S3 객체 키 (파일 이름)
                    .build();
            s3Client.deleteObject(deleteObjectRequest);
        } catch (S3Exception e) {
            throw new FoodAwsS3Exception(FoodErrorCode.AWS_S3_DELETE_ERROR,e.getMessage());
        } catch (Exception ex) {
            throw new FoodAwsS3Exception(FoodErrorCode.AWS_S3_ERROR,ex.getMessage());
        }
    }

    private void uploadImageToS3(MultipartFile file, String fileName, S3Client s3Client) {
        try {
            RequestBody requestBody = RequestBody.fromBytes(file.getBytes());
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(fileName)
                    .contentType(file.getContentType()) // file 타입
                    .contentLength(file.getSize()) // file 사이즈
                    .build();

            s3Client.putObject(putObjectRequest, requestBody);
        } catch (IOException exception) {
            throw new FoodAwsS3Exception(FoodErrorCode.AWS_S3_ERROR, "S3에 이미지를 업로드하다 오류가 발생했습니다.");
        }
    }

//    private void validateImageFileExtention(String filename) {
//        int lastDotIndex = filename.lastIndexOf(".");
//        if (lastDotIndex == -1) {
//            throw new FoodAwsS3Exception(FoodErrorCode.AWS_S3_ERROR);
//        }
//
//        String extention = filename.substring(lastDotIndex + 1).toLowerCase();
//        List<String> allowedExtentionList = Arrays.asList("jpg", "jpeg", "png", "gif");
//
//        if (!allowedExtentionList.contains(extention)) {
//            throw new FoodAwsS3Exception(FoodErrorCode.AWS_S3_TYPE_ERROR);
//        }
//    }


    // 이미지 파일 이름 중복 예방으로 UUID 사용.
    private String createFileName(MultipartFile file) {
        return UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
    }

    // URL에서 버킷 베이스 URL을 제거하고 파일 키를 반환
    private String extractFileNameFromUrl(String imageUrl) {
        return imageUrl.replace(imageBaseUri, "");
    }




    private void validate(MultipartFile file) {
        validateNotNull(file);
        String fileName = file.getOriginalFilename();
        validateFileNameNotBlank(fileName);
        validateExtension(fileName);
    }

    private void validateNotNull(MultipartFile file) {
        if (file == null) {
            throw new FoodAwsS3Exception(FoodErrorCode.AWS_S3_ERROR,"파일을 전달 받지 못했습니다");
        }
    }

    private void validateFileNameNotBlank(String fileName) {
        if (fileName == null || fileName.isBlank()) {
            throw new FoodAwsS3Exception(FoodErrorCode.AWS_S3_ERROR,"파일 이름은 비어있을 수 없습니다");
        }
    }

    public void validateExtension(String fileName) {
        int extensionIndex = fileName.lastIndexOf(".");
        if (extensionIndex == -1 || fileName.endsWith(".")) {
            throw new FoodAwsS3Exception(FoodErrorCode.AWS_S3_TYPE_ERROR,"파일 형식이 잘못되었습니다");
        }
        String extension = fileName.substring(extensionIndex + 1);
        if (!WHITE_LIST.contains(extension.toLowerCase())) {
            throw new  FoodAwsS3Exception(FoodErrorCode.AWS_S3_TYPE_ERROR, "지원하지 않는 확장자입니다: " + extension);
        }
    }
}
