import org.apache.commons.configuration.ConfigurationException;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;
import java.io.IOException;

public class UserTestRunner extends Setup {
    @Test(priority = 1)
    public void login() throws IOException, ConfigurationException {
        User user=new User();
        UserModel model=new UserModel();
        model.setEmail("salman@roadtocareer.net");
        model.setPassword("1234");
        String token=User.callingLoginAPI(model);
        Utils.setEnvVar("token",token);

    }
    @Test(priority = 2)
    public void createUser() throws IOException, ConfigurationException {
        User user=new User();
        Faker faker=new Faker();
        UserModel userModel=new UserModel();
        userModel.setName(faker.name().fullName());
        userModel.setEmail(faker.internet().emailAddress());
        userModel.setPassword("1273");
        userModel.setPhone_number("01598"+Utils.generateRandomId(100000,999999));
        userModel.setNid("987654321");
        userModel.setRole("Customer");
        user.createUser(prop.getProperty("token"), userModel);
    }
    @Test(priority = 3)
    public void searchUser() throws IOException, InterruptedException {
       User user=new User();
       User.searchUser(prop.getProperty("token"),prop.getProperty("userId"));
    }
}
