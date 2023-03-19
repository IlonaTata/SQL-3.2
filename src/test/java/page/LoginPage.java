package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private SelenideElement loginField = $("[data-test-id=login] input");
    private SelenideElement passwordField = $("[data-test-id=password] input");
    public SelenideElement loginButton = $("[data-test-id=action-login]");

    private SelenideElement errorNotification = $(" [data-test-id=error-notification]");
    public VerificationPage validLogin(DataHelper.AuthInfo info) {
        loginField.setValue(info.getLogin());
        passwordField.setValue(info.getPassword());
        loginButton.click();
        return new VerificationPage();
    }
    public void verifyErrorNotificationVisibility (){
        errorNotification.shouldBe(visible);
    }
    public void clickButton() {
        loginButton.click();

    }
    public void blockedNotification (){
        errorNotification.shouldHave(Condition.text("Пользователь заблокирован"));
    }

}

