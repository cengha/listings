package com.heycar.listings.service;

import com.heycar.listings.fixtures.TestFixtures;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.List;

import static com.heycar.listings.fixtures.TestFixtures.someDealerId;

@ExtendWith(MockitoExtension.class)
class CsvServiceTest {

  @InjectMocks
  CsvService csvService;

  @Test
  void thatFailsWhenParseInvalidListingsCsvFile() throws IOException {
    // given
    final var stream = this.getClass().getClassLoader().getResourceAsStream("listings_invalid.csv");
    final var file = new MockMultipartFile("listings", stream);

    // and
    final var dealerId = someDealerId();

    // expect
    Assertions.assertThrows(RuntimeException.class, () -> csvService.toListings(dealerId, file));
  }

  @Test
  void thatSuccessWhenParseValidListingsCsvFile() throws IOException {
    // given
    final var stream = this.getClass().getClassLoader().getResourceAsStream("listings.csv");
    final var file = new MockMultipartFile("listings", stream);

    // and
    final var dealerId = someDealerId();

    // when
    final var actual = csvService.toListings(dealerId, file);

    // then
    final var expected = List.of(
      TestFixtures.someListing()
        .withId(actual.get(0).getId())
        .withDealerId(actual.get(0).getDealerId())
        .withCode("1")
        .withMake("mercedes")
        .withModel("a 180")
        .withPower(123)
        .withYear("2014")
        .withColor("black")
        .withPrice(15950L),
      TestFixtures.someListing()
        .withId(actual.get(1).getId())
        .withDealerId(actual.get(1).getDealerId())
        .withCode("2")
        .withMake("audi")
        .withModel("a3")
        .withPower(111)
        .withYear("2016")
        .withColor("white")
        .withPrice(17210L)
    );

    Assertions.assertIterableEquals(actual, expected);
  }

}