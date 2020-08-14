package com.heycar.listings.service;

import com.heycar.listings.model.Listing;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReaderBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CsvService {

  final CSVParser parser = new CSVParserBuilder()
    .withSeparator(',')
    .withIgnoreQuotations(true)
    .build();

  public List<Listing> toListings(final String dealerId, final MultipartFile file) {
    try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

      final var csvReader = new CSVReaderBuilder(reader).withSkipLines(1).withCSVParser(parser).build();

      return StreamSupport
        .stream(csvReader.spliterator(), false)
        .map(line -> toListing(dealerId, line))
        .collect(Collectors.toList());

    } catch (Exception ex) {
      throw new RuntimeException();
    }
  }

  private Listing toListing(final String dealerId, final String[] line) {
    final var makeModel = line[1].split("/");
    return Listing.builder()
      .id(UUID.randomUUID().toString())
      .dealerId(dealerId)
      .code(line[0])
      .make(makeModel[0])
      .model(makeModel[1])
      .power(Integer.valueOf(line[2]))
      .year(line[3])
      .color(line[4])
      .price(Long.valueOf(line[5]))
      .build();
  }

}
