import clients.OrderClient;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.Order;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderTest {
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final int rentTime;
    private final String deliveryDate;
    private final String comment;
    private final List<String> color;

    public OrderTest(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, List<String> color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color= color;
    }

    @Before
    public void setUpUrl () {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Parameterized.Parameters
    public static Object[][] orderData() {
        return new Object[][] {
                {"Пчела", "Полосатая", "ул. Сотовая, д.100, улей 5", "4", "71471471471", 5, "03.11.2024", "Привозите скорее", List.of("Black")},
                {"Пчела", "Рыжая", "ул. Сотовая, д.100, улей 8", "4", "71471471471", 5, "03.11.2024", "Привозите скорее", List.of("Gray")},
                {"Пчела", "Полосатая", "ул. Сотовая, д.100, улей 5", "4", "71471471471", 5, "03.11.2024", "Привозите скорее", List.of("Black", "Gray")},
                {"Пчела", "Полосатая", "ул. Сотовая, д.100, улей 5", "4", "71471471471", 5, "03.11.2024", "Привозите скорее", Collections.emptyList()},
        };
    }

    @Test
    @DisplayName("Send POST request to /api/v1/orders and check that Status Code is 201 and track is not null")
    public void CreationOfOrder () {

        Order order = new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        OrderClient orderClient = new OrderClient();

        Response response = orderClient.create(order);
        response.then().assertThat().statusCode(201)
                .and()
                .body("track", notNullValue());

    }


}