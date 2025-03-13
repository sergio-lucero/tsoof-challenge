package com.sooft.challenge.exception;

import com.sooft.challenge.exception.enums.ErrorEnum;

public interface ChallengeException {

  ErrorEnum getErrorEnum();
}
