import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class User {
    Properties prop;
    public User() throws IOException {
        initConfig();
    }
    public void initConfig() throws IOException {
        prop=new Properties();
        FileInputStream fileInputStream=new FileInputStream("./src/test/resources/config.properties");
        prop.load(fileInputStream);
    }

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

    @Test
    public void createUser() throws ConfigurationException {
            RestAssured.baseURI= "http://dmoney.roadtocareer.net";
            Faker faker=new Faker();
            UserModel userModel=new UserModel();
            userModel.setName(faker.name().fullName());
            userModel.setEmail(faker.internet().emailAddress());
            userModel.setPassword("1273");
            userModel.setPhone_number("01598"+Utils.generateRandomId(100000,999999));
            userModel.setNid("987654321");
            userModel.setRole("Customer");

            Response res= given().contentType("application/json")
                    .header("Authorization",prop.getProperty("token"))
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
    @Test
    public void searchUser() throws InterruptedException {
        Thread.sleep(3000);
        RestAssured.baseURI="http://dmoney.roadtocareer.net";
        Response res=given().contentType("application/json").header("Authorization",prop.getProperty("token"))
                .when()
                .get("/user/search/id/"+prop.getProperty("userId"));
        System.out.println(res.asString());

    }
}
