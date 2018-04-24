import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.*;

public class AdminPage {

    public AdminPage checkHeader() {
        $(byXpath("//h1")).should(Condition.exist);
        return page(AdminPage.class);
    }

    //Iterates through all the menu and submenu items
    public void checkAllMenuItems() {
        ElementsCollection menuUrls = $$(byXpath("//ul[@id = 'box-apps-menu']//a"));
        //List of menu urls
        List<String> hrefs = new ArrayList<>();
        List<String> subHrefs = new ArrayList<>();
        //Cycle which goes through all menu items and selects url
        for (SelenideElement menuButton : menuUrls) {
            hrefs.add(menuButton.getAttribute("href"));
        }
        //Cycle which goes through all urls and checks headers.
        for (String href : hrefs) {
            open(href);
            checkHeader();
            //Also collect submenu urls which are not selected
            ElementsCollection subMenuUrls = $$(byXpath("//ul[@class='docs']//li[not(@class = 'selected')]//a"));
            for (SelenideElement subMenuButton : subMenuUrls) {
                subHrefs.add(subMenuButton.getAttribute("href"));
            }

        }
        //Cycle which goes through all submenu urls and checks headers.
        for (String href : subHrefs) {
            open(href);
            checkHeader();
        }


    }


}





