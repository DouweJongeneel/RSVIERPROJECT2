package com.adm.web.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import java.util.HashSet;

import com.adm.domain.BestelArtikel;
import com.adm.domain.Klant;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * Created by Milan_Verheij on 15-08-16.
 *
 * Home controller
 *
 * Voegt vanaf begin af aan een Klant-object toe aan het model, als het goed is
 * is die klant een global session klant.
 *
 */
@Controller
@Component
@RequestMapping({"/", "/homepage", "/index"})
@SessionAttributes({"klant", "winkelwagen"})
public class HomeController {

    private Klant klant;
    private HashSet<BestelArtikel> winkelwagen;
    
    @RequestMapping(method=GET)
    public String home(Model model, Klant klant, HashSet<BestelArtikel> winkelwagen) {

    	model.addAttribute("klant", klant);
    	model.addAttribute("winkelwagen", winkelwagen);

        return "home";
    }
}
