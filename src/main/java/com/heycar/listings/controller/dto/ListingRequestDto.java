package com.heycar.listings.controller.dto;

import com.heycar.listings.model.Listing;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.util.UUID;

@Value
@Builder
@AllArgsConstructor
@With
public class ListingRequestDto {

  String code;
  String make;
  String model;
  Integer power;
  String year;
  String color;
  Long price;

  public Listing asListing(final String dealerId) {
    return Listing.builder()
      .id(UUID.randomUUID().toString())
      .dealerId(dealerId)
      .code(code)
      .make(make)
      .model(model)
      .power(power)
      .year(year)
      .color(color)
      .price(price)
      .build();
  }

}
