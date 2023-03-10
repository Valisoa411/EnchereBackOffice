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
import java.util.ArrayList;

/**
 *
 * @author P14_A_111_Dina
 */
@ClassAnotation(table="Client")
public class Client {
    @Attr(isPrimary=true)
    int id;
    @Attr
    String nom;
    @Attr
    String email;
    @Attr
    String password;
    @Attr
    double solde;

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Client(String nom, String email, String password) {
        this.nom = nom;
        this.email = email;
        this.password = password;
    }
    
    public Client(int id) {
        this.id = id;
    }

    public Client() {
    }
    
    public ArrayList<Client> listeCategorie() throws Exception{
        Connection connect = new Connexion().getConnexion();
        String requete = "SELECT * FROM Client";
        ArrayList<Client> liste = new ArrayList<Client>();
        try{
            liste = (ArrayList<Client>)GenericDAO.findBySql(this,requete,connect);
        }
        catch(Exception e){
            throw e;
        }
        finally{
            connect.close();
        }
        return liste;
    }
    
    public Client getClientById() throws Exception{
        Connection connect = new Connexion().getConnexion();
        String requete = "SELECT * FROM Client where id = "+this.id;
        ArrayList<Client> liste = new ArrayList<Client>();
        try{
            liste = (ArrayList<Client>)GenericDAO.findBySql(this,requete,connect);
        }
        catch(Exception e){
            throw e;
        }
        finally{
            connect.close();
        }
        return liste.get(0);
    }
}
