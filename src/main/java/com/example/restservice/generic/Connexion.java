package com.example.restservice.generic;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connexion {
    
    public static Connection getConnexion(){
        Connection connect = null;
        try{
            System.out.println("getConnexion Try to connect");
            Class.forName("org.postgresql.Driver");
            connect = DriverManager.getConnection("jdbc:postgresql://containers-us-west-63.railway.app:7844/railway","postgres","ZxdGIUkUQgff5awb9j6B");
            System.out.println("getConnexion Connected");
        }
        catch(Exception e){
            
        }
        return connect;
    }
}
