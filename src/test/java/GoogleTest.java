import com.codeborne.selenide.Configuration;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.open;


/**
 * Created by lilu on 15/4/18.
 */
public class GoogleTest {

    @BeforeClass
    public static void setupClass() {
        ChromeDriverManager.getInstance().setup();
        Configuration.browser = "chrome";
    }


    @Test
    public void openGoogle() {
        open("https://en.wikipedia.org/wiki/Main_Page");
    }


}
