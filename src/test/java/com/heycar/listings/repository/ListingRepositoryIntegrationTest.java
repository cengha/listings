package com.heycar.listings.repository;

import com.heycar.listings.CommonFunctionalTest;
import com.heycar.listings.infrastructure.persistence.jooq.generated.listings.Tables;
import org.jooq.DSLContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.heycar.listings.fixtures.TestFixtures.someColor;
import static com.heycar.listings.fixtures.TestFixtures.someListing;
import static com.heycar.listings.fixtures.TestFixtures.somePower;

class ListingRepositoryIntegrationTest extends CommonFunctionalTest {

  @Autowired
  ListingRepository listingRepository;


  @Autowired
  DSLContext dslContext;

  @Test
  void thatInsertsAllNewListings() {
    // given
    final var listing = someListing();
    final var anotherListing = someListing();
    final var listings = List.of(listing, anotherListing);

    // when
    final var actual = listingRepository.insertOrUpdate(listings);

    // then
    Assertions.assertIterableEquals(actual, List.of(1, 1));
    Assertions.assertIterableEquals(
      dslContext.selectFrom(Tables.LISTINGS),
      listings.stream().map(ListingRepository::toListingRecord).collect(Collectors.toList())
    );
  }

  @Test
  void thatInsertsAllNewListingsAndUpdatesExistingOnes() {
    // given
    final var listing = someListing();
    final var listingVariation = listing.withColor(someColor()).withPower(somePower());
    final var anotherListing = someListing();
    final var listings = List.of(listing, listingVariation, anotherListing);

    // when
    final var actual = listingRepository.insertOrUpdate(listings);

    // then
    Assertions.assertIterableEquals(actual, List.of(1, 1, 1));
    Assertions.assertIterableEquals(
      dslContext.selectFrom(Tables.LISTINGS),
      Stream.of(listingVariation, anotherListing).map(ListingRepository::toListingRecord).collect(Collectors.toList())
    );
  }

}