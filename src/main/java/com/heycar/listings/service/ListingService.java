package com.heycar.listings.service;

import com.heycar.listings.controller.model.ListingListing;
import com.heycar.listings.model.Listing;
import com.heycar.listings.repository.ListingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class ListingService {

  private final ListingRepository listingRepository;
  private final CsvService csvService;

  public List<Integer> insertOrUpdate(final List<Listing> listings) {
    return listingRepository.insertOrUpdate(listings);
  }

  public List<Integer> insertOrUpdate(final String dealerId, final MultipartFile listingsFile) {
    final var listings = csvService.toListings(dealerId, listingsFile);
    return insertOrUpdate(listings);
  }

  public ListingListing getVehicleListings(final VehicleListingsQuery query) {
    final var listings = listingRepository.getVehicleListings(query);
    final var total = listingRepository.countVehicleListings(query);

    return ListingListing.builder()
      .currentPage(query.getListingParams().getOffset() / query.getListingParams().getLimit() + 1)
      .total(total)
      .totalPages((int) Math.ceil(((double) total) / query.getListingParams().getLimit()))
      .items(listings)
      .build();
  }
}
