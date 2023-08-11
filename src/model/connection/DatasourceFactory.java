package model.connection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatasourceFactory {
    public static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
    public static final String DB_URL = "jdbc:oracle:thin:@//192.168.253.91:1521/komplat";
    public static final String DB_LOGIN = "smataeva";
    public static final String DB_PASSWORD = "smataeva";
    public static int DB_POOL_SIZE = 16;


    private Connection connection;

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(DB_DRIVER);
        return DriverManager.getConnection(DB_URL, DB_LOGIN, DB_PASSWORD);
    }
}
