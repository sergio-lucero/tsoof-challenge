package com.sooft.challenge.dto.controller;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferCreatedDto {

  @NotNull
  private UUID companyId;
  @NotNull
  @Min(0)
  private Double amount;
  private String debitAccount;
  private String creditAccount;

  @AssertTrue(message = "either debit or credit account must be set")
  public boolean isDebitOrCreditAccountSet() {
    return (Objects.nonNull(debitAccount) || Objects.nonNull(creditAccount))
        && !(Objects.nonNull(debitAccount) && Objects.nonNull(creditAccount));
  }
}
