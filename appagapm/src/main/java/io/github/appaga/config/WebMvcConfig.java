package io.github.appaga.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.navercorp.lucy.security.xss.servletfilter.XssEscapeServletFilter;

import lombok.extern.slf4j.Slf4j;
import nz.net.ultraq.thymeleaf.LayoutDialect;

@Slf4j
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Autowired
	private SysProperty sysProperty;
	
	@Autowired
	private Environment environment;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		if(log.isDebugEnabled()) {
			log.debug("environment={}", environment);
		}
		// 프로파일을 읽어와서 서버 모드를 설정한다.
		// 인터셉터에서 sysProperty를 사용해야 함으로 인터셉터 추가 전에 설정한다.
		sysProperty.setServerMode(SysProperty.SERVER_MODE_LOCAL);
		String[] profiles = environment.getActiveProfiles();
		for (String profile : profiles) {
			switch (profile.toUpperCase()) {
			case SysProperty.SERVER_MODE_PRD:
				sysProperty.setServerMode(SysProperty.SERVER_MODE_PRD);
				break;
			case SysProperty.SERVER_MODE_STG:
				sysProperty.setServerMode(SysProperty.SERVER_MODE_STG);
				break;
			case SysProperty.SERVER_MODE_DEV:
				sysProperty.setServerMode(SysProperty.SERVER_MODE_DEV);
				break;
			}
		}
		WebMvcConfigurer.super.addInterceptors(registry);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/static/assets/");
		registry.addResourceHandler("/html/**").addResourceLocations("classpath:/static/html/");
		registry.addResourceHandler("/favicon.ico").addResourceLocations("classpath:/static/favicon.ico");
		WebMvcConfigurer.super.addResourceHandlers(registry);
	}

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
		templateEngine.addDialect(new LayoutDialect()); // 레이아웃 관리(기본)
		//templateEngine.addDialect(new SpringSecurityDialect()); // 스프링 시큐리티 dialect
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

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(escapingConverter());

	}

	/**
	 * request body xss 적용
	 * 
	 * @return
	 */
	@Bean
	public HttpMessageConverter<?> escapingConverter() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.getFactory().setCharacterEscapes(new HTMLCharacterEscapes());

		MappingJackson2HttpMessageConverter escapingConverter = new MappingJackson2HttpMessageConverter();
		escapingConverter.setObjectMapper(objectMapper);

		return escapingConverter;
	}

	/**
	 * XSS 필터 적용
	 * 
	 * @return
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
