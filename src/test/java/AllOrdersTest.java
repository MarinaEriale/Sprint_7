import clients.OrderClient;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
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
    @DisplayName("Send GET request to /api/v1/orders and compare Status Code with 200")
    public void gettingOrdersTest () {
        OrderClient orderClient = new OrderClient();

        Response response = orderClient.get();
        response.then().assertThat().statusCode(200);
    }
}