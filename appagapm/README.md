# Getting Started
[Spring boot 2.2.6][springboot]을 이용한 웹 어플리케이션 기본 프로젝트로 만든 것이다.
소규모 프로젝트 또는 소규모 조직의 자원을 관리하는 어플리케이션을 만들고자 한다.

## html/css, font 등 디자인 자원
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

MVC 설정에 타임리프 템플릿엔진 설정하는 부분에 `templateEngine.addDialect(new LayoutDialect());`로 layout-dialect를 추가해줘야 한다.
```java
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	/**
	 * 타임리프 속성 생성
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

## XSS 공격 방어 필터 적용
XSS(Cross-site-scripting) 공격에 대처하기 위하여 get/post 파라미터 및 request body에 필터를 적용한다.

### GET/POST 파라미터에 XSS 공격 방어 필터 적용
많이 사용되는 [네이버 lucy-xss-servlet-filter][lucyxssservletfilter] 라이브러리를 사용한다.
아래 디펜던시가 추가한다.
```xml
<dependency>
	<groupId>com.navercorp.lucy</groupId>
	<artifactId>lucy-xss-servlet</artifactId>
	<version>2.0.0</version>
</dependency>
```

MVC 설정에 필터를 등록한다.
```java
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	/**
	 * XSS 필터 적용
	 */
	@Bean
	public FilterRegistrationBean<XssEscapeServletFilter> getXssEscapeServletFilterRegistrationBean() {
		FilterRegistrationBean<XssEscapeServletFilter> registrationBean = new FilterRegistrationBean<XssEscapeServletFilter>();
		registrationBean.setFilter(new XssEscapeServletFilter());
		registrationBean.setOrder(1);
		registrationBean.addUrlPatterns("/*"); // filter를 거칠 url patterns
		return registrationBean;
	}

}
```

### request body에 XSS 공격 방어 필터 적용
Ajax나 restful api 시에 사용되는 request body에는 lucy 필터가 적용되지 않는다고 한다.
request body에 XSS 공격 방어 필터도 같이 적용한다.
필터링을 담당하는 클래스 `HTMLCharacterEscapes`를 생성하고: 
```java
public class HTMLCharacterEscapes extends CharacterEscapes {
	private final int[] asciiEscapes;

	private final CharSequenceTranslator translator;

	public HTMLCharacterEscapes() {

		Map<CharSequence, CharSequence> customMap = new HashMap<>();
		customMap.put("(", "&#40;");
		Map<CharSequence, CharSequence> CUSTOM_ESCAPE = Collections.unmodifiableMap(customMap);

		// XSS 방지 처리할 특수 문자 지정
		asciiEscapes = CharacterEscapes.standardAsciiEscapesForJSON();
		asciiEscapes['<'] = CharacterEscapes.ESCAPE_CUSTOM;
		asciiEscapes['>'] = CharacterEscapes.ESCAPE_CUSTOM;
		asciiEscapes['&'] = CharacterEscapes.ESCAPE_CUSTOM;
		asciiEscapes['('] = CharacterEscapes.ESCAPE_CUSTOM;

		// XSS 방지 처리 특수 문자 인코딩 값 지정
		translator = new AggregateTranslator(new LookupTranslator(EntityArrays.BASIC_ESCAPE), // <, >, &, " 는 여기에 포함됨
				new LookupTranslator(EntityArrays.ISO8859_1_ESCAPE),
				new LookupTranslator(EntityArrays.HTML40_EXTENDED_ESCAPE), new LookupTranslator(CUSTOM_ESCAPE));

	}

	@Override
	public int[] getEscapeCodesForAscii() {
		return asciiEscapes;
	}

	@Override
	public SerializableString getEscapeSequence(int ch) {
		return new SerializedString(translator.translate(Character.toString((char) ch)));
	}

}
```

MVC 설정에 `Jackson` 컨버터에 등록해준다.
```java
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		// request body xss 추가
		converters.add(escapingConverter());

	}

	/**
	 * request body xss 적용
	 */
	@Bean
	public HttpMessageConverter<?> escapingConverter() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.getFactory().setCharacterEscapes(new HTMLCharacterEscapes());

		MappingJackson2HttpMessageConverter escapingConverter = new MappingJackson2HttpMessageConverter();
		escapingConverter.setObjectMapper(objectMapper);

		return escapingConverter;
	}

}
```

## Reference Documentation
For further reference, please consider the following sections:

* [Spring Web 2.2.6][springboot]
* [Thymeleaf][thymeleaf]
* [W3.CSS][w3css]
* [Thymeleaf Layout Dialect][thymeleaflayoutdialect]
* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.2.6.RELEASE/maven-plugin/)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.2.6.RELEASE/reference/htmlsingle/#using-boot-devtools)
* [Spring Configuration Processor](https://docs.spring.io/spring-boot/docs/2.2.6.RELEASE/reference/htmlsingle/#configuration-metadata-annotation-processor)

## Guides
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
[lucyxssservletfilter]: https://github.com/naver/lucy-xss-servlet-filter "lucy-xss-servlet-filter"