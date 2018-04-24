import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.testng.ScreenShooter;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.io.File;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;

/**
 * Created by lilu on 22/4/18.
 */
public class CartOperations {

    @BeforeClass
    public static void setup(){
        ChromeDriverManager.getInstance().setup();
        Configuration.browser = "chrome";
        ScreenShooter.captureSuccessfulTests = true;
        Configuration.reportsFolder = "reports";


    }

    @Test
    public void productOperations () {

        // Create product on admin side with price $3.00

        open("http://localhost/litecart/admin");

        //Login to admin
        $(byXpath("//input[@name = 'username']")).setValue("admin");
        $(byXpath("//input[@name = 'password']")).setValue("admin");
        $(byXpath("//button[@name = 'login']")).click();

        //Open Catalog
        $(byXpath("//span[text() = 'Catalog']")).click();

        //Add new product with image (duck)
        $(byXpath("//a[contains(.,'Add New Product')]")).click();
        $(byXpath("//label[contains(.,'Enabled')]")).click();
        $(byXpath("//input[@name = 'name[en]']")).setValue("New Duck");
        $(byXpath("//input[@name = 'quantity']")).setValue("20");


        //Using relative path for image
        File file = new File (this.getClass().getClassLoader().getResource("sports.jpg").getFile());
        $(byXpath("//input[@name = 'new_images[]']")).setValue(file.getAbsolutePath());

        //Go to information tab and add some required data
        $(byXpath("//a[@href='#tab-information']")).click();
        $(byXpath("//select[@name = 'manufacturer_id']")).selectOption(1);
        $(byXpath("//input[@name = 'short_description[en]']")).setValue("short description");

        //Go to prices tab and add some required data
        $(byXpath("//a[@href = '#tab-prices']")).click();
        $(byXpath("//input[@name = 'prices[USD]']")).setValue("3");
        $(byXpath("//select[@name = 'purchase_price_currency_code']")).selectOptionByValue("USD");
        $(byXpath("//button[@name = 'save']")).click();

        //Check that product is saved
        $(byXpath("//span[text() = 'Catalog']")).click();
        $(byXpath("//td[@id = 'content']")).shouldHave(Condition.text("New Duck"));

        //Check created product on the site
        open("http://localhost/litecart/en/");
        $(byXpath("//a[@title = 'New Duck']")).click();

        //Add newly created product to cart
        $(byXpath("//button[@name = 'add_cart_product']")).click();
        $(byXpath("//span[@class = 'quantity']")).shouldBe(Condition.text("1"));

        //Check that item is added to cart
        $(byXpath("(//a[text() = 'Home'])[1]")).click();


        //Open another product's page and add it to cart
        $(byXpath("//a[@title = 'Green Duck']")).click();
        $(byXpath("//button[@name = 'add_cart_product']")).click();

        //Check that quantity of items is 2
        $$(byXpath("//span[@class = 'quantity']")).shouldBe(CollectionCondition.texts("2"));

        //Checkout
        $(byXpath("//a[text() = 'Checkout »']")).click();
        $(byXpath("(//li[@class = 'shortcut'])[1]")).click();

        //Remove first item from the cart and check that table has 6 lines including title line
        $(byXpath("//button[@name = 'remove_cart_item']")).click();
        $$(byXpath("//table[@class = 'dataTable rounded-corners']/tbody/tr")).shouldHaveSize(6);

        //Remove another item from cart and check that table is disappeared
        // and text that there is no items in cart are left
        $(byXpath("//button[@name = 'remove_cart_item']")).click();
        $(byXpath("//h2[text() = 'Order Summary']")).shouldNotBe(Condition.exist);
        $(byXpath("//em[text() = 'There are no items in your cart.']")).should(Condition.visible);

    }

}
