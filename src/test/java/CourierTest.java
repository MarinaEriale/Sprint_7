import io.restassured.RestAssured;
import io.restassured.response.Response;
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
    public void creationOfCourier () {
        Courier courier = new Courier("test_courier_4", "444444", "Тестовый_четвертый");

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier");
        response.then().assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(201);

    }

    @Test
    public void loginOfCourier () {
        CourierWithCreds courierWithCreds = new CourierWithCreds("test_courier_4", "444444");
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(courierWithCreds)
                .when()
                .post("/api/v1/courier/login");
        response.then().assertThat().body("id", notNullValue())
                .and()
                .statusCode(200);
    }
}