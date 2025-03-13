package com.sooft.challenge.exception;

import com.sooft.challenge.exception.enums.ErrorEnum;
import java.util.Map;
import org.springframework.http.HttpStatus;

public class BadRequestException extends AbstractChallengeException{

  public BadRequestException(ErrorEnum errorEnum) {
    super(errorEnum);
    this.httpStatus = HttpStatus.BAD_REQUEST;
  }

  public BadRequestException(
      ErrorEnum errorEnum,
      Map<String, String> errors
  ) {
    super(errorEnum, errors);
    this.httpStatus = HttpStatus.BAD_REQUEST;
  }
}
