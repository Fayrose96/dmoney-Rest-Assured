import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class Utils {
    public static void setEnvVar(String key,String value) throws ConfigurationException {
        PropertiesConfiguration config=new PropertiesConfiguration("./src/test/resources/config.properties");
        config.setProperty(key,value);
        config.save();
    }
    public static int generateRandomId(int min, int max){
        double randId= Math.random()*(max-min)+min;
        return (int) randId;
    }

    public static void main(String[] args) {
        int rand= generateRandomId(1000,9999);
        System.out.println(rand);
    }
}
