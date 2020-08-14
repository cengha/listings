package com.heycar.listings.controller.model;

import com.heycar.listings.model.Listing;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class ListingListing {

  @NonNull
  private Integer currentPage;

  @NonNull
  private Integer totalPages;

  @NonNull
  private Integer total;

  @NonNull
  private List<Listing> items;

}
