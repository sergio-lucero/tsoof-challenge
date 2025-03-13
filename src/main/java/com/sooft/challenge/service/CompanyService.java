package com.sooft.challenge.service;

import com.sooft.challenge.dto.controller.CompanyCreatedDto;
import com.sooft.challenge.dto.controller.CompanyDto;
import com.sooft.challenge.dto.filters.CompanyFiltersDto;
import com.sooft.challenge.dto.filters.PaginatedRequestDto;
import com.sooft.challenge.dto.filters.PaginatedResponseDto;
import java.util.UUID;

public interface CompanyService {

  PaginatedResponseDto<CompanyDto> findAll(CompanyFiltersDto filters);

  CompanyDto findById(UUID id);

  CompanyDto create(CompanyCreatedDto createdDto);

  CompanyDto update(UUID id, CompanyCreatedDto updatedDto);

  void delete(UUID id);

  PaginatedResponseDto<CompanyDto> findCompaniesCreatedBeforeMonths(int months,
      PaginatedRequestDto paginated);

  PaginatedResponseDto<CompanyDto> findCompaniesWithTransfersAfterMonths(int months,
      PaginatedRequestDto paginated);

}
