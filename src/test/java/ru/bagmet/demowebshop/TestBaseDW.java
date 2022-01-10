package ru.bagmet.demowebshop;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import io.restassured.RestAssured;
import ru.bagmet.demowebshop.config.DemoShopConfig;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;

public class TestBaseDW {

    public static DemoShopConfig shopConfig = ConfigFactory.create(DemoShopConfig.class, System.getProperties());

    public String basePathProduct = "/addproducttocart";

    int productsToBuy;
    int productsInCart;

    @BeforeAll
    static void setUp(){
        RestAssured.baseURI = "http://demowebshop.tricentis.com";

        RequestSpecification requestSpec = new RequestSpecBuilder()
                .setContentType("application/x-www-form-urlencoded; charset=UTF-8")
                .setAccept(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();

        ResponseSpecification responseSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectBody("success", is("true"))
                .build();
    }

    void doLogin() {
        Response loginResponse = given()
                .spec(requestSpecification)
                .formParam("Email", shopConfig.getUserLogin())
                .formParam("Password", shopConfig.getUserPassword())
                .when()
                .post("/login")
                .then()
                .statusCode(302)
                .cookie("NOPCOMMERCE.AUTH", notNullValue())
                .extract().response();

        String authCookie = loginResponse.getCookie("NOPCOMMERCE.AUTH");
        String customerCookie = loginResponse.getCookie("Nop.customer");
    }

    @AfterAll
    static void tearDown(){
        String authCookie = "";
        String customerCookie = "";
    }

}
