package com.sooft.challenge.exception;

import com.sooft.challenge.exception.enums.ErrorEnum;
import org.springframework.http.HttpStatus;

public class NotFoundException extends AbstractChallengeException {

  public NotFoundException(ErrorEnum errorEnum) {
    super(errorEnum);
    this.httpStatus = HttpStatus.NOT_FOUND;
  }
}
