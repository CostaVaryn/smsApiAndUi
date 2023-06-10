package sms;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import core.BaseSelenideTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import phone.NumberPage;
import phone.PhoneData;

import java.io.File;

import static com.codeborne.selenide.Selenide.$x;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SmsTest extends BaseSelenideTest {

    @BeforeEach
    public void setUpExtension() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addExtensions(new File("C:\\Users\\cosva\\IdeaProjects\\smsApiAndUi\\src\\test\\resources\\modheader.crx"));
        Configuration.browserCapabilities = new DesiredCapabilities();
        Configuration.browserCapabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
        addCookie();
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
    public void checkBalance() {
        Selenide.open("http://smshub.org/ru/activations");
        SmsApi smsApi = new SmsApi();
        NumberPage numberPage = new NumberPage();
        String balanceApi = smsApi.getAccountBalance();
        String balanceUi = numberPage.getAccountBalance();
        assertTrue(balanceUi.contains(balanceApi));
        int i = 0;

        /**
         * SmsApi smsApi = new SmsApi();
         * smsApi.getAccountBalance();
         * smsApi.getPhone();
         */

    }

    @Test
    public void numberCheck() {
        Selenide.open("http://smshub.org/ru/activations");
        SmsApi smsApi = new SmsApi();
        NumberPage numberPage = new NumberPage();
        PhoneData phoneData = smsApi.getPhone();
        String apiId = phoneData.getNumberId();
        String apiPhone = phoneData.getPhoneNumber();

        String uiId = numberPage.getPhoneId();
        String uiPhone = numberPage.getPhoneNumber();
        assertEquals(apiId, uiId);
        assertEquals(apiPhone, uiPhone);
        int i = 0;
    }
}
