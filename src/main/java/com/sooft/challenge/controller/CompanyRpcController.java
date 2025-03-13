package com.sooft.challenge.controller;

import com.sooft.challenge.dto.controller.CompanyDto;
import com.sooft.challenge.dto.filters.PaginatedRequestDto;
import com.sooft.challenge.dto.filters.PaginatedResponseDto;
import com.sooft.challenge.service.CompanyService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping
public class CompanyRpcController {

  private final CompanyService companyService;

  public CompanyRpcController(CompanyService companyService) {
    this.companyService = companyService;
  }

  @GetMapping("/rpc/companies/created")
  public PaginatedResponseDto<CompanyDto> findCompaniesCreatedBeforeMonths(
     @RequestParam(required = true) @Min(1) int months,
     @ParameterObject @Valid PaginatedRequestDto paginated) {
    return companyService.findCompaniesCreatedBeforeMonths(months, paginated);
  }

  @GetMapping("/rpc/companies/with/transfer")
  public PaginatedResponseDto<CompanyDto> findCompaniesWithTransfers(
      @RequestParam(required = true) @Min(1) int months,
      @ParameterObject @Valid PaginatedRequestDto paginated) {
    return companyService.findCompaniesWithTransfersAfterMonths(months, paginated);
  }
}
