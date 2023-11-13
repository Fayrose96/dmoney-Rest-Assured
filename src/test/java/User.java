import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


import static io.restassured.RestAssured.given;

public class User {
    public static String callingLoginAPI(UserModel model) throws ConfigurationException {
        RestAssured.baseURI="http://dmoney.roadtocareer.net";
        Response res= given().contentType("application/json")
                .body(model)
                .when()
                .post("/user/login")
                .then().assertThat()
                .statusCode(200)
                .extract().response();
        System.out.println(res.asString());

        JsonPath jsonPath=res.jsonPath();
        String token = jsonPath.get("token");
        return token;
    }


    public void createUser(String token,UserModel userModel) throws ConfigurationException {
            RestAssured.baseURI= "http://dmoney.roadtocareer.net";


            Response res= given().contentType("application/json")
                    .header("Authorization",token)
                    .header("X-AUTH-SECRET-KEY","ROADTOSDET")
                    .body(userModel)
                    .when()
                    .post("/user/create")
                    .then().assertThat()
                    .statusCode(201)
                    .extract().response();
            System.out.println(res.asString());

        JsonPath jsonPath=res.jsonPath();
        int userId = jsonPath.get("user.id");
        Utils.setEnvVar("userId", String.valueOf(userId));

    }
    public static void searchUser(String token, String userId) throws InterruptedException {
        Thread.sleep(3000);
        RestAssured.baseURI="http://dmoney.roadtocareer.net";
        Response res=given().contentType("application/json").header("Authorization",token)
                .when()
                .get("/user/search/id/"+userId);
        System.out.println(res.asString());

    }
}
