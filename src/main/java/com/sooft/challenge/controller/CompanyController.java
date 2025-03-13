package com.sooft.challenge.controller;

import com.sooft.challenge.dto.controller.CompanyCreatedDto;
import com.sooft.challenge.dto.controller.CompanyDto;
import com.sooft.challenge.dto.filters.CompanyFiltersDto;
import com.sooft.challenge.dto.filters.PaginatedResponseDto;
import com.sooft.challenge.service.CompanyService;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping
public class CompanyController {

  private final CompanyService companyService;

  public CompanyController(CompanyService companyService) {
    this.companyService = companyService;
  }

  @GetMapping("/api/companies")
  public PaginatedResponseDto<CompanyDto> findAll(
      @ParameterObject @Valid CompanyFiltersDto filters) {
    return companyService.findAll(filters);
  }

  @GetMapping("/api/companies/{id}")
  public CompanyDto findById(@PathVariable UUID id) {
    return companyService.findById(id);
  }

  @PostMapping(value = "/api/companies")
  @ResponseStatus(HttpStatus.CREATED)
  public CompanyDto create(@RequestBody @Valid CompanyCreatedDto createdDto) {
    return companyService.create(createdDto);
  }

  @PutMapping(value = "/api/companies/{id}")
  public CompanyDto update(@PathVariable UUID id,
      @RequestBody @Valid CompanyCreatedDto updatedDto) {
    return companyService.update(id, updatedDto);
  }

  @DeleteMapping("/api/companies/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID id) {
    companyService.delete(id);
  }
}
