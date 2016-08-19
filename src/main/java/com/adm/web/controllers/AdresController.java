package com.adm.web.controllers;

import com.adm.database.daos.AdresDAO;
import com.adm.database.daos.KlantDAO;
import com.adm.database.service.AdresService;
import com.adm.database.service.KlantService;
import com.adm.domain.Adres;
import com.adm.domain.AdresType;
import com.adm.domain.Klant;
import com.adm.web.forms.AdresRegisterForm;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by Milan_Verheij on 19-08-16.
 *
 * Adres Controller
 */

@Controller
@Component
@RequestMapping("/klant/adres")
@Transactional
@SessionAttributes("klant")
public class AdresController {
    private AdresDAO adresDAO;
    private KlantDAO klantDAO;

    @Autowired
    public AdresController(AdresService adresService, KlantService klantService) {
        this.adresDAO = adresService.getAdresDAO();
        this.klantDAO = klantService.getDAO();
    }

    @RequestMapping(value = "/register", method = GET)
    public String showRegistrationForm(Model model, Klant klant) {

        model.addAttribute("adresRegisterForm", new AdresRegisterForm());
        model.addAttribute("klant", klant);

        return "klant/adres/adresRegisterForm";
    }

    @RequestMapping(value = "/register", method = POST)
    public String processRegistration(
            @Valid AdresRegisterForm adresRegisterForm,
            Errors errors,
            RedirectAttributes model,
            Klant klant)
            throws IOException {

        if (errors.hasErrors()) {
            return "/klant/adres/adresRegisterForm";
        }

        // Maak van adersForm -> adres
        Adres nieuwAdres = adresRegisterForm.toAdres();

        //Zet het adres in de klant en persist die shit
        Hibernate.initialize(klant.getAdresGegevens());
        klant.getAdresGegevens().put(nieuwAdres, AdresType.THUISADRES);
        klantDAO.makePersistent(klant);

        // Update dat adres enzo wat betreft persistence
        nieuwAdres = adresDAO.makePersistent(nieuwAdres);

        // Save flash attribute
        model.addFlashAttribute("adres", nieuwAdres);
        model.addAttribute("id", nieuwAdres.getId());

        // Redirect to created profile
        return "redirect:/klant/profile";
    }

}
