package com.sooft.challenge.util;

import lombok.Generated;
import org.springframework.data.domain.Sort;

public class Sorts {

  @Generated
  private Sorts() {
    throw new IllegalStateException("Utility class");
  }

  public static Sort of(String sortProperty, SortDirection sortDirection) {
    if (sortProperty == null || sortProperty.isEmpty()) {
      return Sort.unsorted();
    }
    return sortDirection == SortDirection.ASC
        ? Sort.by(sortProperty).ascending()
        : Sort.by(sortProperty).descending();
  }
}
