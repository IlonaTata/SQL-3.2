package page;

import com.codeborne.selenide.SelenideElement;
import data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private static SelenideElement loginField = $("[data-test-id=login] input");
    private static SelenideElement passwordField = $("[data-test-id=password] input");
    public static SelenideElement loginButton = $("[data-test-id=action-login]");

    private static SelenideElement errorNotification = $(" [data-test-id=error-notification]");
    public static VerificationPage validLogin(DataHelper.AuthInfo info) {
        loginField.setValue(info.getLogin());
        passwordField.setValue(info.getPassword());
        loginButton.click();
        return new VerificationPage();
    }
    public void verifyErrorNotificationVisibility (){
        errorNotification.shouldBe(visible);


    }}

