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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import ru.bagmet.demowebshop.config.DemoShopConfig;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.given;

public class TestBaseDW {

    public static DemoShopConfig shopConfig = ConfigFactory.create(DemoShopConfig.class, System.getProperties());

    public String basePathProduct = "/addproducttocart";
    protected int productsToBuy;
    protected int productsInCart;

    protected static String authCookie;
    protected static String customerCookie;

    protected RequestSpecification requestSpec = new RequestSpecBuilder()
            .setContentType("application/x-www-form-urlencoded; charset=UTF-8")
            .setAccept(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    protected ResponseSpecification responseSpec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .log(LogDetail.BODY)
            .expectBody("success", is(true))
            .build();

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "http://demowebshop.tricentis.com";
    }

    @BeforeEach
    protected void doLogin() {
        Response loginResponse = given()
                .spec(requestSpec)
                .formParam("Email", shopConfig.getUserLogin())
                .formParam("Password", shopConfig.getUserPassword())
                .when()
                .post("/login")
                .then()
                .statusCode(302)
                .cookie("NOPCOMMERCE.AUTH", notNullValue())
                .extract().response();

        authCookie = loginResponse.getCookie("NOPCOMMERCE.AUTH");
        customerCookie = loginResponse.getCookie("Nop.customer");
    }

    @AfterEach
    void logOut() {
        given()
                .spec(requestSpec)
                .get("/logout")
                .then()
                .statusCode(200);

        authCookie = null;
        customerCookie = null;
        System.out.println(authCookie);
    }


}
