package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBSingleton {
    private static DBSingleton instance;
    private final Connection con;
    private DBSingleton() throws ClassNotFoundException, SQLException {
        String url = "jdbc:postgresql://localhost:5432/FastDelivery";
        String user = "postgres";
        String password = "1234";
        Class.forName("org.postgresql.Driver");
        con = DriverManager.getConnection(url, user, password);
    }

    public static DBSingleton getInstance() throws SQLException, ClassNotFoundException {
        if (instance == null) {
            instance = new DBSingleton();
        } else if (instance.getConnection().isClosed()) {
            instance = new DBSingleton();
        }

        return instance;
    }

    public Connection getConnection() {
        return con;
    }
}