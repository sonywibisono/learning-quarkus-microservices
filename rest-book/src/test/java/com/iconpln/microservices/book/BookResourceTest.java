package com.iconpln.microservices.book;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class BookResourceTest {
    @Test
    public void shouldCreateAqBook() {
        given().formParam("title","Understanding Quarkus")
                .formParam("author","Sony Wibisono")
                .formParam("genre","IT")
                .formParam("year",2020)
                .when()
                .post("/api/books")
                .then()
                .statusCode(201)
                .body("isbn_13",startsWith("13-"))
                .body("title",is("Understanding Quarkus"))
                .body("author",is("Sony Wibisono"))
                .body("year_of_publication",is(2020))
                .body("creation_date",startsWith("20"));
    }
}
