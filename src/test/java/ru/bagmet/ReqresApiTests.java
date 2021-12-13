package ru.bagmet;

import io.restassured.RestAssured;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class ReqresApiTests {

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "https://reqres.in";
    }

    @Test
    void getUserInfo() {
        given()
                .contentType(JSON)
                .when()
                .get("/api/users/2")
                .then()
                .statusCode(200)
                .body("data.email",is("janet.weaver@reqres.in"),
                        "data.id",equalTo(2));

        Integer id = given()
                .contentType(JSON)
                .when()
                .get("/api/users/2")
                .then()
                .extract().path("data.id");
        Assertions.assertThat(id).isEqualTo(2);

    }

}
