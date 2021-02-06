package com.ryc.store.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@ControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	private final String CONTENT_TYPE = "Content-Type";

	@ExceptionHandler(value = BusinessServiceException.class)
	public ResponseEntity<Object> processBusinessErrorException(BusinessServiceException exception, WebRequest webRequest){
		ErrorApi errorApi = new ErrorApi();
		errorApi.setMessage(exception.getMessage());
		errorApi.setCodeError(exception.getErrorType().getCodeError());

		String json = "";
		ObjectMapper mapperFromObjectToJson = new ObjectMapper();

		try {
			json = mapperFromObjectToJson.writeValueAsString(errorApi);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return handleExceptionInternal(exception, json, getHttpHeaders(), HttpStatus.valueOf(exception.getErrorType().getStatus()), webRequest);
	}

	@ExceptionHandler(value = { Exception.class })
	public ResponseEntity<Object> processValidationErrorGeneric(RuntimeException exception, WebRequest request) {
		logger.error(exception.toString());
		ErrorApi errorApi = new ErrorApi();
		errorApi.setMessage("Internal error");
		errorApi.setCodeError(ErrorType.SERVER_ERROR.getCodeError());
		errorApi.setDetails(new ArrayList<>());
		errorApi.getDetails().add(exception.toString());
		String json = "";
		ObjectMapper mapperFromObjectToJson = new ObjectMapper();

		try {
			json = mapperFromObjectToJson.writeValueAsString(errorApi);
		} catch (JsonProcessingException e) {
			logger.error(e.toString());
		}
		return handleExceptionInternal(exception, json, getHttpHeaders(), HttpStatus.valueOf(ErrorType.SERVER_ERROR.getStatus()), request);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ErrorApi errorApi = new ErrorApi();
		errorApi.setMessage("Internal error");
		errorApi.setCodeError(ErrorType.SERVER_ERROR.getCodeError());
		errorApi.setDetails(new ArrayList<>());
		errorApi.getDetails().add(Objects.nonNull(exception.getCause()) ? exception.getCause().toString() : "");

		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		String json = null;
		try {
			json = mapper.writeValueAsString(errorApi);
		} catch (JsonProcessingException e) {
			return handleExceptionInternal(exception, null, headers, HttpStatus.BAD_REQUEST, request);
		}

		return handleExceptionInternal(exception, json, getHttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ErrorApi errorApi = new ErrorApi();
		errorApi.setMessage("Internal error");
		errorApi.setCodeError(ErrorType.SERVER_ERROR.getCodeError());
		errorApi.setDetails(new ArrayList<>());
		errorApi.getDetails().add(exception.getCause().toString());

		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		String json;
		try {
			json = mapper.writeValueAsString(errorApi);
		} catch (JsonProcessingException e) {
			return handleExceptionInternal(exception, null, headers, HttpStatus.BAD_REQUEST, request);
		}

		return handleExceptionInternal(exception, json, getHttpHeaders(),  HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ErrorApi errorApi = new ErrorApi();
		errorApi.setMessage("Internal error");
		errorApi.setCodeError(ErrorType.BUSINESS_ERROR.getCodeError());
		List<String> details = new ArrayList<>();
		for(ObjectError error : ex.getBindingResult().getAllErrors()) {
			details.add(error.getDefaultMessage());
		}
		errorApi.setDetails(details);
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		String json;
		try {
			json = mapper.writeValueAsString(errorApi);
		} catch (JsonProcessingException e) {
			return handleExceptionInternal(e, null, headers, HttpStatus.BAD_REQUEST, request);
		}
		return handleExceptionInternal(ex, json, getHttpHeaders(),  HttpStatus.BAD_REQUEST, request);
	}

	private HttpHeaders getHttpHeaders() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		return httpHeaders;
	}
}
