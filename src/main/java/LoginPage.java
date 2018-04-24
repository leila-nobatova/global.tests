import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;


public class LoginPage {
    public final static String URL = "http://localhost/litecart/admin";


    public AdminPage loginToAdmin() {
        $(byXpath("//input[@name = 'username']")).setValue("admin");
        $(byXpath("//input[@name = 'password']")).setValue("admin");
        $(byXpath("//button[@name = 'login']")).click();
        return page(AdminPage.class);
    }


}
