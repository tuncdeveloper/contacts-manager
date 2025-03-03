package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDb {

    private static final String URL = "jdbc:postgresql://localhost/contacts_manager";
    private static final String USER = "postgres";
    private static final String PASSWORD = "test";

    private static ConnectDb connectDb;

    private Connection connection;

    private ConnectDb(){

    }

    public Connection getConnection() throws SQLException {

        if (connection == null || connection.isClosed()) {
            connection=DriverManager.getConnection(URL,USER,PASSWORD);
        }
        return connection;
    }


    public static ConnectDb instance(){
        if (connectDb == null) {
            return new ConnectDb();
        }
        return connectDb;
    }


}
