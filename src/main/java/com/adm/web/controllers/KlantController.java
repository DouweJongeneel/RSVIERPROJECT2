package com.adm.web.controllers;

import com.adm.database.daos.KlantDAO;
import com.adm.domain.Klant;
import com.adm.web.forms.KlantRegisterForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by Milan_Verheij on 15-08-16.
 *
 * Klant controller
 *
 * - Inloggen klant
 * - Registeren klant
 * - Profiel klant
 *
 */

@Controller
@RequestMapping("/klant")
public class KlantController {

    private KlantDAO klantDAO;

    @Autowired
    public KlantController(KlantDAO klantDAO) {
        this.klantDAO = klantDAO;
    }

    @RequestMapping(value = "/register", method=GET)
    public String showRegistrationForm(Model model) {
        model.addAttribute("klantRegisterForm", new KlantRegisterForm());
        return "klant/klantRegisterForm";
    }

    @RequestMapping(value = "/register", method=POST)
    public String processRegistration(
            @Valid KlantRegisterForm klantRegisterForm,
            Errors errors,
            RedirectAttributes model)
            throws IOException {

        if (errors.hasErrors()) {
            return "/klant/klantRegisterForm";
        }

        // Save klant to repository
        Klant nieuweKlant = klantRegisterForm.toKlant();
        klantDAO.makePersistent(nieuweKlant);

        // Save profilePicture
        MultipartFile profilePicture = klantRegisterForm.getProfilePicture();
        profilePicture.transferTo(new File("/data/profilePictures/"
                + nieuweKlant.getVoornaam() + nieuweKlant.getAchternaam() + ".jpg"));

        //TODO: Aan de hand van de oorspronkelijke filename opslaan met juiste bestandsnaam

        // Save flash attribute
        model.addFlashAttribute("klant", nieuweKlant);

        // Redirect to created profile
        return "redirect:/klant/{username}";
    }

    @RequestMapping(value="/{username}", method=GET)
    public String showSpitterProfile(@PathVariable String username, Model model) {

        if (!model.containsAttribute("klant")) {
            Klant klantje = new Klant(null, "FOUT", "FOUT", "FOUT", "FOUT", null);
            model.addAttribute("klant", klantje);
        }

        return "klant/klantProfile";
    }

    @RequestMapping(value="/me", method=GET)
    public String me() {
        System.out.println("ME ME ME ME ME ME ME ME ME ME ME");
        return "home";
    }
}
