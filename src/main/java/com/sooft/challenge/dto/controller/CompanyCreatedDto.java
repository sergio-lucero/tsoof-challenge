package com.sooft.challenge.dto.controller;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyCreatedDto {

  @NotNull
  @NotEmpty
  private String cuit;
  @NotNull
  @NotEmpty
  private String companyName;
}
