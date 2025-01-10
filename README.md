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