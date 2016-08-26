package com.adm.web.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import com.adm.database.daos.KlantDAO;
import com.adm.database.service.KlantService;
import com.adm.domain.Klant;
import com.adm.domain.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


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
@SessionAttributes({"klant", "winkelwagen", "shoppingCart"})
public class HomeController {

    private Klant klant;
    private ShoppingCart shoppingCart;

    @RequestMapping(method=GET)
    public String home(Model model, Klant klant, ShoppingCart shoppingCart) {

    	model.addAttribute("klant", klant);
        model.addAttribute("shoppingCart", shoppingCart);

        return "home";
    }

    @RequestMapping(value = "/about", method = GET)
    public String about(Model model, Klant klant, ShoppingCart shoppingCart) {
        model.addAttribute("klant", klant);
        model.addAttribute("shoppingCart", shoppingCart);

        return "about";
    }

    @RequestMapping(value="/logout", method = GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }


        return "redirect:/";
    }

    @RequestMapping(value = "/loginSuccess", method = GET)
    public String loginSuccess() {

        return "redirect:/";
    }

}
