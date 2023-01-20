/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.restservice.model;

import com.example.restservice.generic.Attr;
import com.example.restservice.generic.ClassAnotation;
import com.example.restservice.generic.Connexion;
import com.example.restservice.generic.Econnect;
import com.example.restservice.generic.GenericDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    
    public RechargeCompte(int id) {
        this.id = id;
    }

    public RechargeCompte() {
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

    public void fillAttributeByID(int id) throws Exception {
        RechargeCompte filled = new RechargeCompte();
        filled.setId(id);
        Connection con = Econnect.connexion();
        try {
            String sql = "SELECT * FROM rechargecompte WHERE id=? LIMIT 1";
            PreparedStatement psmt = con.prepareStatement(sql);
            psmt.setInt(1, id);
            ResultSet resultSet = psmt.executeQuery();
            while (resultSet.next()) {
                this.id = resultSet.getInt("id");
                this.idClient = resultSet.getInt("idclient");
                this.date = resultSet.getDate("date");
                this.valeur = resultSet.getDouble("valeur");
                this.etat = resultSet.getInt("etat");
            }

        } catch (Exception e) {
            throw e;
        }
    }

    public void changeState(int state, Connection conn) throws Exception {
        if (conn == null) {
            conn = Econnect.connexion();
        }
        this.fillAttributeByID(this.getId());
        System.out.println("id filled " + this.getId());
        PreparedStatement psmt = null;
        try {
            conn.setAutoCommit(false);
            this.setEtat(state);
            String sqlRechargeUpdate = "UPDATE rechargecompte SET etat=? WHERE id=?";
            psmt = conn.prepareStatement(sqlRechargeUpdate);
            psmt.setInt(1, state);
            System.out.println("update " + state);
            psmt.setInt(2, this.getId());
           
            if (state == 11) {
                System.out.println("ACCEPTED");
                String sqlgetLastSoldeClient = "SELECT * FROM client WHERE id=?";
                PreparedStatement psmt2 = conn.prepareStatement(sqlgetLastSoldeClient);
                psmt2 = conn.prepareStatement(sqlgetLastSoldeClient);
                psmt2.setInt(1, this.getIdClient());
                ResultSet resultSet = psmt2.executeQuery();
                double lastSolde = 0;
                    while (resultSet.next()) {
                        lastSolde = resultSet.getDouble("solde");
                    }
                System.out.println("lastS: " + lastSolde);
                String sqlClientUpdate = "UPDATE client SET solde=? WHERE id=?";
                PreparedStatement psmt3 = conn.prepareStatement(sqlClientUpdate);
                double newSolde = lastSolde + this.getValeur();
                psmt3.setDouble(1, newSolde);
                psmt3.setInt(2, this.getIdClient());
                psmt3.executeUpdate();
                System.out.println("newS: " + newSolde);
            }
            psmt.executeUpdate();
            System.out.println("-----------------------");
            System.out.println("Sql"+psmt);
            conn.commit();
            System.out.println("----Couocuu");
            //psmt = conn

            //GenericDAO.update(this, conn);
        } catch (Exception e) {
            e.printStackTrace();
            conn.rollback();
        } finally {
            if (conn != null) {
                conn.close();
            }
            //if(psmt != null)psmt.close();

        }
    }
}
