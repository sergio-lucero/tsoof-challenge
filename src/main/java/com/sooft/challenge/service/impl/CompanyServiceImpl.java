package com.sooft.challenge.service.impl;

import com.sooft.challenge.dto.controller.CompanyCreatedDto;
import com.sooft.challenge.dto.controller.CompanyDto;
import com.sooft.challenge.dto.filters.CompanyFiltersDto;
import com.sooft.challenge.dto.filters.PaginatedRequestDto;
import com.sooft.challenge.dto.filters.PaginatedResponseDto;
import com.sooft.challenge.exception.NotFoundException;
import com.sooft.challenge.exception.enums.CompanyErrorEnum;
import com.sooft.challenge.mapper.CompanyMapper;
import com.sooft.challenge.model.Company;
import com.sooft.challenge.repository.CompanyRepository;
import com.sooft.challenge.service.CompanyService;
import com.sooft.challenge.util.Pageables;
import com.sooft.challenge.util.Sorts;
import com.sooft.challenge.util.Specifications;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl implements CompanyService {

  private final CompanyRepository companyRepository;

  public CompanyServiceImpl(CompanyRepository companyRepository) {
    this.companyRepository = companyRepository;
  }

  @Override
  public PaginatedResponseDto<CompanyDto> findAll(CompanyFiltersDto filters) {

    List<Company> companies;
    if (filters.getLimit() == 0) {
      companies = companyRepository.findAll(
          Specifications.concatenate(renderFilters(filters)),
          Sorts.of(filters.getSortProperty(), filters.getSortDirection()));
      return new PaginatedResponseDto<>(companies.stream().map(CompanyMapper::fromEntity).toList());
    } else {
      Page<Company> companiesPage = companyRepository.findAll(
          Specifications.concatenate(renderFilters(filters)),
          Pageables.of(filters));
      return new PaginatedResponseDto<>(
          companiesPage.getContent().stream().map(CompanyMapper::fromEntity).toList(),
          companiesPage.getTotalPages(), companiesPage.getTotalElements(),
          filters.getPage(), filters.getLimit(), companiesPage.getNumberOfElements());
    }

  }

  @Override
  public CompanyDto findById(UUID id) {

    return CompanyMapper.fromEntity(companyRepository.findById(id).orElseThrow(()-> new NotFoundException(
        CompanyErrorEnum.NOT_FOUND)));
  }

  @Override
  public CompanyDto create(CompanyCreatedDto createdDto) {
    Company company = CompanyMapper.toEntity(createdDto);
    company.setId(UUID.randomUUID());
    return CompanyMapper.fromEntity(companyRepository.save(company));
  }

  @Override
  public CompanyDto update(UUID id, CompanyCreatedDto updatedDto) {
    Company company = companyRepository.findById(id).orElseThrow(()-> new NotFoundException(
        CompanyErrorEnum.NOT_FOUND));

    company.setCompanyName(updatedDto.getCompanyName());
    company.setCuit(updatedDto.getCuit());
    return CompanyMapper.fromEntity(companyRepository.save(company));
  }

  @Override
  public void delete(UUID id) {
    Company company = companyRepository.findById(id).orElseThrow(()-> new NotFoundException(
        CompanyErrorEnum.NOT_FOUND));
    companyRepository.delete(company);
  }

  @Override
  public PaginatedResponseDto<CompanyDto> findCompaniesCreatedBeforeMonths(int months,
      PaginatedRequestDto paginated) {
    CompanyFiltersDto filters = new CompanyFiltersDto();
    filters.setLimit(paginated.getLimit());
    filters.setPage(paginated.getPage());
    filters.setCreatedFrom(LocalDate.now().minusMonths(months));
    filters.setCreatedTo(LocalDate.now());

    return this.findAll(filters);
  }

  @Override
  public PaginatedResponseDto<CompanyDto> findCompaniesWithTransfersAfterMonths(int months,
      PaginatedRequestDto paginated) {
    List<Company> companies;
    List<Specification<Company>> specs = new ArrayList<>();
    specs.add(Company.withTransferAfter(LocalDateTime.now().minusMonths(months)));
    if (paginated.getLimit() == 0) {
      companies = companyRepository.findAll(
          Specifications.concatenate(specs),
          Sorts.of(paginated.getSortProperty(), paginated.getSortDirection()));
      return new PaginatedResponseDto<>(companies.stream().map(CompanyMapper::fromEntity).toList());
    } else {
      Page<Company> companiesPage = companyRepository.findAll(
          Specifications.concatenate(specs),
          Pageables.of(paginated));
      return new PaginatedResponseDto<>(
          companiesPage.getContent().stream().map(CompanyMapper::fromEntity).toList(),
          companiesPage.getTotalPages(), companiesPage.getTotalElements(),
          paginated.getPage(), paginated.getLimit(), companiesPage.getNumberOfElements());
    }
  }

  private List<Specification<Company>> renderFilters(CompanyFiltersDto filters) {
    List<Specification<Company>> specs = new ArrayList<>();

    if (filters.getIds() != null && !filters.getIds().isEmpty()) {
      specs.add(Company.idsIn(filters.getIds()));
    }

    if (filters.getCuits() != null && !filters.getCuits().isEmpty()) {
      specs.add(Company.cuitsIn(filters.getCuits()));
    }

    if (filters.getCompanyNames() != null && !filters.getCompanyNames().isEmpty()) {
      specs.add(Company.companyNamesIn(filters.getCompanyNames()));
    }

    if (Objects.nonNull(filters.getCreatedFrom())) {
      specs.add(Company.createdAtGte(filters.getCreatedFrom().atStartOfDay()));
    }

    if (Objects.nonNull(filters.getCreatedTo())) {
      specs.add(Company.createdAtLt(filters.getCreatedTo().atTime(23, 59, 59)));
    }

    return specs;
  }
}
