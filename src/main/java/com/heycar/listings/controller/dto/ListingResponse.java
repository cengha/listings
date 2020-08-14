package com.heycar.listings.controller.dto;

import com.heycar.listings.controller.model.ListingListing;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.List;
import java.util.stream.Collectors;

@Value
@Builder
public class ListingResponse {

  @NonNull
  private Integer currentPage;

  @NonNull
  private Integer totalPages;

  @NonNull
  private Integer total;

  @NonNull
  private List<ListingsDto> items;

  public static ListingResponse of(final ListingListing listingListing) {
    return ListingResponse.builder()
      .currentPage(listingListing.getCurrentPage())
      .total(listingListing.getTotal())
      .items(listingListing.getItems().stream().map(ListingsDto::of).collect(Collectors.toList()))
      .totalPages(listingListing.getTotalPages())
      .build();
  }

}
