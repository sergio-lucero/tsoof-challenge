package com.sooft.challenge.exception;

import com.sooft.challenge.exception.enums.ErrorEnum;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.springframework.http.HttpStatus;

public abstract class AbstractChallengeException extends RuntimeException implements ChallengeException {

  private final ErrorEnum errorEnum;
  protected HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
  private final Map<String, String> errors;

  protected AbstractChallengeException(ErrorEnum errorEnum) {
    this.errorEnum = errorEnum;
    this.errors = new HashMap<>();
  }

  protected AbstractChallengeException(ErrorEnum errorEnum, Map<String, String> errors) {
    this.errorEnum = errorEnum;
    this.errors = Objects.isNull(errors) ? new HashMap<>() : errors;
  }

  protected AbstractChallengeException(ErrorEnum errorEnum, Throwable cause) {
    super(cause);
    this.errorEnum = errorEnum;
    this.errors = new HashMap<>();
  }

  public ErrorEnum getErrorEnum() {
    return errorEnum;
  }

  public Map<String, String> getErrors() {
    return errors;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }
}
