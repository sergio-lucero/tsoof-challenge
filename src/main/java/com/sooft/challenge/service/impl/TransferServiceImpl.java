package com.sooft.challenge.service.impl;

import com.sooft.challenge.dto.controller.TransferCreatedDto;
import com.sooft.challenge.dto.controller.TransferDto;
import com.sooft.challenge.dto.filters.PaginatedResponseDto;
import com.sooft.challenge.dto.filters.TransferFiltersDto;
import com.sooft.challenge.exception.NotFoundException;
import com.sooft.challenge.exception.enums.CompanyErrorEnum;
import com.sooft.challenge.exception.enums.TransferErrorEnum;
import com.sooft.challenge.mapper.TransferMapper;
import com.sooft.challenge.model.Transfer;
import com.sooft.challenge.repository.CompanyRepository;
import com.sooft.challenge.repository.TransferRepository;
import com.sooft.challenge.service.TransferService;
import com.sooft.challenge.util.Pageables;
import com.sooft.challenge.util.Sorts;
import com.sooft.challenge.util.Specifications;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class TransferServiceImpl implements TransferService {

  private final TransferRepository transferRepository;
  private final CompanyRepository companyRepository;

  public TransferServiceImpl(TransferRepository transferRepository,
      CompanyRepository companyRepository) {
    this.transferRepository = transferRepository;
    this.companyRepository = companyRepository;
  }

  @Override
  public PaginatedResponseDto<TransferDto> findAll(TransferFiltersDto filters) {

    if (filters.getLimit() == 0) {
      List<Transfer> transfers = transferRepository.findAll(
          Specifications.concatenate(renderFilters(filters)),
          Sorts.of(filters.getSortProperty(), filters.getSortDirection()));
      return new PaginatedResponseDto<>(transfers.stream().map(TransferMapper::fromEntity).toList());
    } else {
      Page<Transfer>transfersPage = transferRepository.findAll(
          Specifications.concatenate(renderFilters(filters)),
          Pageables.of(filters));
      return new PaginatedResponseDto<>(
          transfersPage.getContent().stream().map(TransferMapper::fromEntity).toList(),
          transfersPage.getTotalPages(), transfersPage.getTotalElements(),
          filters.getPage(), filters.getLimit(), transfersPage.getNumberOfElements());
    }
  }

  @Override
  public TransferDto findById(UUID id) {

    return TransferMapper.fromEntity(transferRepository.findById(id).orElseThrow(()-> new NotFoundException(
        TransferErrorEnum.NOT_FOUND)));
  }

  @Override
  public TransferDto create(TransferCreatedDto createdDto) {
    Transfer transfer = TransferMapper.toEntity(createdDto);
    transfer.setId(UUID.randomUUID());
    transfer.setCompany(companyRepository.findById(createdDto.getCompanyId()).orElseThrow(()-> new NotFoundException(
        CompanyErrorEnum.NOT_FOUND)));
    return TransferMapper.fromEntity(transferRepository.save(transfer));
  }

  @Override
  public void delete(UUID id) {
    Transfer transfer = transferRepository.findById(id).orElseThrow(()-> new NotFoundException(
        TransferErrorEnum.NOT_FOUND));
    transferRepository.delete(transfer);
  }

  private List<Specification<Transfer>> renderFilters(TransferFiltersDto filters) {
    List<Specification<Transfer>> specs = new ArrayList<>();

    if (filters.getIds() != null && !filters.getIds().isEmpty()) {
      specs.add(Transfer.idsIn(filters.getIds()));
    }

    if (filters.getCompanyIds() != null && !filters.getCompanyIds().isEmpty()) {
      specs.add(Transfer.companyIdsIn(filters.getCompanyIds()));
    }

    if (Objects.nonNull(filters.getCreditAccount())) {
      specs.add(Transfer.creditAccountEquals(filters.getCreditAccount()));
    }

    if (Objects.nonNull(filters.getDebitAccount())) {
      specs.add(Transfer.debitAccountEquals(filters.getDebitAccount()));
    }

    if (Objects.nonNull(filters.getCreatedFrom())) {
      specs.add(Transfer.createdAtGte(filters.getCreatedFrom().atStartOfDay()));
    }

    if (Objects.nonNull(filters.getCreatedTo())) {
      specs.add(Transfer.createdAtLt(filters.getCreatedTo().atTime(23, 59, 59)));
    }

    if(Objects.nonNull(filters.getIsCreditAccount())) {
      specs.add(Transfer.isCreditAccount(filters.getIsCreditAccount()));
    }

    if(Objects.nonNull(filters.getIsDebitAccount())) {
      specs.add(Transfer.isCreditAccount(filters.getIsDebitAccount()));
    }

    return specs;
  }
}
