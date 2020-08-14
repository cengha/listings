package com.heycar.listings.repository;

import com.heycar.listings.infrastructure.persistence.jooq.generated.listings.tables.records.ListingsRecord;
import com.heycar.listings.model.Listing;
import com.heycar.listings.service.VehicleListingsQuery;
import lombok.RequiredArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static com.heycar.listings.infrastructure.persistence.jooq.generated.listings.Tables.LISTINGS;
import static org.jooq.impl.DSL.trueCondition;

@Repository
@RequiredArgsConstructor
public class ListingRepository {

  private final DSLContext dslContext;

  public List<Integer> insertOrUpdate(final List<Listing> listings) {
    return listings
      .stream()
      .map(ListingRepository::toListingRecord)
      .map(this::insertOrUpdateOnConflict).collect(Collectors.toList());
  }

  private int insertOrUpdateOnConflict(final ListingsRecord listingsRecord) {
    return dslContext.insertInto(LISTINGS)
      .set(listingsRecord)
      .onConflict(List.of(LISTINGS.DEALER_ID, LISTINGS.CODE))
      .doUpdate()
      .set(listingsRecord)
      .execute();
  }

  public static ListingsRecord toListingRecord(final Listing listing) {
    return new ListingsRecord(
      listing.getId(),
      listing.getDealerId(),
      listing.getCode(),
      listing.getMake(),
      listing.getModel(),
      listing.getPower(),
      listing.getYear(),
      listing.getColor(),
      listing.getPrice()
    );
  }

  public List<Listing> getVehicleListings(final VehicleListingsQuery vehicleListingsQuery) {
    return dslContext.select(LISTINGS.fields())
      .from(LISTINGS)
      .where(toCondition(vehicleListingsQuery))
      .limit(vehicleListingsQuery.getListingParams().getLimit())
      .offset(vehicleListingsQuery.getListingParams().getOffset())
      .fetchStreamInto(ListingsRecord.class)
      .map(this::toListing)
      .collect(Collectors.toList());
  }

  private Listing toListing(final ListingsRecord record) {
    return Listing.builder()
      .id(record.getId())
      .dealerId(record.getDealerId())
      .code(record.getCode())
      .make(record.getMake())
      .model(record.getModel())
      .power(record.getPower())
      .year(record.getYear())
      .color(record.getColor())
      .price(record.getPrice())
      .build();
  }

  private Condition toCondition(final VehicleListingsQuery query) {
    final var makeCon = query.getMake() == null ? trueCondition() : LISTINGS.MAKE.eq(query.getMake());
    final var modelCon = query.getModel() == null ? trueCondition() : LISTINGS.MODEL.eq(query.getModel());
    final var yearCon = query.getYear() == null ? trueCondition() : LISTINGS.YEAR.eq(query.getYear());
    final var colorCon = query.getColor() == null ? trueCondition() : LISTINGS.COLOR.eq(query.getColor());
    return DSL.and(makeCon, modelCon, yearCon, colorCon);
  }

  public Integer countVehicleListings(final VehicleListingsQuery vehicleListingsQuery) {
    return dslContext.selectCount()
      .from(LISTINGS)
      .where(toCondition(vehicleListingsQuery))
      .fetchOneInto(Integer.class);
  }
}
