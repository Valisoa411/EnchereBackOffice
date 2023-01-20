package com.example.restservice.model;

import com.example.restservice.generic.Connexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;


public class Statistic {

    public static HashMap<String,Double> getChiffreAffaireByCategorie() throws Exception{
        Connection connection = null;
        try {
            HashMap<String,Double> stat = new HashMap<>();
            connection = Connexion.getConnexion();
            String sql = "SELECT * FROM v_all_recette_by_categorie";
            PreparedStatement psmt = connection.prepareStatement(sql);
            ResultSet resultSet = psmt.executeQuery();
            while(resultSet.next()){
                Categorie categorie = new Categorie();
                categorie.setId(resultSet.getInt("id"));
                categorie.setLibelle(resultSet.getString("libelle"));
                Double valeur =  resultSet.getDouble("sum");
                stat.put(categorie.libelle, valeur);
            }

            return stat;
        } catch (Exception e) {
            throw e;
        }
        
        
    }

    public static double getMoyenneChiffreAffaire() throws Exception{
        Connection connection = null;
        try {
            double avg = 0;
            connection = Connexion.getConnexion();
            String sql = "select avg(sum) from v_all_recette_by_categorie";
            PreparedStatement psmt = connection.prepareStatement(sql);
            ResultSet resultSet = psmt.executeQuery();
            while(resultSet.next()){
                avg =  resultSet.getDouble("avg");
            }

            return avg;
        } catch (Exception e) {
            throw e;
        }
    }

    public static double getSumChiffreAffaire() throws Exception{
        Connection connection = null;
        try {
            double sum = 0;
            connection = Connexion.getConnexion();
            String sql = "select sum(sum) from v_all_recette_by_categorie";
            PreparedStatement psmt = connection.prepareStatement(sql);
            ResultSet resultSet = psmt.executeQuery();
            while(resultSet.next()){
                sum =  resultSet.getDouble("sum");
            }

            return sum;
        } catch (Exception e) {
            // TODO: handle exception
            throw e;
        }
    }
    
}
