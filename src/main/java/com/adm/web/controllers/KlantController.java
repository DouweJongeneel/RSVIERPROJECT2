package com.adm.web.controllers;

import com.adm.database.daos.KlantDAO;
import com.adm.domain.Klant;
import com.adm.web.forms.KlantRegisterForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;

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
@SessionAttributes("klant")
public class KlantController {

    private KlantDAO klantDAO;
//    private Klant klant;

    @Autowired
    public KlantController(KlantDAO klantDAO) {
        this.klantDAO = klantDAO;
    }

    @RequestMapping(value = "/register", method = GET)
    public String showRegistrationForm(Model model) {
        model.addAttribute("klantRegisterForm", new KlantRegisterForm());
        return "klant/klantRegisterForm";
    }

    @RequestMapping(value = "/register", method = POST)
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
        model.addAttribute("email", nieuweKlant.getEmail());

        // Redirect to created profile
        return "redirect:/klant/{email}";
    }

    // Profiel pagina (leeg)
    @RequestMapping(value = "/profile", method = GET)
    public String showProfile(Model model, Klant klant) {
        model.addAttribute("klant", klant);

        return "klant/klantProfile";
    }

    // Profielpagina(op basis van url met email)
    @RequestMapping(value = "/{email}", method = GET)
    public String showKlantProfile(@PathVariable String email, Model model, Klant globalKlant) {

        if (!model.containsAttribute("klant")) {
            Klant klantje = new Klant(null, "FOUT", "FOUT", "FOUT", "FOUT", "FOUT", null);
            model.addAttribute("klant", klantje);
        }

        model.addAttribute("klant", globalKlant);

        return "klant/klantProfile";
    }

    // Klantenlijst pagina
    @RequestMapping(value = "/klanten", method = GET)
    public String showKlanten(Model model) {
        List<Klant> klantenLijst = klantDAO.findAll();

        model.addAttribute("klantenList", klantenLijst);
        return "klant/klantenLijst";
    }

    // Tumble status methode
    @RequestMapping(value = "/delete/{id}", method = GET)
    public String tumbleStatusKlant(@PathVariable Long id, Model model) {
        Klant klant = klantDAO.findById(id);

        if (klant.getKlantActief().charAt(0) == '0')
            klant.setKlantActief("1");
        else
            klant.setKlantActief("0");

        klantDAO.makePersistent(klant);


        return showKlanten(model);
    }

    // Select Client
    @RequestMapping(value = "/select/{id}", method = GET)
    public String selectKlant(@PathVariable Long id, Model model) {
        Klant klant = klantDAO.findById(id);

        model.addAttribute(klant);

        return showProfile(model, klant);
    }
}