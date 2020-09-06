package nahubar65.gmail.com.score.db;

import org.bukkit.Bukkit;

import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

public class DBMapWriter {

    private String tableName;

    private Connection connection;

    private Statement statement;

    private String[] columns;

    private String id;

    public DBMapWriter(Connection connection, String tableName, String id){
        this.connection = connection;
        this.tableName = tableName;
        this.id = id;
        try {
            this.statement = connection.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        ResultSet resultSet;
        try {
            resultSet = statement.executeQuery("SELECT column_name " +
                    "FROM information_schema.columns " +
                    "WHERE table_name = '"+tableName+"' " +
                    "AND table_schema = 'SurvivalCore'");
            List<String> list = new ArrayList<>();
            while (resultSet.next()) {
                String column = resultSet.getString(1);
                list.add(column);
            }
            columns = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                columns[i] = list.get(i);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Optional<Map<String, Object>> get(String key){
        try {
            Map<String, Object> map = new HashMap<>();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM "+tableName+" WHERE ("+id+"='"+key+"')");
            resultSet.next();
            for (int i = 0; i < columns.length; i++) {
                Object o = resultSet.getObject(i+1);
                if (o instanceof BigDecimal) {
                    BigDecimal bigDecimal = (BigDecimal) o;
                    o = bigDecimal.doubleValue();
                }
                map.put(columns[i], o);
                Bukkit.getConsoleSender().sendMessage(o.toString() + columns[i]);
            }
            return Optional.of(map);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }

    public void set(String key, Map<String, Object> map) {
        try {
            String query = "INSERT INTO "+tableName+" VALUES (?,"+values(map.size())+")";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, key);
            List<Object> objects = new ArrayList<>(map.values());
            int count = 1;
            for (Object object : objects) {
                count++;
                preparedStatement.setObject(count, object);
            }
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private String values(int count){
        String values = "";
        for (int i = 0; i < count; i++) {
            String sign = "";
            if (i >= count-1) {
                sign = "?";
            } else if (i == 0) {
                sign = "?,";
            } else {
                sign = "?,";
            }
            values+=sign;
        }
        return values;
    }

    public List<String> getKeys(){
        List<String> stringList = new ArrayList<>();
        try {
            String query = "SELECT * FROM "+tableName;
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                String s = resultSet.getString(1);
                stringList.add(s);
            }
        } catch (SQLException throwables) {

        }
        return stringList;
    }
}