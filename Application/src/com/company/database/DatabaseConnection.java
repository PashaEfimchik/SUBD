package com.company.database;

import com.company.models.Auto;
import com.company.models.User;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseConnection extends Configs{
    public static Connection dbConnection = null;
    private static Statement statement;
    private static final String url = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName + "?" + "autoReconnect=true&useSSL=false&useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        try {
            dbConnection = DriverManager.getConnection(url, dbUser, dbPass);
            if(dbConnection!=null){
                System.out.println("Connection success!");
            }
            else{
                System.out.println("Failed connection!");
            }
        }catch(Exception exception){
            exception.printStackTrace();
        }
        return dbConnection;
    }

    public static void requestSql(String sql) throws SQLException, ClassNotFoundException{
        statement = DatabaseConnection.getConnection().createStatement();
        statement.execute(sql);
    }

}
