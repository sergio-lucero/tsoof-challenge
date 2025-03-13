package com.sooft.challenge.exception;

import java.util.Map;
import org.slf4j.event.Level;
import org.springframework.http.HttpStatus;

public class AppException extends RuntimeException {

  private final HttpStatus httpStatus;
  private final Level level;
  private final String errorCode;
  private final Map<String, String> errorMap;

  public AppException(HttpStatus httpStatus, Level level, String errorCode,
      Map<String, String> errorMap, Throwable cause) {
    super(cause);
    this.httpStatus = httpStatus;
    this.level = level;
    this.errorCode = errorCode;
    this.errorMap = errorMap;
  }

  public static Builder builder() {
    return new Builder();
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

  public Level getLevel() {
    return level;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public Map<String, String> getErrorMap() {
    return errorMap;
  }

  @Override
  public String toString() {
    return "AppException{" +
        "httpStatus=" + httpStatus +
        ", level=" + level +
        ", errorCode='" + errorCode + '\'' +
        ", errorMap=" + errorMap +
        '}';
  }

  public static class Builder {

    private HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    private Level level = Level.ERROR;
    private String errorCode = "error.unknown";
    private Map<String, String> errorMap = null;
    private Throwable cause = null;

    public Builder httpStatus(HttpStatus httpStatus) {
      this.httpStatus = httpStatus;
      return this;
    }

    public Builder level(Level level) {
      this.level = level;
      return this;
    }

    public Builder errorCode(String errorCode) {
      this.errorCode = errorCode;
      return this;
    }

    public Builder errorMap(Map<String, String> errorMap) {
      this.errorMap = errorMap;
      return this;
    }

    public Builder cause(Throwable cause) {
      this.cause = cause;
      return this;
    }

    public AppException build() {
      return new AppException(httpStatus, level, errorCode, errorMap, cause);
    }
  }

}
