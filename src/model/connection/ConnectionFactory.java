package model.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static model.connection.DatasourceFactory.*;

public class ConnectionFactory {
    private ConnectionFactory(){
    }

    static Connection createConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_LOGIN, DB_PASSWORD);

    }
}
