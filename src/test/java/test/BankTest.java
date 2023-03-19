package test;

import com.codeborne.selenide.SelenideElement;
import data.DataHelper;
import data.SqlHelper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import page.DashboardPage;
import page.LoginPage;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;



public class BankTest {


    @AfterAll
    static void tearDown() {
        SqlHelper.cleanDataBase();
    }

    static void cleanBase() {
        SqlHelper.cleanDataBase();
    }

    @Test
    @DisplayName("Should successfully login to dashboard with exist login and password")
    void shouldValidLogin() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfoWithTestData();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verifyVerificationPageVisibility();
        var verificationCode = SqlHelper.getVerificationCode();
        verificationPage.validVerify(verificationCode.getCode());

    }

    @Test
    @DisplayName("Should get error if user is not exist")
    void shouldInvalidLogin() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getInvalidUser();
        loginPage.validLogin(authInfo);
        loginPage.verifyErrorNotificationVisibility();
    }

    @Test
    void shouldBeBlocked() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getInvalidUser();
        loginPage.validLogin(authInfo);
        loginPage.verifyErrorNotificationVisibility();
        loginPage.clickButton();
        loginPage.verifyErrorNotificationVisibility();
        loginPage.clickButton();
        loginPage.blockedNotification();

    }
}
