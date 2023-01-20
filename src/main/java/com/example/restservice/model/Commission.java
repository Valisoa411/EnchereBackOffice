package com.example.restservice.model;

import com.example.restservice.generic.Attr;
import com.example.restservice.generic.ClassAnotation;
import com.example.restservice.generic.Connexion;
import com.example.restservice.generic.GenericDAO;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;

@ClassAnotation(table="commission")
public class Commission{
    @Attr(isPrimary=true)
    int id;
    @Attr
    double commission;
    @Attr
    Date date;

    public Commission() {
    }

    public Commission(double commission) {
        this.commission = commission;
    }
    

    public Commission(double commission, Date date) {
        this.commission = commission;
        this.date = date;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getCommission() {
        return this.commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getCommissionValue() throws Exception{
        double val = 0;
        Connection connect = new Connexion().getConnexion();
        String requete = "SELECT * FROM Commission order by id desc limit 1";
        ArrayList<Commission> liste = new ArrayList<Commission>();
        try{
            liste = (ArrayList<Commission>)GenericDAO.findBySql(this,requete,connect);
            val = liste.get(0).getCommission();
        }
        catch(Exception e){
            throw e;
        }
        finally{
            connect.close();
        }
        return val;
    }
}