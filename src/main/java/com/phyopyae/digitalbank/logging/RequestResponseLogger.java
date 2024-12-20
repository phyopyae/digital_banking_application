package com.phyopyae.digitalbank.logging;

import java.util.Arrays;
import java.util.Objects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Aspect
@Component
public class RequestResponseLogger {

	private static final Logger logger = LoggerFactory.getLogger(RequestResponseLogger.class);
	private final ObjectMapper objectMapper = new ObjectMapper();
	
	@Around("@annotation(org.springframework.web.bind.annotation.PostMapping) || "
			+ "@annotation(org.springframework.web.bind.annotation.GetMapping) || "
			+ "@annotation(org.springframework.web.bind.annotation.DeleteMapping) ")
	public Object logHttpRequestResponseC(ProceedingJoinPoint joinPoint) throws Throwable {
		logger.info("Request: " + joinPoint.getSignature());

		HttpServletRequest request = ((ServletRequestAttributes) Objects
				.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
		HttpServletResponse httpResponse = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getResponse();

		String requestBody = getRequestBody(joinPoint);
		logger.info("Request: Method: {}, URI: {}, Body: {}", request.getMethod(), request.getRequestURI(),
				requestBody);

		Object response = null;
		long startTime = System.currentTimeMillis();
		long endTime;
		try {
			response = joinPoint.proceed();
			endTime = System.currentTimeMillis();
		} catch (Throwable exceptions) {
			logger.error("Exception: Method: {}, URI: {} failed with exception message: {}", request.getMethod(),
					request.getRequestURI(), exceptions.getMessage());
			throw exceptions;
		}

		long duration = endTime - startTime;
		String responseBody = convertObjectToJson(response);
		logger.info("Response: Method: {}, URI: {}, Status {} - Body: {}, Time Taken: {} ms", request.getMethod(),
				request.getRequestURI(), httpResponse != null ? httpResponse.getStatus() : 0, responseBody, duration);
		logger.info("Response: " + response);
		return response;
	}

	private String getRequestBody(ProceedingJoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		if (args.length > 0) {
			try {
				return Arrays.stream(args)
						.map(this::convertObjectToJson)
						.reduce((arg1, arg2) -> arg1 + ", " + arg2)
						.orElse("");
			} catch (Exception exception) {
				logger.error("Error serializing request body", exception);
			}
		}
		return "";
	}

	private String convertObjectToJson(Object object) {
		if (object == null)
			return "";
		try {
			return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
		} catch (JsonProcessingException exception) {
			logger.error("Error serializing object to JSON", exception);
			return "Error serializing object to JSON";
		}
	}
}
