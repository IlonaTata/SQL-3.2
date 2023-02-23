package data;


import lombok.SneakyThrows;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlHelper {
    private static QueryRunner runner = new QueryRunner();

    private SqlHelper() {
    }
    private static Connection getConn() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
    }


    public static DataHelper.VerificationCode getVerificationCode() {
        var codeSql = "SELECT code From auth_codes ORDER BY created DESC LIMIT 1";
        try (var conn = getConn()) {
            var code = runner.<String>query(conn, codeSql, new ScalarHandler<String>());
            return new DataHelper.VerificationCode(code);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    @SneakyThrows
    public static void cleanDataBase() {
        var connection = getConn();
        runner.execute(connection, "DELETE FROM auth_codes");
        runner.execute(connection, "DELETE FROM card_transactions");
        runner.execute(connection, "DELETE FROM cards");
        runner.execute(connection, "DELETE FROM users");
    }
}