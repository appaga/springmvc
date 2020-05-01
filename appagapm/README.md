# Getting Started
[Spring boot 2.2.6][springboot]을 이용한 웹 어플리케이션 기본 프로젝트로 만든 것이다.
소규모 프로젝트 또는 소규모 조직의 자원을 관리하는 어플리케이션을 만들고자 한다.

## html/css 디자인 자원
[W3.CSS][w3css]를 이용하여 html 화면 디자인을 처리한다.

폰트는 네이버에서 제공하는 [나눔스퀘어라운드][naverfont]를 사용한다.
TTF 폰트를 다운받아서 [Online Font Convenverter][fontconverter]에서 `EOT`, `WOFF`, `WOFF2` 등으로 변환하여 사용하였다.
```css
@font-face {
  font-family: "NanumSquareRoundR";
  src: url("./fonts/NanumSquareRoundR.eot"); /* IE9 Compat Modes */
  src: url("./fonts/NanumSquareRoundR.eot?#iefix") format("embedded-opentype"), /* IE6-IE8 */
    url("./fonts/NanumSquareRoundR.otf") format("opentype"), /* Open Type Font */
    url("./fonts/NanumSquareRoundR.svg") format("svg"), /* Legacy iOS */
    url("./fonts/NanumSquareRoundR.ttf") format("truetype"), /* Safari, Android, iOS */
    url("./fonts/NanumSquareRoundR.woff") format("woff"), /* Modern Browsers */
    url("./fonts/NanumSquareRoundR.woff2") format("woff2"); /* Modern Browsers */
  font-weight: normal;
  font-style: normal;
}
body {font-family: "NanumSquareRoundR", sans-serif}
```

## Thymeleaf 템플릿엔진 사용
[Thymeleaf][thymeleaf]를 템플릿엔진으로 사용한다.

[Thymeleaf Layout Dialect][thymeleaflayoutdialect]에서 제공하는 `nz.net.ultraq.thymeleaf` 라이브러리를 추가하여 레이아웃 설정을 추가하였다.
다음 디펜던시가 추가되야 한다.

```xml
<dependency>
  <groupId>nz.net.ultraq.thymeleaf</groupId>
  <artifactId>thymeleaf-layout-dialect</artifactId>
</dependency>
```

```java
@Slf4j
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	/**
	 * 타임리프 속성 생성
	 * 
	 * @return
	 */
	@Bean
	public SpringResourceTemplateResolver templateResolver() {
		SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
		templateResolver.setPrefix("classpath:templates/");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode(TemplateMode.HTML);
		templateResolver.setCharacterEncoding("UTF-8");
		// TODO: Template cache is true by default. Set to false if you want
		// templates to be automatically updated when modified.
		templateResolver.setCacheable(false);
		return templateResolver;
	}

	/**
	 * 타임리프 dialect 추가
	 * 
	 * @return
	 */
	@Bean
	public SpringTemplateEngine templateEngine() {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setEnableSpringELCompiler(true); // Compiled SpringEL should speed up executions
		templateEngine.setTemplateResolver(templateResolver());
		templateEngine.addDialect(new LayoutDialect()); // nz.net.ultraq.thymeleaf 레이아웃 관리 추가
		return templateEngine;
	}

	@Bean
	public ThymeleafViewResolver viewResolver() {
		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		viewResolver.setTemplateEngine(templateEngine());
		viewResolver.setCharacterEncoding("UTF-8");
		viewResolver.setOrder(0);
		return viewResolver;
	}

}
```

### Reference Documentation
For further reference, please consider the following sections:

* [Spring Web 2.2.6][springboot]
* [Thymeleaf][thymeleaf]
* [W3.CSS][w3css]
* [Thymeleaf Layout Dialect][thymeleaflayoutdialect]
* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.2.6.RELEASE/maven-plugin/)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.2.6.RELEASE/reference/htmlsingle/#using-boot-devtools)
* [Spring Configuration Processor](https://docs.spring.io/spring-boot/docs/2.2.6.RELEASE/reference/htmlsingle/#configuration-metadata-annotation-processor)

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
[naverfont]: https://hangeul.naver.com/2017/nanum "네이버 한글한글아름답게"
[fontconverter]: https://www.font-converter.net "Online Font Converter / Web Font Generator"