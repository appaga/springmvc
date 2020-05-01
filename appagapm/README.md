# Getting Started
[Spring boot 2.2.6][springboot]을 이용한 웹 어플리케이션 기본 프로젝트로 만든 것이다.
소규모 프로젝트 또는 소규모 조직의 자원을 관리하는 어플리케이션을 만들고자 한다.

[Thymeleaf][thymeleaf]를 템플릿엔진으로 사용하였고, [W3.CSS][w3css]를 이용하여 html 화면 디자인을 처리한다.

[Thymeleaf Layout Dialect][thymeleaflayoutdialect]에서 제공하는 `nz.net.ultraq.thymeleaf` 라이브러리를 추가하여 레이아웃 설정을 추가하였다.
다음 디펜던시가 추가되야 한다.

```xml
<dependency>
  <groupId>nz.net.ultraq.thymeleaf</groupId>
  <artifactId>thymeleaf-layout-dialect</artifactId>
</dependency>
```

### Reference Documentation
For further reference, please consider the following sections:

* [Thymeleaf][thymeleaf]
* [W3.CSS][w3css]
* [Thymeleaf Layout Dialect][thymeleaflayoutdialect]
* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.2.6.RELEASE/maven-plugin/)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.2.6.RELEASE/reference/htmlsingle/#using-boot-devtools)
* [Spring Configuration Processor](https://docs.spring.io/spring-boot/docs/2.2.6.RELEASE/reference/htmlsingle/#configuration-metadata-annotation-processor)
* [Spring Web 2.2.6](https://docs.spring.io/spring-boot/docs/2.2.6.RELEASE/reference/htmlsingle/#boot-features-developing-web-applications)

### Guides
The following guides illustrate how to use some features concretely:

* [Handling Form Submission](https://spring.io/guides/gs/handling-form-submission/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)

[springboot]: https://docs.spring.io/spring-boot/docs/2.2.6.RELEASE/reference/htmlsingle/#boot-features-developing-web-applications "Spring Web 2.2.6"
[thymeleaf]: https://docs.spring.io/spring-boot/docs/2.2.6.RELEASE/reference/htmlsingle/#boot-features-spring-mvc-template-engines "Thymeleaf"
[thymeleaflayoutdialect]: https://github.com/ultraq/thymeleaf-layout-dialect "Thymeleaf Layout Dialect"
[w3css]: https://www.w3schools.com/w3css/default.asp "W3.CSS"