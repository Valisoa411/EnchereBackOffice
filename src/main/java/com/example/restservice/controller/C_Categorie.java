/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.restservice.controller;

import com.example.restservice.generic.Econnect;
import com.example.restservice.generic.GenericDAO;
import com.example.restservice.model.Categorie;
import java.sql.Connection;
import java.util.HashMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Karen
 */
@RestController
@CrossOrigin
@RequestMapping("/categorie")
public class C_Categorie {

    @PostMapping("/addCategorie/{libelle}")
    public void addCategorie(
            @PathVariable String libelle
    ) throws Exception {
        try (Connection con = Econnect.connexion()) {
            if (!libelle.equalsIgnoreCase("")) {
                Categorie cat = new Categorie(libelle);
                System.out.println(cat.getLibelle());
                GenericDAO.save(cat, con);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @PutMapping("/updateCategorie/{id}/{newLibelle}")
    public void updateCategorie(
            @PathVariable int id,
            @PathVariable String newLibelle
    ) {
        try (Connection con = Econnect.connexion()) {
            Categorie cat = new Categorie();
            cat.setId(id);
            cat.setLibelle(newLibelle);
            GenericDAO.update(cat, con);
        } catch (Exception e) {
        }
    }
    
    @GetMapping("/statistic")
    public HashMap<String,Double> getStatistic() throws Exception{
        return Categorie.getStatistic();   
    }

}
