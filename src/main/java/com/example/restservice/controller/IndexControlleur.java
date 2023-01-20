package com.example.restservice.controller;

import com.example.restservice.generic.Econnect;
import com.example.restservice.generic.GenericDAO;
import com.example.restservice.model.Categorie;
import com.example.restservice.model.Commission;
import com.example.restservice.model.Parametre;
import com.example.restservice.model.RechargeCompte;
import com.example.restservice.model.Statistic;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexControlleur {

    @GetMapping("/")
    public String index(Model m) {
        m.addAttribute("categorie", new Categorie());
//        return "index";
        return "backoffice-login";
    }

    @PostMapping("addCategorie")
    public String valider(@ModelAttribute Categorie c, Model m) throws Exception {
        System.out.println(c.toString());
        try (Connection con = Econnect.connexion()) {
            GenericDAO.save(c, con);
            ArrayList<Categorie> categorie = GenericDAO.findBySql(new Categorie(), "Select * from categorie where etat =0", con);
            for (Categorie cat : categorie) {
                m.addAttribute("list", Arrays.asList(categorie.toArray()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:categorie";
    }
    
    @GetMapping("/modifyCategorie")
    public String modifier(HttpServletRequest rq, Model m) throws Exception {
        System.out.println("Tafiditraa ato aloha !!! ");
        System.out.println(rq.getParameter("id"));
        try (Connection con = Econnect.connexion()) {
            Categorie c = new Categorie(Integer.parseInt(rq.getParameter("id")), 1);
            c.setId(Integer.parseInt(rq.getParameter("id")));
            GenericDAO.update(new Categorie(Integer.parseInt(rq.getParameter("id")), 0), con);
            ArrayList<Categorie> categorie = GenericDAO.findBySql(new Categorie(), "Select * from categorie where etat =0", con);
            for (Categorie cat : categorie) {
                m.addAttribute("list", Arrays.asList(categorie.toArray()));
            }
        } catch (Exception e) {     
            e.printStackTrace();
        }
        return "1";
    }

    @GetMapping("/addCommission")
    public String addCommission(HttpServletRequest rq, Model m) throws Exception {
        try (Connection con = Econnect.connexion()) {
            GenericDAO.save(new Commission(Double.parseDouble(rq.getParameter("commission")), new Date()), con);
            ArrayList<Parametre> listParam = GenericDAO.all(new Parametre(), con);
            m.addAttribute("listParam", listParam);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return "redirect:parametre";
    }

//    @GetMapping("/getParametre")
//    public String addParametre(HttpServletRequest rq, Model m) throws Exception {
//        try (Connection con = Econnect.connexion()) {
//            ArrayList<Parametre> listParam = GenericDAO.all(new Parametre(), con);
//            ArrayList<Commission> listCom = GenericDAO.findBySql(new Commission(), "select * from commission order by date desc  limit 1 ", con);
//            m.addAttribute("listParam", listParam);
//            m.addAttribute("listCom", listCom);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw e;
//        }
//        return "parametre";
//    }

//    @GetMapping("/modifyParam")
//    public String modifyParams(HttpServletRequest rq, Model m) {
//        try (Connection con = Econnect.connexion()) {
//            
//            GenericDAO.update(new Parametre(Integer.parseInt(rq.getParameter("idParam")), Double.parseDouble(rq.getParameter("com"))), con);
//            GenericDAO.update(new Parametre(Integer.parseInt(rq.getParameter("idCom")), Double.parseDouble(rq.getParameter("valeur"))), con);
//            
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//          return "redirect:getParametre";
//    }
    
    @PostMapping("/modifyParam")
    public String modifyParams(HttpServletRequest rq, Model m) {
        try (Connection con = Econnect.connexion()) {
            GenericDAO.save(new Commission(Double.parseDouble(rq.getParameter("com")), new Date()), con);
            ArrayList<Parametre> list = GenericDAO.all(new Parametre(), con);
            for (Parametre c : list) {
               GenericDAO.update(
                    new Parametre(c.getId(),
                            Double.parseDouble(rq.getParameter(c.getLibelle()))), con);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:parametre";
    }
    
    //LOAD PAGE
    @RequestMapping("/categorie")
    public String loadCategorie(Model m) throws Exception {
        m.addAttribute("categorie", new Categorie());
        m.addAttribute("listCategorie", new Categorie().listeCategorie());
        return "backoffice-categorie";
    }
    
    @RequestMapping("/rechargeCompte")
    public String loadRecharge(Model m) throws Exception {
        m.addAttribute("listRecharge", new RechargeCompte().listeRecharge());
        return "backoffice-recharge_compte";
    }
    
    @RequestMapping("/parametre")
    public String parametreEnchere(Model m) throws Exception {
        m.addAttribute("listParametre", new Parametre().listeParametre());
        m.addAttribute("commission", new Commission().getCommissionValue());
        return "backoffice-parametre_enchere";
    }
        
    @GetMapping("/showStatistics")
    public String statistics(Model m) throws Exception {
        Gson g = new Gson();
        ArrayList array1 = new ArrayList();
        ArrayList array2 = new ArrayList();
        HashMap<String, Double> map = Categorie.getStatistic();
        for (Map.Entry<String, Double> entry : map.entrySet()) {
            String key = entry.getKey();
            Double value = entry.getValue();
            
            array1.add(key);
            array2.add(value);
        }
        
        m.addAttribute("array1", array1);
        m.addAttribute("array2", array2);
        m.addAttribute("categorie", new Categorie().listeCategorie().size());
        m.addAttribute("don1", Statistic.getMoyenneChiffreAffaire());
        m.addAttribute("don2", Statistic.getSumChiffreAffaire());
        System.out.println(new Categorie().listeCategorie().size());
        return "backoffice-statistic";
    }
}
