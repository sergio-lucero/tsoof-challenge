package com.sooft.challenge.exception.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sooft.challenge.exception.AbstractChallengeException;
import com.sooft.challenge.exception.AppException;
import com.sooft.challenge.exception.utils.ExceptionUtil;
import jakarta.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  private static final Logger LOG = LoggerFactory.getLogger(RestExceptionHandler.class);
  private final Map<String, String> errorMap;

  public RestExceptionHandler(
      @Qualifier("errorMap") Map<String, String> errorMap
  ) {
    this.errorMap = errorMap;
  }

  private static void log(Throwable t, String errorCode, Map<String, String> errors) {
    if (LOG.isErrorEnabled()) {
      String causeExceptionName = null;
      String causeStackTrace = null;

      if (t.getCause() != null) {
        causeExceptionName = t.getCause().getClass().getName();
        causeStackTrace = ExceptionUtil.format(t.getCause());
      }

      LOG.error(
          "exceptionName={}, errorCode={}, errors={}, stackTrace={}, causeExceptionName={}, causeStackTrace={}",
          t.getClass().getName(), errorCode, errors, ExceptionUtil.format(t), causeExceptionName,
          causeStackTrace
      );
    }
  }

  @ExceptionHandler
  public ResponseEntity<Object> handleAbstractChallengeException(
      AbstractChallengeException ex, WebRequest request
  ) {

    String code = ex.getErrorEnum().getCode();
    Map<String, String> errors = ex.getErrors().isEmpty() ? null : ex.getErrors();
    Error error = new Error(code, errorMap.getOrDefault(code, ""), errors);

    return handleInternal(ex, error, ex.getHttpStatus(), request);
  }

  @ExceptionHandler
  public ResponseEntity<Object> handleAppException(AppException ex, WebRequest request) {
    String s = "exceptionName={}, errorCode={}, errors={}, stackTrace={}, causeExceptionName={}, causeStackTrace={}";

    String causeExceptionName = null;
    String causeStackTrace = null;

    if (ex.getCause() != null) {
      causeExceptionName = ex.getCause().getClass().getName();
      causeStackTrace = ExceptionUtil.format(ex.getCause());
    }

    if (ex.getLevel().equals(Level.ERROR)) {
      LOG.error(s, ex.getClass().getName(), ex.getErrorCode(), ex.getErrorMap(),
          ExceptionUtil.format(ex), causeExceptionName, causeStackTrace);
    } else if (ex.getLevel().equals(Level.WARN)) {
      LOG.warn(s, ex.getClass().getName(), ex.getErrorCode(), ex.getErrorMap(),
          ExceptionUtil.format(ex), causeExceptionName, causeStackTrace);
    } else if (ex.getLevel().equals(Level.INFO)) {
      LOG.info(s, ex.getClass().getName(), ex.getErrorCode(), ex.getErrorMap(),
          ExceptionUtil.format(ex), causeExceptionName, causeStackTrace);
    } else if (ex.getLevel().equals(Level.DEBUG)) {
      LOG.debug(s, ex.getClass().getName(), ex.getErrorCode(), ex.getErrorMap(),
          ExceptionUtil.format(ex), causeExceptionName, causeStackTrace);
    } else if (ex.getLevel().equals(Level.TRACE)) {
      LOG.trace(s, ex.getClass().getName(), ex.getErrorCode(), ex.getErrorMap(),
          ExceptionUtil.format(ex), causeExceptionName, causeStackTrace);
    }

    String code = ex.getErrorCode() == null ? "error.unknown" : ex.getErrorCode();
    String description = errorMap.getOrDefault(code, "");
    Map<String, String> errors = ex.getErrorMap() == null || ex.getErrorMap().isEmpty()
        ? null
        : ex.getErrorMap();

    Error error = new Error(code, description, errors);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    return handleExceptionInternal(ex, error, headers, ex.getHttpStatus(), request);
  }

  @ExceptionHandler
  public ResponseEntity<Object> handleRuntimeException(RuntimeException ex, WebRequest request) {
    log(ex, null, null);

    Error error = new Error("error.internal-server-error",
        "Temporary error in the service, we are working to solve it", null);

    return handleInternal(ex, error, HttpStatus.INTERNAL_SERVER_ERROR, request);
  }

  @ExceptionHandler
  public ResponseEntity<Object> handleConstraintViolationException(
      ConstraintViolationException ex, WebRequest request
  ) {
    Map<String, String> errors = new HashMap<>();
    ex.getConstraintViolations().forEach(
        v -> errors.put(v.getPropertyPath().toString(), v.getMessage()));

    log(ex, null, errors);

    Error error = new Error("error.parameter-invalid-value",
        "One or more of the parameters is invalid", errors);

    return handleInternal(ex, error, HttpStatus.BAD_REQUEST, request);
  }

  @ExceptionHandler
  public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
      MethodArgumentTypeMismatchException ex, WebRequest request
  ) {
    Map<String, String> errors = new HashMap<>();
    errors.put(
        ex.getName(),
        "Should be of type " + Objects.requireNonNull(ex.getRequiredType()).getName()
    );

    log(ex, null, errors);

    Error error = new Error("error.parameter-invalid-type", "", errors);

    return handleInternal(ex, error, HttpStatus.BAD_REQUEST, request);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status,
      WebRequest request
  ) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getFieldErrors()
        .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
    ex.getBindingResult().getAllErrors().forEach(error -> {
      if (!errors.containsValue(error.getDefaultMessage())) {
        errors.put("", error.getDefaultMessage());
      }
    });

    log(ex, null, errors);

    Error error = new Error("error.parameter-invalid-value",
        "One or more of the parameters is invalid", errors);

    return handleInternal(ex, error, HttpStatus.BAD_REQUEST, request);
  }

  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(
      MissingServletRequestParameterException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request
  ) {
    Map<String, String> errors = new HashMap<>();
    errors.put(ex.getParameterName(), "Parameter is missing");

    log(ex, null, errors);

    Error error = new Error("error.parameter-missing", "Parameter missing", errors);

    return handleInternal(ex, error, status, request);
  }

  private ResponseEntity<Object> handleInternal(
      Exception ex, Error error, HttpStatusCode status, WebRequest request
  ) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    return handleExceptionInternal(ex, error, headers, status, request);
  }

  public static class Error {

    private final String code;
    private final String description;
    @JsonInclude(Include.NON_NULL)
    private final Map<String, String> errors;

    public Error(String code, String description, Map<String, String> errors) {
      this.code = code;
      this.description = description;
      this.errors = errors;
    }

    public String getCode() {
      return code;
    }

    public String getDescription() {
      return description;
    }

    public Map<String, String> getErrors() {
      return errors;
    }
  }
}
