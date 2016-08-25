package com.adm.web.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.*;

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
 */
@Controller
@Component
@RequestMapping({"/", "/homepage", "/index"})
@SessionAttributes("klant")
public class HomeController {

    private Klant klant;

    @RequestMapping(method=GET)
    public String home(Model model, Klant klant) {

        model.addAttribute("klant", klant);

        return "home";
    }
}
