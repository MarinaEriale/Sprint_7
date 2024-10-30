import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

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
                {"Second_test_courier", "222222", "Тестовый_второй", 409, "message", "Этот логин уже используется. Попробуйте другой."},
                {"Second_test_courier", "121212", "Тестовый_не_второй", 409, "message", "Этот логин уже используется. Попробуйте другой."},
                {null, null, "Тестовый_третий", 400, "message", "Недостаточно данных для создания учетной записи"},
        };
    }

    @Before
    public void firstCreation () {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
        Courier courier = new Courier("Second_test_courier", "222222", "Тестовый_второй");
        given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier");
    }

    @Test
    public void CreationOfCourier () {

        Courier courier = new Courier(login, password, firstName);

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier");
        response.then().assertThat().body(expectedKey, equalTo(expectedMessage))
                .and()
                .statusCode(expectedStatusCode);

    }

}