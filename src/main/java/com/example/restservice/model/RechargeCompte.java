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
import java.util.Date;

/**
 *
 * @author P14_A_111_Dina
 */
@ClassAnotation(table="RechargeCompte")
public class RechargeCompte {
    @Attr(isPrimary=true)
    int id;
    @Attr
    int idClient;
    @Attr
    Date date;
    @Attr
    double valeur;
    @Attr
    int etat;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getValeur() {
        return valeur;
    }

    public void setValeur(double valeur) {
        this.valeur = valeur;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }
    
    public ArrayList<RechargeCompte> listeRecharge() throws Exception{
        Connection connect = new Connexion().getConnexion();
        String requete = "SELECT * FROM RechargeCompte where etat=11";
        ArrayList<RechargeCompte> liste = new ArrayList<RechargeCompte>();
        try{
            liste = (ArrayList<RechargeCompte>)GenericDAO.findBySql(this,requete,connect);
        }
        catch(Exception e){
            throw e;
        }
        finally{
            connect.close();
        }
        return liste;
    }
    
    public Client getClient() throws Exception{
        Client c = new Client();
        c.setId(this.idClient);
        return c.getClientById();
    }
}