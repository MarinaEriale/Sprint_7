import clients.CourierClient;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.Courier;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(Parameterized.class)
public class CourierParameterizedTest {
    private final String login;
    private final String password;
    private final String firstName;
    private final int expectedStatusCode;
    private final String expectedKey;
    private final String expectedMessage;

    public CourierParameterizedTest(String login, String password, String firstName, int expectedStatusCode, String expectedKey, String expectedMessage) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.expectedStatusCode = expectedStatusCode;
        this.expectedKey = expectedKey;
        this.expectedMessage = expectedMessage;
    }

    @Parameterized.Parameters
    public static Object[][] courierData() {
        return new Object[][] {
                {"Seventh_test_courier", "777777", "Тестовый_седьмой", 409, "message", "Этот логин уже используется. Попробуйте другой."},
                {"Seventh_test_courier", "121212", "Тестовый_не_седьмой", 409, "message", "Этот логин уже используется. Попробуйте другой."},
                {null, null, "Тестовый_третий", 400, "message", "Недостаточно данных для создания учетной записи"},
        };
    }

    @Before
    public void firstCreation () {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
        Courier courier = new Courier("Seventh_test_courier", "777777", "Тестовый_седьмой");
        given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier");
    }

    @Test
    @DisplayName("Send POST request to /api/v1/courier and compare Status Codes and messages with expected")
    public void CreationOfCourier () {

        Courier courier = new Courier(login, password, firstName);
        CourierClient courierClient = new CourierClient();

        Response response = courierClient.create(courier);
        response.then().assertThat().statusCode(expectedStatusCode)
                .and()
                .body(expectedKey, equalTo(expectedMessage));

    }

}