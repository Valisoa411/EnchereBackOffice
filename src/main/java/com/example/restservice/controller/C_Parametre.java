/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.restservice.controller;

import com.example.restservice.generic.Econnect;
import com.example.restservice.generic.GenericDAO;
import com.example.restservice.model.Parametre;
import java.sql.Connection;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("parametre")
public class C_Parametre {

    @PostMapping("/addParametre/{libelle}/{value}")
    public void addParametre(
            @PathVariable String libelle,
            @PathVariable double value
    ) throws Exception {
        try (Connection con = Econnect.connexion()) {
                GenericDAO.save(new Parametre(libelle, value), con);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @PutMapping("/modifyParametre/{id}/{valeur}")
    public void modifyParametre(
            @PathVariable int id,
            @PathVariable double valeur
    ) {
        try(Connection con =Econnect.connexion()){
            Parametre p= new Parametre();
            p.setId(id);
            p.setValeur(valeur);
            GenericDAO.update(p, con);
            
        } catch (Exception e) {
        }
    }
}
