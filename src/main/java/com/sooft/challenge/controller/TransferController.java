package com.sooft.challenge.controller;

import com.sooft.challenge.dto.controller.TransferCreatedDto;
import com.sooft.challenge.dto.controller.TransferDto;
import com.sooft.challenge.dto.filters.PaginatedResponseDto;
import com.sooft.challenge.dto.filters.TransferFiltersDto;
import com.sooft.challenge.service.TransferService;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping
public class TransferController {

  private final TransferService transferService;

  public TransferController(TransferService transferService) {
    this.transferService = transferService;
  }

  @GetMapping("/api/transfers")
  public PaginatedResponseDto<TransferDto> findAll(
      @ParameterObject @Valid TransferFiltersDto filters) {
    return transferService.findAll(filters);
  }
  @GetMapping("/api/transfers/{id}")
  public TransferDto findById(@PathVariable UUID id) {
    return transferService.findById(id);
  }

  @PostMapping(value = "/api/transfers")
  @ResponseStatus(HttpStatus.CREATED)
  public TransferDto create(@RequestBody @Valid TransferCreatedDto createdDto) {
    return transferService.create(createdDto);
  }

  @DeleteMapping("/api/transfers/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID id) {
    transferService.delete(id);
  }
}
