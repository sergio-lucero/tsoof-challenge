package com.sooft.challenge.mapper;

import com.sooft.challenge.dto.controller.TransferCreatedDto;
import com.sooft.challenge.dto.controller.TransferDto;
import com.sooft.challenge.model.Transfer;

public class TransferMapper {

  private TransferMapper() {
  }

  public static Transfer toEntity(TransferCreatedDto dto) {
    Transfer entity = new Transfer();
    entity.setAmount(dto.getAmount());
    entity.setCreditAccount(dto.getCreditAccount());
    entity.setDebitAccount(dto.getDebitAccount());
    return entity;
  }

  public static TransferDto fromEntity(Transfer entity) {
    return TransferDto.builder()
        .id(entity.getId())
        .amount(entity.getAmount())
        .company(CompanyMapper.fromEntity(entity.getCompany()))
        .creditAccount(entity.getCreditAccount())
        .debitAccount(entity.getDebitAccount())
        .creationDate(entity.getCreatedAt())
        .build();
  }
}
