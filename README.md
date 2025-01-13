# 트러블 슈팅 1
- 스프링 부트 3.4.x 버전 사용하는데 Swagger가 500 에러를 던짐.
   - 기존 swagger 버전은 2.4.0이였음 
  ~~~ java
   java.lang.NoSuchMethodError: void org.springframework.web.method.ControllerAdviceBean.<init>(java.lang.Object)']
  ~~~
  - 해당 오류를 던졌다. 그래서 찾아보니 ExceptionHandler 쪽의 @RestControllerAdvice가 문제였다.
  - 정확한 이유는 모르겠으나 @RestControllerAdvice를 주석 처리하니 swagger가 작동됨.
  - 하지만 이러면 예외처리를 하지 못한다. 따라서 다른 방법을 살펴보니다가 swagger 버전을 2.7.0으로 올리니 해결 완료
## 결론: 3.4.x 이상 버전에서는 
~~~
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.7.0")
~~~
를 사용하자

# 배운 점
### application.properties
- application.properties에는 민감한 정보가 노출 되면 안된다. 따라서 application-private.properties
를 만들고 민감한 정보는 해당 파일에 저장 후 .gitignore에 추가함.
- 그리고application.properties에
~~~
spring.profiles.include=private
~~~
를 추가하여서 사용하면 application-private.properties의 정보도 사용 가능하다.
### AWS 관련 라이브러리 

#### spring-cloud-starter-aws
- 다양한 AWS 서비스 지원
- S3 뿐만 아닌 다른 AWS 서비스 통합 여러 기능 포함
#### aws-java-sdk-s3 (해당 프로젝트는 이 라이브러리 사용.)
- AWS SDK for Java의 S3 전용 클라이언트
-	S3와 관련된 작업을 수행하기 위한 가장 기본적인 라이브러리
-	S3에 특화된 기능 제공, 다른 AWS 서비스 통합 기능 포함x

# 트러블 슈팅
- S3 Transaction을 어떻게 해결할 것인가.
- 이미지 업로드 중 DB에 저장 또는 삭제 후 S3에 업로드나 삭제하는데 실패할 경우(반대 경우)에 해결방안은?
- 찾아보자..