package com.heycar.listings.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

@Value
@Builder
@With
public class Listing {

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

}
