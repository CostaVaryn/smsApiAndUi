package yandexMarket;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import core.BaseSelenideTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.util.Set;

import static com.codeborne.selenide.Selectors.shadowCss;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class MarketTest extends BaseSelenideTest {
    private static Set<Cookie> yandexCookie;

    @BeforeEach
    public void setUpExtension() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addExtensions(new File("C:\\Users\\cosva\\IdeaProjects\\smsApiAndUi\\src\\test\\resources\\modheader.crx"));
        Configuration.browserCapabilities = new DesiredCapabilities();
        Configuration.browserCapabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
        // addCookie();
    }

    private void addCookie() {
        Selenide.open("http://2ip.ru");
        Selenide.open("chrome-extension://idgpnmonknjnojddfkpgkljpfnnfcklj/popup.html");
        $x("//input[@placeholder='Name']").sendKeys("cookie");
        $x("//input[@placeholder='Value']").sendKeys("cookie");
        // change 'cookie' to original cookie
        $x("//input[@placeholder='Name']").click();
    }

    @Test
    public void checkAuth() {
        Selenide.open("https://market.yandex.ru/");
        Set<Cookie> cookie = WebDriverRunner.getWebDriver().manage().getCookies();
        yandexCookie = cookie;
        SelenideElement textInfo = $x("//h1/following::div[1]");
        $(shadowCss("#anyButton","#shadow-host")).click();
        String message = textInfo.getText();
        Assertions.assertNotEquals("Чтобы посмотреть историю заказов, авторизируйтесь", message);
    }
}
