package com.heycar.listings.service;

import com.heycar.listings.controller.ListingParams;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class VehicleListingsQuery {

  @NonNull
  @Builder.Default
  private ListingParams listingParams = ListingParams.builder().build();

  String make;

  String model;

  String year;

  String color;
}
