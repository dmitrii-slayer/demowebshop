package guru.qa;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static guru.qa.Specs.requestSpec;
import static io.restassured.RestAssured.given;
import static io.restassured.config.EncoderConfig.encoderConfig;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.Matchers.is;

public class DemoWebShopTests {

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "https://demowebshop.tricentis.com/";
        RestAssured.config = RestAssured.config().encoderConfig(encoderConfig().defaultContentCharset(UTF_8));
    }


    @ValueSource(strings = {
            "1", "2", "3", "4"
    })
    @ParameterizedTest
    @DisplayName("Проверка недоступности голосования для неавторизованного пользователя")
    public void unauthorizedUserCanNotVote(String answerId) {

        String data = "pollAnswerId=" + answerId;

        given()
                .spec(requestSpec)
                .body(data)
        .when()
                .post("/poll/vote")
        .then()
                .statusCode(200)
                .body("error", is("Only registered users can vote."));
    }

    @ValueSource(strings = {
            "1", "4"
    })
    @ParameterizedTest
    @DisplayName("Проверка доступности добавления товара в корзину для неавторизованного пользователя")
    public void unauthorizedUserCanAddProductToCart(String productQuantity) {

        String data = "addtocart_45.EnteredQuantity=" + productQuantity;

        given()
                .spec(requestSpec)
                .body(data)
        .when()
                .post("/addproducttocart/details/45/1")
        .then()
                .statusCode(200)
                .body("success", is(true),
                        "message", is("The product has been added to your <a href=\"/cart\">shopping cart</a>"),
                        "updatetopcartsectionhtml", is(String.format("(%s)", productQuantity))
                );
    }
}
