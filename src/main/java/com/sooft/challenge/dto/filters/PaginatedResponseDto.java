package com.sooft.challenge.dto.filters;

import java.util.Collections;
import java.util.List;

public class PaginatedResponseDto<T> {

  private List<T> data;
  private Integer totalPages;
  private Long totalElements;
  private Integer page;
  private Integer limit;
  private Integer numberOfElements;

  public PaginatedResponseDto() {
  }

  public PaginatedResponseDto(Integer page, Integer limit) {
    this.data = Collections.emptyList();
    this.totalPages = 0;
    this.totalElements = 0L;
    this.page = page;
    this.limit = limit;
    this.numberOfElements = 0;
  }

  public PaginatedResponseDto(List<T> data, Integer totalPages, Long totalElements, Integer page, Integer limit, Integer numberOfElements) {
    this.data = data;
    this.totalPages = totalPages;
    this.totalElements = totalElements;
    this.page = page;
    this.limit = limit;
    this.numberOfElements = numberOfElements;
  }

  public PaginatedResponseDto(List<T> data) {
    this(data, 1, (long)data.size(), 0, 0, data.size());
  }

  public List<T> getData() {
    return this.data;
  }

  public void setData(List<T> data) {
    this.data = data;
  }

  public Integer getTotalPages() {
    return this.totalPages;
  }

  public void setTotalPages(Integer totalPages) {
    this.totalPages = totalPages;
  }

  public Long getTotalElements() {
    return this.totalElements;
  }

  public void setTotalElements(Long totalElements) {
    this.totalElements = totalElements;
  }

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

  public Integer getNumberOfElements() {
    return this.numberOfElements;
  }

  public void setNumberOfElements(Integer numberOfElements) {
    this.numberOfElements = numberOfElements;
  }
}
