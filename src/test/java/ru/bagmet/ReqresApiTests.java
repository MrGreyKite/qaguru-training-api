package ru.bagmet;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

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
        assertThat(id).isEqualTo(2);

    }

    @Test
    void GetUnexcitedUser() {
        given()
                .contentType(JSON)
                .when()
                .get("/api/users/23")
                .then()
                .statusCode(404)
                .body(is("{}"));
    }

    @Test
    void successfulRegistration() {
        JsonPath reg = given()
                .contentType(JSON)
                .body("{\"email\":  \"eve.holt@reqres.in\"," +
                        "\"password\": \"pistol\"}")
                .when()
                .post("/api/register")
                .then()
                .statusCode(200)
                .extract().jsonPath();
        assertThat("id").isNotEmpty();
        assertThat("token").isNotEmpty();
    }

    @Test
    void UnSuccessfulRegistration() {
        given()
                .contentType(JSON)
                .body("{\"email\":  \"sydney@fife\"}")
                .when()
                .post("/api/register")
                .then()
                .statusCode(400)
                .contentType(JSON)
                .body("error", is("Missing password"));
    }

    @Test
    void deleteUser() {
        given()
                .contentType(JSON)
                .when()
                .delete("/api/users/2")
                .then()
                .statusCode(204)
                .body(is(""));
    }

}
