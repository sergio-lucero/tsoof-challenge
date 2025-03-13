package com.sooft.challenge.util;

import com.sooft.challenge.dto.filters.PaginatedRequestDto;
import lombok.Generated;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class Pageables {
  @Generated
  private Pageables() {
    throw new IllegalStateException("Utility class");
  }

  public static <T extends PaginatedRequestDto> Pageable of( T dto) {
    return PageRequest.of(
        dto.getPage(),
        dto.getLimit(),
        Sorts.of(dto.getSortProperty(), dto.getSortDirection())
    );
  }

  public static Sort sort(PaginatedRequestDto dto) {
    return Sorts.of(dto.getSortProperty(), dto.getSortDirection());
  }
}
