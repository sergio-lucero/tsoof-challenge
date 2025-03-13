package com.sooft.challenge.dto.controller;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferDto {
  private UUID id;
  private Double amount;
  private CompanyDto company;
  private String debitAccount;
  private String creditAccount;
  private LocalDateTime creationDate;
}