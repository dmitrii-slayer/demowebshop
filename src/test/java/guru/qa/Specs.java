package guru.qa;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.with;

public class Specs {

    public static RequestSpecification requestSpec = with()
            .log().all()
//            .contentType("application/x-www-form-urlencoded; charset=UTF-8");
            .contentType(ContentType.URLENC)
            .header("Connection", "close");
}
