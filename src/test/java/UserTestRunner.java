import org.apache.commons.configuration.ConfigurationException;
import org.testng.annotations.Test;

import java.io.IOException;

public class UserTestRunner {
    @Test(priority = 1)
    public void login() throws IOException, ConfigurationException {
        User user=new User();
        UserModel model=new UserModel();
        model.setEmail("salman@roadtocareer.net");
        model.setPassword("1234");
        String token=User.callingLoginAPI(model);
        Utils.setEnvVar("token",token);

    }
}
