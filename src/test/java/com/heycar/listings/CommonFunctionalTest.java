package com.heycar.listings;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.heycar.listings.IntegrationTest.INTEGRATION_TEST_TAG;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@Tag(INTEGRATION_TEST_TAG)
public abstract class CommonFunctionalTest {

  @LocalServerPort
  int serverPort;

  @Autowired
  DbCleaner dbCleaner;

  @BeforeAll
  public static void before() {
    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
  }

  @AfterEach
  void cleanUp() {
    dbCleaner.execute();
  }

  public RequestSpecification requestSpecification() {
    return RestAssured
      .given()
      .relaxedHTTPSValidation()
      .log().all()
      .baseUri("https://localhost")
      .port(serverPort)
      .accept(ContentType.JSON)
      .contentType(ContentType.JSON);
  }

}
