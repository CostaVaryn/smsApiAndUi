package sms;

import io.restassured.http.ContentType;
import phone.PhoneData;

import static io.restassured.RestAssured.given;

public class SmsApi {
    private final String URL = "https://smshub.org/stubs/handler_api.php";
    private final String API = "1523iu12h4kjh12iu431235iu1i2512352fw56";

    public String getAccountBalance() {
        String body = given().contentType(ContentType.HTML)
                .queryParam("apy_key", API)
                .queryParam("action", "getBalance")
                .get(URL).then().log().body()
                .extract().body().htmlPath().getString("body");
        String[] data = body.split(":");
        return data[1];
    }

    public PhoneData getPhone() {
        String body = given().contentType(ContentType.HTML)
                .queryParam("apy_key", API)
                .queryParam("action", "getNumber")
                .queryParam("service", "pm")
                .queryParam("country", "0")
                .get(URL).then().log().body()
                .extract().body().htmlPath().getString("body");
        String[] data = body.split(":");
        PhoneData phoneData = new PhoneData(data[1], data[2]);
        return phoneData;
    }
}
