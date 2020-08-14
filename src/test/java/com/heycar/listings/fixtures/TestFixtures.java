package com.heycar.listings.fixtures;

import com.heycar.listings.controller.dto.ListingRequestDto;
import com.heycar.listings.infrastructure.persistence.jooq.generated.listings.tables.records.ListingsRecord;
import com.heycar.listings.model.Listing;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TestFixtures {

  private static final Map<String, List<String>> makeMap = Map.of(
    "Renault", List.of("clio", "megan", "symbol"),
    "BMW", List.of("clio", "megan", "symbol"),
    "VW", List.of("clio", "megan", "symbol"),
    "Audi", List.of("clio", "megan", "symbol"),
    "Fiat", List.of("clio", "megan", "symbol")
  );

  private static final String[] makes = makeMap.keySet().toArray(new String[0]);

  private static final List<String> years = List.of(
    "2001",
    "2002",
    "2003",
    "2004",
    "2005"
  );

  private static final List<String> colors = List.of(
    "red",
    "blue",
    "green",
    "pink",
    "orange",
    "grey"
  );

  public static String someMake() {
    return makes[RandomUtils.nextInt(0, makeMap.size())];
  }

  public static String someCode() {
    return RandomStringUtils.randomAlphanumeric(5);
  }

  public static String someDealerId() {
    return UUID.randomUUID().toString();
  }

  public static String someModel(final String make) {
    final var models = makeMap.get(make);
    return models.get(RandomUtils.nextInt(0, models.size()));
  }

  public static int somePower() {
    return RandomUtils.nextInt(130, 200);
  }

  public static String someYear() {
    return years.get(RandomUtils.nextInt(0, years.size()));
  }

  public static String someColor() {
    return colors.get(RandomUtils.nextInt(0, years.size()));
  }

  public static ListingRequestDto someListingRequestDto() {
    final var make = someMake();
    return ListingRequestDto.builder()
      .code(someCode())
      .make(make)
      .model(someModel(make))
      .power(somePower())
      .year(someYear())
      .color(someColor())
      .price(100L)
      .build();
  }

  public static Listing someListing() {
    final var make = someMake();

    return Listing.builder()
      .id(UUID.randomUUID().toString())
      .dealerId(someDealerId())
      .code(someCode())
      .make(make)
      .model(someModel(make))
      .power(somePower())
      .year(someYear())
      .color(someColor())
      .price(100L)
      .build();
  }

  public static ListingsRecord someListingRecord() {
    final var make = someMake();

    return new ListingsRecord(
      UUID.randomUUID().toString(),
      someDealerId(),
      someCode(),
      make,
      someModel(make),
      somePower(),
      someYear(),
      someColor(),
      100L
    );
  }

}
