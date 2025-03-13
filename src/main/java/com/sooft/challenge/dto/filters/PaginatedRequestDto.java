package com.sooft.challenge.dto.filters;

import com.sooft.challenge.util.SortDirection;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class PaginatedRequestDto {

  private @NotNull @Min(0L) Integer page;
  private @NotNull @Min(0L) Integer limit;
  private String sortProperty;
  private SortDirection sortDirection;

  public Integer getPage() {
    return this.page;
  }

  public void setPage(Integer page) {
    this.page = page;
  }

  public Integer getLimit() {
    return this.limit;
  }

  public void setLimit(Integer limit) {
    this.limit = limit;
  }

  public String getSortProperty() {
    return this.sortProperty;
  }

  public void setSortProperty(String sortProperty) {
    this.sortProperty = sortProperty;
  }

  public SortDirection getSortDirection() {
    return this.sortDirection;
  }

  public void setSortDirection(SortDirection sortDirection) {
    this.sortDirection = sortDirection;
  }

  public static boolean isUnpaged(PaginatedRequestDto paginatedRequestDto) {
    if (paginatedRequestDto != null && paginatedRequestDto.page != null
        && paginatedRequestDto.limit != null) {
      return paginatedRequestDto.page == 0 && paginatedRequestDto.limit == 0;
    } else {
      throw new IllegalArgumentException("paginatedRequestDto, page and limit mustn't be null");
    }
  }

  public String toString() {
    return "PaginatedRequestDto{page=" + this.page + ", limit=" + this.limit + ", sortProperty='"
        + this.sortProperty + "', sortDirection=" + this.sortDirection + "}";
  }

  private @AssertTrue(message = "Page must be 0 when limit is 0") boolean isPageAndLimitValid() {
    if (this.limit != null && this.page != null) {
      return this.limit != 0 || this.page == 0;
    } else {
      return false;
    }
  }
}
