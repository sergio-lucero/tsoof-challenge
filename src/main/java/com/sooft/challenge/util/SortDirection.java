package com.sooft.challenge.util;

public enum SortDirection {
  ASC,
  DESC;

  private SortDirection() {
  }

  public boolean isAscending() {
    return this.equals(ASC);
  }

  public boolean isDescending() {
    return this.equals(DESC);
  }

  public static SortDirection fromString(String sortDirection) {
    try {
      return valueOf(sortDirection.toUpperCase());
    } catch (IllegalArgumentException ex) {
      throw new IllegalArgumentException(String.format("Invalid value '%s' for orders given; Has to be either 'desc' or 'asc' (case insensitive)", sortDirection), ex);
    }
  }
}
