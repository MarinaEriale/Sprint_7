package clients;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.Courier;
import models.CourierWithCreds;

import static io.restassured.RestAssured.given;


public class CourierClient {
    @Step ("Get response for creation of a new courier")
    public Response create (Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier");
    }

    @Step ("Get response for login of existing courier")
    public Response login (CourierWithCreds courierWithCreds) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courierWithCreds)
                .when()
                .post("/api/v1/courier/login");
    }
}
