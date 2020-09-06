package nahubar65.gmail.com.score.db;

import com.zaxxer.hikari.HikariDataSource;
import java.sql.*;

public class MySQLConnector {

    private HikariDataSource dataSource;

    public MySQLConnector(String pass, String user, String ip, String database) {
        this.dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://" + ip + ":3306/" + database);
        dataSource.setUsername(user);
        dataSource.setPassword(pass);
    }

    public Connection getConnection(){
        try {
            return dataSource.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}