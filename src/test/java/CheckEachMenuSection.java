import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.testng.ScreenShooter;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

/**
 * Created by lilu on 23/4/18.
 */
public class CheckEachMenuSection {
    @BeforeClass
    public void setup(){
        ChromeDriverManager.getInstance().setup();
        Configuration.browser = "chrome";
        ScreenShooter.captureSuccessfulTests = true;
        Configuration.reportsFolder = "reports";

    }

    @Test
    public void checkMenuSections() {

        //Login to admin. Check that menu items can be opened and headers are present
        LoginPage loginPage = open(LoginPage.URL,LoginPage.class);
        AdminPage adminPage = loginPage.loginToAdmin();
        adminPage.checkAllMenuItems();
    }
}
