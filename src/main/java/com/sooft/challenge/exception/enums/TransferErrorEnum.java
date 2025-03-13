package com.sooft.challenge.exception.enums;

public enum TransferErrorEnum implements ErrorEnum {
  NOT_FOUND("error.transfer.not-found");

  private final String code;

  TransferErrorEnum(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }
}
