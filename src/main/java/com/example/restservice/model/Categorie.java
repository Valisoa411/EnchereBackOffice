/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.restservice.model;

import com.example.restservice.generic.Attr;
import com.example.restservice.generic.ClassAnotation;
import com.example.restservice.generic.Connexion;
import com.example.restservice.generic.GenericDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Karen
 */
@ClassAnotation(table="categorie")
public class Categorie {
    @Attr(isPrimary=true)
    int id;
    @Attr
    String libelle;
    @Attr
    int  etat;


    public Categorie() {
    }

    public Categorie(String libelle) {
        this.libelle = libelle;
    }

    public Categorie(int id, int etat) {
        this.id = id;
        this.etat = etat;
    }
    
    public Categorie(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public int getEtat() {
        return this.etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    @Override
    public String toString() {
        return "Categorie{" + "id=" + id + ", libelle=" + libelle + '}';
    }
    
    public static HashMap<String,Double> getStatistic() throws Exception{
        Connection con = null;
        PreparedStatement psmt = null;
        ResultSet resultSet = null;
        try {
            con = Connexion.getConnexion();
            //ArrayList<Categorie> categories = (ArrayList<Categorie>)GenericDAO.all(this, con);
            String sql = "SELECT * FROM v_count_categorie";
            psmt = con.prepareStatement(sql);
            resultSet = psmt.executeQuery();

            HashMap<String,Double> hashMap = new LinkedHashMap<>();
            double N = 0;
            while(resultSet.next()){
                Categorie toAdd = new Categorie();
                toAdd.setId(resultSet.getInt("id"));
                toAdd.setLibelle(resultSet.getString("libelle"));
                Double count = resultSet.getDouble("count");
                if(count==null)count=(double) 0;
                N=N+count;
                // System.out.println("getStatistic before : lib="+toAdd.libelle+" c="+count);
                hashMap.put(toAdd.libelle, count);
                // System.out.println(hashMap.size());
                // System.out.println("getStatistic after : lib="+toAdd.libelle+" c="+count);
            }
            
            // for (Map.Entry<String, Double> entry : hashMap.entrySet()) {
            //     String key = entry.getKey();
            //     Double value = entry.getValue();
                
            //     // System.out.println("N = "+N);
            //     // System.out.println("V: "+value);
            //     // value = value/N;
            //     // System.out.println(">>>> /"+N+" = "+value);
            //     hashMap.replace(key, value);
                
            // }
            System.out.println("hash size : "+hashMap.size());
            return hashMap;
            

            
        } catch (Exception e) {
           throw e;
        } finally{
            if(con != null)con.close();
            if(psmt != null)psmt.close();
            if(resultSet != null)con.close();
        }
        
    }
    
    
    public ArrayList<Categorie> listeCategorie() throws Exception{
        Connection connect = new Connexion().getConnexion();
        String requete = "SELECT * FROM Categorie WHERE etat = 11";
        ArrayList<Categorie> liste = new ArrayList<Categorie>();
        try{
            liste = (ArrayList<Categorie>)GenericDAO.findBySql(this,requete,connect);
        }
        catch(Exception e){
            throw e;
        }
        finally{
            connect.close();
        }
        return liste;
    }
}
