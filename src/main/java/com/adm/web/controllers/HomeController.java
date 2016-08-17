package com.adm.web.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import com.adm.domain.Klant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
@RequestMapping({"/", "/homepage", "/index"})
public class HomeController {

    @RequestMapping(method=GET)
    public String home(Klant klant,
                       Model model) {

        model.addAttribute("globalKlant", klant);

        return "home";
    }
}
