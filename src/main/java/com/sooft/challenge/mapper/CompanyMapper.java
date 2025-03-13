package com.sooft.challenge.mapper;

import com.sooft.challenge.dto.controller.CompanyCreatedDto;
import com.sooft.challenge.dto.controller.CompanyDto;
import com.sooft.challenge.model.Company;

public class CompanyMapper {

  private CompanyMapper() {
  }

  public static Company toEntity(CompanyCreatedDto dto) {
    Company entity = new Company();
    entity.setCompanyName(dto.getCompanyName());
    entity.setCuit(dto.getCuit());
    return entity;
  }

  public static CompanyDto fromEntity(Company entity) {
    return CompanyDto.builder()
        .id(entity.getId())
        .cuit(entity.getCuit())
        .companyName(entity.getCompanyName())
        .creationDate(entity.getCreatedAt())
        .build();
  }
}
