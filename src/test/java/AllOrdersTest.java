import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class AllOrdersTest {
    @Before
    public void setUpUrl () {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    @Step ("Send GET request to /api/v1/orders and compare Status Code with 200")
    public void gettingOrdersTest () {
        Response response = given()
                .header("Content-type", "application/json")
                .when()
                .get("/api/v1/orders");
        response.then().assertThat().statusCode(200);
    }
}