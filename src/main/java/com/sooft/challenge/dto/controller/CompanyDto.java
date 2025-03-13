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
public class CompanyDto {
  private UUID id;
  private String cuit;
  private String companyName;
  private LocalDateTime creationDate;
}
