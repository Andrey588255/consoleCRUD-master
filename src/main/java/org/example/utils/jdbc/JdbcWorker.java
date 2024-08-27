package org.example.utils.jdbc;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Properties;

public class JdbcWorker {
    private static Connection connection;

    private static String url;
    private static String password;
    private static String user;

    static {
        Properties dbCredentials = new Properties();
        try (FileInputStream fis = new FileInputStream("src/main/resources/DBCredentials.properties"))  {
             dbCredentials.load(fis);
             url = dbCredentials.getProperty("url");
             password = dbCredentials.getProperty("password");
             user = dbCredentials.getProperty("user");
             String driver = dbCredentials.getProperty("driver");

             Class.forName(driver);
            connection = getConnection();
        }catch (IOException | ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }

   public static void executeVoid(String query, Long id) throws SQLException {
        try (PreparedStatement prepSt = getConnection().prepareStatement(query)) {
            prepSt.setLong(1, id);
            prepSt.executeUpdate();
        }
    }
    public static <T> T executeGet(String query, Long id, ResultSetParser<T> parser){
        try (PreparedStatement prepSt = getConnection().prepareStatement(query)){
            if (id != null){
                prepSt.setLong(1, id);
            }

             return parser.parse(prepSt.executeQuery());

        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static Long executeSave(String query, PrepStSetter setter ) throws SQLException{
        try (PreparedStatement prepSt = getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
           setter.setUp(prepSt);

           prepSt.executeUpdate();
           ResultSet generatedKeys = prepSt.getGeneratedKeys();
           if (generatedKeys.next()){
               return generatedKeys.getLong(1);
           }
        }
        return null;
    }

   public static LocalDateTime convertToLocalDateTime(Timestamp dateToConvert){
        return new java.sql.Timestamp(
                dateToConvert.getTime()).toLocalDateTime();
   }

   private static Connection getConnection() throws SQLException{
        if (connection == null){
            connection = DriverManager.getConnection(url, user, password);
        }
        return connection;
   }

}