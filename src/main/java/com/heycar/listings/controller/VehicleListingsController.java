package com.heycar.listings.controller;

import com.heycar.listings.controller.dto.ListingRequestDto;
import com.heycar.listings.controller.dto.ListingResponse;
import com.heycar.listings.service.ListingService;
import com.heycar.listings.service.VehicleListingsQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class VehicleListingsController {

  private final ListingService listingService;

  @PostMapping(value = "/vehicle-listings", consumes = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<String> postListingsAsJson(
    @RequestHeader(value = "X-Dealer-id") final String dealerId,
    @RequestBody final List<ListingRequestDto> listingDtos
  ) {

    final var listings = listingDtos.stream()
      .map(listingDto -> listingDto.asListing(dealerId))
      .collect(Collectors.toList());

    final var result = listingService.insertOrUpdate(listings);
    return ResponseEntity.ok(String.format("%d listings has been created", result.size()));
  }

  @PostMapping(value = "/upload_csv/{dealer_id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  ResponseEntity<String> uploadListingsAsCsv(
    @PathVariable(value = "dealer_id") final String dealerId,
    @RequestParam(value = "file") final MultipartFile listingsFile
  ) {
    final var result = listingService.insertOrUpdate(dealerId, listingsFile);
    return ResponseEntity.ok(String.format("%d listings has been created", result.size()));
  }

  @GetMapping("/vehicle-listings")
  ResponseEntity<ListingResponse> retrieve(
    final @RequestParam(name = "make", required = false) String make,
    final @RequestParam(name = "model", required = false) String model,
    final @RequestParam(name = "year", required = false) String year,
    final @RequestParam(name = "color", required = false) String color,
    final ListingParams listingParams
  ) {

    final var vehicleListingListing = listingService.getVehicleListings(
      VehicleListingsQuery.builder()
        .listingParams(listingParams)
        .make(make)
        .model(model)
        .year(year)
        .color(color)
        .build()
    );

    return ResponseEntity.ok(
      ListingResponse.of(vehicleListingListing)
    );

  }

}
