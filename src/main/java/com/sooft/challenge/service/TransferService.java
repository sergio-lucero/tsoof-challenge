package com.sooft.challenge.service;

import com.sooft.challenge.dto.controller.TransferCreatedDto;
import com.sooft.challenge.dto.controller.TransferDto;
import com.sooft.challenge.dto.filters.PaginatedResponseDto;
import com.sooft.challenge.dto.filters.TransferFiltersDto;
import java.util.UUID;

public interface TransferService {

  PaginatedResponseDto<TransferDto> findAll(TransferFiltersDto filters);

  TransferDto findById(UUID id);

  TransferDto create(TransferCreatedDto createdDto);

  void delete(UUID id);
}
