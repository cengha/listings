package com.heycar.listings.controller;

import com.heycar.listings.CommonFunctionalTest;
import org.jooq.DSLContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static com.heycar.listings.fixtures.TestFixtures.someColor;
import static com.heycar.listings.fixtures.TestFixtures.someListingRecord;
import static com.heycar.listings.fixtures.TestFixtures.someListingRequestDto;
import static com.heycar.listings.fixtures.TestFixtures.someMake;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

public class VehicleListingControllerFunctionalTest extends CommonFunctionalTest {

  @Autowired
  DSLContext dslContext;

  @Test
  void thatPostsVehicleListingsWithSuccess() {
    // given
    final var body = List.of(
      someListingRequestDto(),
      someListingRequestDto()
    );

    // when
    requestSpecification()
      .header("X-Dealer-id", UUID.randomUUID())
      .contentType(MediaType.APPLICATION_JSON_VALUE)
      .body(body)
      .post("/vehicle-listings")

      // then
      .then()
      .assertThat()
      .statusCode(200);
  }

  @Test
  void thatPostsVehicleListingsWithSuccessWhenListingExists() {
    // given
    final var firstListing = someListingRequestDto();
    final var secondListing = someListingRequestDto();
    final var body = List.of(
      firstListing,
      secondListing,
      secondListing.withMake(someMake()).withColor(someColor())
    );

    // when
    requestSpecification()
      .header("X-Dealer-id", UUID.randomUUID())
      .body(body)
      .post("/vehicle-listings")

      // then
      .then()
      .assertThat()
      .statusCode(200);
  }

  @Test
  void thatPostsVehicleFailsDueToAbsentHeaderDealerId() {
    // given
    final var body = List.of(
      someListingRequestDto(),
      someListingRequestDto()
    );

    // when
    requestSpecification()
      .body(body)
      .post("/vehicle-listings")

      // then
      .then()
      .assertThat()
      .statusCode(400);
  }

  @Test
  void thatPostsVehicleFailsDueToAbsentRequestBody() {
    // when
    requestSpecification()
      .header("X-Dealer-id", UUID.randomUUID())
      .post("/vehicle-listings")

      // then
      .then()
      .assertThat()
      .statusCode(400);
  }

  @Test
  void thatUploadsVehicleListingsSuccessfullyFromCsvFile() throws IOException {
    // given
    final var stream = this.getClass().getClassLoader().getResourceAsStream("listings.csv");
    final var file = new MockMultipartFile("listings", stream);

    // when
    requestSpecification()
      .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
      .multiPart("file","listings.csv",file.getBytes())
      .post("/upload_csv/3434")

      // then
      .then()
      .assertThat()
      .statusCode(200);
  }

  @Test
  void thatGetsVehicleListings() {
    // given
    final var firstListingRecord = someListingRecord()
      .setModel("audix")
      .setMake("a909")
      .setYear("1900")
      .setColor("purple");

    final var secondListingRecord = someListingRecord()
      .setModel("audix")
      .setMake("a909")
      .setYear("1900")
      .setColor("purple");

    final var thirdListingRecord = someListingRecord()
      .setModel("audix")
      .setMake("a909")
      .setYear("1900")
      .setColor("purple");

    final var fourthListingRecord = someListingRecord();
    final var fifthListingRecord = someListingRecord();
    final var sixthListingRecord = someListingRecord();
    final var eighthListingRecord = someListingRecord();
    final var ninthListingRecord = someListingRecord();
    final var tenthListingRecord = someListingRecord();

    Stream.of(
      firstListingRecord,
      secondListingRecord,
      thirdListingRecord,
      fourthListingRecord,
      fifthListingRecord,
      sixthListingRecord,
      eighthListingRecord,
      ninthListingRecord,
      tenthListingRecord
    ).forEach(listingRecord -> dslContext.executeInsert(listingRecord));

    // when
    final var limit = "2";

    requestSpecification()
      .queryParam("model", "audix")
      .queryParam("make", "a909")
      .queryParam("year", "1900")
      .queryParam("color", "purple")
      .queryParam("offset", "0")
      .queryParam("limit", limit)
      .get("/vehicle-listings")
      .then()
      .log().all()
      .assertThat()
      .statusCode(HttpStatus.OK.value())
      .and()
      .body("currentPage", equalTo(1))
      .body("total", equalTo(3))
      .body("totalPages", equalTo(2))
      .body("items", hasSize(Integer.parseInt(limit)));
  }

}
