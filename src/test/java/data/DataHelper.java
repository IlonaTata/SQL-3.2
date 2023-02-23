package data;

import com.github.javafaker.Faker;
import lombok.*;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;

public class DataHelper {

    private static Faker faker = new Faker(new Locale("en"));


    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        String login;
        String password;
    }

   @Value
    public static class VerificationCode {
         String code;
    }

    public static AuthInfo getAuthInfoWithTestData() {
       return new AuthInfo("vasya","qwerty123");
    }


    public static String generateRandomLogin() {
        return faker.name().username();
    }

    public static String generateRandomPassword() {
        return faker.internet().password();
    }

    public static AuthInfo generateRandomUser() {
        return new AuthInfo(generateRandomLogin(), generateRandomPassword());
    }

    public static VerificationCode generateRandomVerificationCode() {
        return new VerificationCode(faker.numerify("12345"));
    }
    public static void clearAuthCodesTable() {
        val deleteAuthCodeSQL = " DELETE FROM auth_codes;";
        val runner = new QueryRunner();
        try {
            try (val conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "app", "pass")) {
               runner.execute(conn, deleteAuthCodeSQL, new ScalarHandler<>());
           }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

}
}
