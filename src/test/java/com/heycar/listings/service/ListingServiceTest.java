package com.heycar.listings.service;

import com.heycar.listings.repository.ListingRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.List;

import static com.heycar.listings.fixtures.TestFixtures.someDealerId;
import static com.heycar.listings.fixtures.TestFixtures.someListing;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class ListingServiceTest {

  @Mock
  ListingRepository listingRepository;

  @Mock
  CsvService csvService;

  @InjectMocks
  ListingService listingService;

  @Test
  void thatInsertOrUpdatesFromListingList() {
    // given
    final var listings = List.of(
      someListing(),
      someListing()
    );

    // and
    Mockito.when(listingRepository.insertOrUpdate(anyList())).thenReturn(List.of(1, 1));

    // when
    final var actual = listingService.insertOrUpdate(listings);

    // then
    Assertions.assertIterableEquals(actual, List.of(1, 1));
    Mockito.verify(listingRepository).insertOrUpdate(listings);
  }

  @Test
  void thatInsertOrUpdatesFromFile() throws IOException {
    // given
    final var dealerId = someDealerId();

    // and
    final var listings = List.of(
      someListing(),
      someListing()
    );

    // and
    final var stream = this.getClass().getClassLoader().getResourceAsStream("listings.csv");
    final var file = new MockMultipartFile("listings", stream);

    // and
    Mockito.when(listingRepository.insertOrUpdate(anyList())).thenReturn(List.of(1, 1));
    Mockito.when(csvService.toListings(anyString(), any())).thenReturn(listings);

    // when
    final var actual = listingService.insertOrUpdate(dealerId, file);

    // then
    Assertions.assertIterableEquals(actual, List.of(1, 1));
    Mockito.verify(listingRepository).insertOrUpdate(listings);
    Mockito.verify(csvService).toListings(dealerId, file);
  }

}
