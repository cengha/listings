package com.heycar.listings.controller;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.Optional;

@Value
@Builder(toBuilder = true)
public class ListingParams {

  @NonNull
  private Integer offset;

  @NonNull
  private Integer limit;

  public ListingParams(
    final Integer offset,
    final Integer limit
  ) {
    this.offset = Optional.ofNullable(offset).orElse(0);
    this.limit = Optional.ofNullable(limit).orElse(50);
    validate();
  }

  private void validate() {
    if (offset < 0) {
      throw new RuntimeException("invalid offset value");
    }

    if (limit < 1) {
      throw new RuntimeException("invalid limit value");
    }
  }

}
