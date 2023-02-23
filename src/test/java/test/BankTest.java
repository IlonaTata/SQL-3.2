package test;

import com.codeborne.selenide.SelenideElement;
import data.DataHelper;
import data.SqlHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import page.DashboardPage;
import page.LoginPage;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;



public class BankTest {
    DataHelper.AuthInfo user = new DataHelper.AuthInfo("vasya", "qwerty123");
    DataHelper.AuthInfo notUser = new DataHelper.AuthInfo("asya", "qwerty");
    SelenideElement loginButton = $("[data-test-id=action-login]");
   //@AfterAll
    //static void tearDown() { DataHelper.clearAuthCodesTable(); }

    @Test
    @DisplayName("Should successfully login to dashboard with exist login and password")
    void shouldValidLogin() {
        var loginPage=open("http://localhost:9999", LoginPage.class);
        var authInfo = user;
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verifyVerificationPageVisibility();
        var verificationCode = SqlHelper.getVerificationCode();
        verificationPage.validVerify(verificationCode.getCode());

    }

    @Test
    @DisplayName("Should get error if user is not exist")
    void shouldInvalidLogin() {
        var loginPage=open("http://localhost:9999", LoginPage.class);
        var authInfo = notUser;
        loginPage.validLogin(authInfo);
        loginPage.verifyErrorNotificationVisibility();
    }

    @Test
    void shouldBeBlocked() {
        var loginPage=open("http://localhost:9999", LoginPage.class);
        var authInfo = notUser;
        loginPage.validLogin(authInfo);
        loginPage.verifyErrorNotificationVisibility();
        loginButton.click();
        loginPage.verifyErrorNotificationVisibility();
        loginButton.click();
        loginPage.verifyErrorNotificationVisibility();


    }
}
