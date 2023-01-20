package com.example.restservice.model;

import com.example.restservice.generic.Attr;
import com.example.restservice.generic.ClassAnotation;
import com.example.restservice.generic.Connexion;
import com.example.restservice.generic.GenericDAO;
import java.sql.Connection;
import java.util.ArrayList;

@ClassAnotation(table="parametre")
public class Parametre {
    @Attr(isPrimary=true)
    int id;
    @Attr 
    String libelle;
    @Attr
    double valeur;

    public Parametre() {
    }

    public Parametre(int id, double valeur) {
        this.id = id;
        this.valeur = valeur;
    }
    

    public Parametre(String libelle, double valeur) {
        this.libelle = libelle;
        this.valeur = valeur;
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

    public double getValeur() {
        return valeur;
    }

    public void setValeur(double valeur) {
        this.valeur = valeur;
    }
    
    public ArrayList<Parametre> listeParametre() throws Exception{
        Connection connect = new Connexion().getConnexion();
        String requete = "SELECT * FROM Parametre";
        ArrayList<Parametre> liste = new ArrayList<Parametre>();
        try{
            liste = (ArrayList<Parametre>)GenericDAO.findBySql(this,requete,connect);
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
