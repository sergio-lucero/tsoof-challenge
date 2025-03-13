package com.sooft.challenge.dto.filters;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferFiltersDto extends PaginatedRequestDto {

  private List<UUID> ids;
  private List<UUID> companyIds;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @Temporal(TemporalType.DATE)
  private LocalDate createdFrom;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @Temporal(TemporalType.DATE)
  private LocalDate createdTo;
  private String debitAccount;
  private String creditAccount;
  private Boolean isDebitAccount;
  private Boolean isCreditAccount;
}