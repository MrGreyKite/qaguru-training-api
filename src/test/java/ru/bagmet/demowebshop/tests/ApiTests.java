package ru.bagmet.demowebshop.tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.bagmet.demowebshop.TestBaseDW;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

public class ApiTests extends TestBaseDW {

    @Test
    @Tag("demowebshop")
    void addOneProductToCardTest(){
        Map<String, Integer> paramsForFirstPurchase = new HashMap<>();
        paramsForFirstPurchase.put("product_attribute_74_5_26", 81);
        paramsForFirstPurchase.put("product_attribute_74_6_27", 84);
        paramsForFirstPurchase.put("product_attribute_74_3_28", 87);
        paramsForFirstPurchase.put("product_attribute_74_8_29", 89);
        paramsForFirstPurchase.put("addtocart_74.EnteredQuantity", 1);

        given()
                .spec(requestSpecification)
                .basePath(basePathProduct)
                .pathParam("productId",74)
                .formParams(paramsForFirstPurchase)
                .when()
                .post("/details/{productId}/1")
                .then()
                .spec(responseSpecification)
                .body("message",is("The product has been added to your <href=\\\"/cart\\\">shopping cart</a>"),
                        "updateflyoutcartsectionhtml", containsString("Build your own expensive computer"));

    }

    @Test
    @Tag("demowebshop")
    void addProductToNotEmptyCardTest(){

    }

    @Test
    @Tag("demowebshop")
    @DisplayName("Delete from cart using API + UI")
    void deleteFromCartTest(){

    }


}
