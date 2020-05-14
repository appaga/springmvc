package io.github.appaga.base;

import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BaseErrorController extends BasicErrorController {

	public BaseErrorController(ErrorAttributes errorAttributes) {
		super(errorAttributes, new ErrorProperties(), Collections.emptyList());
	}

	@RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
	@Override
	public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = super.errorHtml(request, response);
		if (log.isDebugEnabled()) {
			log.debug("model={}", mv.getModel());
		}

		mv.setViewName("error/error");
		return mv;
	}

	@RequestMapping
	@Override
	public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
		ResponseEntity<Map<String, Object>> result = super.error(request);
		if (log.isDebugEnabled()) {
			log.debug("result={}", result.getBody());
		}
		return result;
	}

	@ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
	@Override
	public ResponseEntity<String> mediaTypeNotAcceptable(HttpServletRequest request) {
		ResponseEntity<String> result = super.mediaTypeNotAcceptable(request);
		if (log.isDebugEnabled()) {
			log.debug("result={}", result.getBody());
		}
		return result;
	}

}
