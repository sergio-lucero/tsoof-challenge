package com.sooft.challenge.exception.enums;

public enum CompanyErrorEnum implements ErrorEnum {
  NOT_FOUND("error.company.not-found");

  private final String code;

  CompanyErrorEnum(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }
}
