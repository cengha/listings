package com.heycar.listings.controller.dto;

import com.heycar.listings.model.Listing;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class ListingsDto {

  @NonNull
  String id;

  @NonNull
  String dealerId;

  @NonNull
  String code;

  @NonNull
  String make;

  @NonNull
  String model;

  @NonNull
  Integer power;

  @NonNull
  String year;

  @NonNull
  String color;

  @NonNull
  Long price;

  public static ListingsDto of(final Listing listing) {
    return ListingsDto.builder()
      .id(listing.getId())
      .dealerId(listing.getDealerId())
      .code(listing.getCode())
      .make(listing.getMake())
      .model(listing.getModel())
      .power(listing.getPower())
      .year(listing.getYear())
      .color(listing.getColor())
      .price(listing.getPrice())
      .build();
  }
}
