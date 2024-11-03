import clients.CourierClient;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.Courier;
import models.CourierWithCreds;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(Parameterized.class)
public class CourierWithCredsTest {
    private final String login;
    private final String password;
    private final int expectedStatusCode;
    private final String expectedKey;
    private final String expectedMessage;

    public CourierWithCredsTest(String login, String password, int expectedStatusCode, String expectedKey, String expectedMessage) {
        this.login = login;
        this.password = password;
        this.expectedStatusCode = expectedStatusCode;
        this.expectedKey = expectedKey;
        this.expectedMessage = expectedMessage;
    }

    @Before
    public void firstCreation () {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
        Courier courier = new Courier("Eighth_test_courier", "888888", "Тестовый_восьмой");
        given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier");
    }

    @Parameterized.Parameters
    public static Object[][] courierData() {
        return new Object[][] {
                {"Eighth_test_test_courier", "888888", 404, "message", "Учетная запись не найдена"},
                {"Eighth_test_courier", "808080", 404, "message", "Учетная запись не найдена"},
                {null, "888888", 400, "message", "Недостаточно данных для входа"},
                {"Eighth_test_courier", null, 400, "message", "Недостаточно данных для входа"},
        };
    }

    @Test
    @DisplayName("Send POST request to /api/v1/courier/login and compare Status Codes and messages with expected")
    public void loginOfCourier () {

        CourierWithCreds courierWithCreds = new CourierWithCreds(login, password);
        CourierClient courierClient = new CourierClient();

        Response response = courierClient.login(courierWithCreds);
        response.then().assertThat().statusCode(expectedStatusCode)
                .and()
                .body(expectedKey, equalTo(expectedMessage));

    }
}