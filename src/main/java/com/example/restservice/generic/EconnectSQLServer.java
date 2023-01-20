package com.example.restservice.generic;

import java.sql.Connection;
import java.sql.DriverManager;

public class EconnectSQLServer {

    public static Connection connexion() {
        Connection con = null;
        try {
            String servername = "LALAINA-MINO\\SQLEXPRESS";
            String dbName = "AmbatovySQLServer";
            String userName = "LALAINA-MINO\\Karen";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
            String connectionString = "jdbc:sqlserver://" + servername + ":1433 ; Database=" + dbName + "; integratedSecurity=true ;trustServerCertificate=true";
            System.out.println(connectionString);
            con = (Connection) DriverManager.getConnection(connectionString);
            System.out.println("Connectee ✔✔");
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            System.err.println("Tsy mety mi-connecte❌");
        }
        return con;
    }

    // Close Connection 
    public static void closeConnection(Connection con) throws Exception {
        try {
            if (con != null) {
                con.close();
            }
        } catch (Exception e) {
            throw e;
        }
    }
}
