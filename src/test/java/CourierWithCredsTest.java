import io.restassured.RestAssured;
import io.restassured.response.Response;
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
        Courier courier = new Courier("Fifth_test_courier", "555555", "Тестовый_пятый");
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
                {"Fifth_test_test_courier", "55555", 404, "message", "Учетная запись не найдена"},
                {"Fifth_test_courier", "505050", 404, "message", "Учетная запись не найдена"},
                {null, "55555", 400, "message", "Недостаточно данных для входа"},
                {"Fifth_test_courier", null, 400, "message", "Недостаточно данных для входа"},
        };
    }

    @Test
    public void CreationOfCourier () {

        CourierWithCreds courierWithCreds = new CourierWithCreds(login, password);

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(courierWithCreds)
                .when()
                .post("/api/v1/courier/login");
        response.then().assertThat().body(expectedKey, equalTo(expectedMessage))
                .and()
                .statusCode(expectedStatusCode);

    }
}