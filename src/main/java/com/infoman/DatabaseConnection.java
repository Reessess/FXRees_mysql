package com.infoman;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    private String url = "jdbc:mysql://localhost:3306/infoman_database_db";

    private String user = "application";
    private String password = "12345";
    public Connection connection;

    public DatabaseConnection(){
        try{
            connection = DriverManager.getConnection(url, user, password);
                    System.out.println("Connected Successfully");
        }catch(Exception e){
           e.printStackTrace();
        }
    }
}
