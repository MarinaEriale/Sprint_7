import clients.CourierClient;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.Courier;
import models.CourierWithCreds;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CourierTest {
    @Before
    public void setUp () {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Send POST request to /api/v1/courier and compare Status Code and response message with expected")
    public void creationOfCourier () {
        Courier courier = new Courier("test_courier_6", "666666", "Тестовый_шестой");
        CourierClient courierClient = new CourierClient();

        Response response = courierClient.create(courier);
        response.then().assertThat().statusCode(201)
                .and()
                .body("ok", equalTo(true));

    }

    @Test
    @DisplayName("Send POST request to /api/v1/courier/login and compare Status Code and response message with expected")
    public void loginOfCourier () {
        CourierWithCreds courierWithCreds = new CourierWithCreds("test_courier_6", "666666");
        CourierClient courierClient = new CourierClient();

        Response response = courierClient.login(courierWithCreds);
        response.then().assertThat().statusCode(200)
                .and()
                .body("id", notNullValue());
    }
}