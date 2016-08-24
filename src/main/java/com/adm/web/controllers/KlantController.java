package com.adm.web.controllers;

import com.adm.database.daos.KlantDAO;
import com.adm.database.service.KlantService;
import com.adm.domain.Adres;
import com.adm.domain.AdresType;
import com.adm.domain.Klant;
import com.adm.web.forms.KlantRegisterForm;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.nio.file.Files;
import java.util.*;

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

//TODO Exception handling

@Controller
@Component
@RequestMapping("/klant")
@Transactional
@SessionAttributes({ "klant", "plaatje"})
public class KlantController {

    private KlantDAO klantDAO;
    private MessageSource messageSource;

//    private String pictureFolder = "C:/harrie/uploads/data/profilePictures"; // Windows
    private String pictureFolder = "/tmp/harrie/uploads/data/profilePictures/"; // Unix-Based

    @Autowired
    public KlantController(KlantService klantService, MessageSource messageSource) {
        this.klantDAO = klantService.getDAO();
        this.messageSource = messageSource;
    }

    @RequestMapping(value = "/register", method = GET)
    public String showRegistrationForm(Model model) {

        model.addAttribute("klantRegisterForm", new KlantRegisterForm());

        return "klant/klantRegister";
    }

    @RequestMapping(value = "/register", method = POST)
    public String processRegistration(
            @Valid KlantRegisterForm klantRegisterForm,
            Errors errors,
            Model model)
            throws Exception {

        if (errors.hasErrors()) {
            return "klant/klantRegister";
        }

        // Save klant to repository
        Klant nieuweKlant = klantRegisterForm.toKlant();
        nieuweKlant = klantDAO.makePersistent(nieuweKlant);

        // Save profilePicture
        MultipartFile profilePicture = klantRegisterForm.getProfilePicture();
        profilePicture.transferTo(new File("/data/profilePictures/"
                + nieuweKlant.getId() + ".jpg"));

        //TODO: Aan de hand van de oorspronkelijke filename opslaan met juiste bestandsnaam

        // Terug naar klantenlijst, nieuwe klanten ophalen
        return showKlanten(model);
    }

    // Profiel pagina (leeg)
    @RequestMapping(value = "/profile", method = GET)
    public String showProfile(Model model, Klant klant) {

        // Initialize address data (lazy loading)
        Hibernate.initialize(klant.getAdresGegevens()); //TODO: Is niet Hibernate-onafhankelijk

        // Fix language settings for Adress Type
        Iterator<Adres> adresIterator = klant.getAdresGegevens().keySet().iterator();

        while (adresIterator.hasNext()) {
            Adres adres = adresIterator.next();
            AdresType adresType = adres.getType();
            adres.setAdresTypeString(messageSource.getMessage(adresType.toString(), new Object[] {}, LocaleContextHolder.getLocale()));
        }

        // Add Attributes in map
        model.addAttribute("adresMap", klant.getAdresGegevens());
        model.addAttribute("klant", klant);

        return "klant/klantProfile";
    }

    // Klantenlijst pagina
    @RequestMapping(value = "/klanten", method = GET)
    public String showKlanten(Model model) throws Exception {
        List<Klant> klantenLijst = klantDAO.findAll();

        ArrayList<String> plaatjesList = new ArrayList<>();

        // Laad de profielpictures in
        for (int i = 0; i < klantenLijst.size(); i++) {
            byte[] array = Files.readAllBytes(new File(pictureFolder
                    + klantenLijst.get(i).getId() + ".jpg").toPath());

            plaatjesList.add((i), Base64.encode(array));
        }

        model.addAttribute("plaatjesList", plaatjesList);
        model.addAttribute("klantenList", klantenLijst);
        return "klant/klantenLijst";
    }

    // Tumble Status
    @RequestMapping(value = "/tumble/{id}", method = GET)
    public String tumbleStatusKlant(@PathVariable Long id, Model model) throws Exception {
        Klant klant = klantDAO.findById(id);

        if (klant.getKlantActief().charAt(0) == '0')
            klant.setKlantActief("1");
        else
            klant.setKlantActief("0");

        klant.setDatumGewijzigd( new Date().toString());

        klantDAO.makePersistent(klant);

        //TODO: Als vanuit een profile page komt terug schakelen naar profile ipv terug naar klantenlijst

        return showKlanten(model);
    }

    // Select Client
    @RequestMapping(value = "/select/{id}", method = GET)
    public String selectKlant(@PathVariable Long id, Model model) throws Exception {
        Klant klant = klantDAO.findById(id);

        byte[] array = Files.readAllBytes(new File("/tmp/harrie/uploads/data/profilePictures/"
                + klant.getId() + ".jpg").toPath());

        String imageDataString = Base64.encode(array);

        model.addAttribute(klant);
        model.addAttribute("plaatje", imageDataString);

        return showProfile(model, klant);
    }


    // Modify Client (from clientlist) TODO: From profile using session client
    @RequestMapping(value = "/modify/{id}", method = GET)
    public String modifyClient(@PathVariable Long id, Model model) throws Exception {
        Klant klant = klantDAO.findById(id);

        model.addAttribute("klant", klant);

        return "klant/klantModify";
    }

    @RequestMapping(value = "/modify/{id}", method = POST)
    public String processRegistration(
            @Valid Klant klant,
            Errors errors,
            Model model)
            throws Exception {

        if (errors.hasErrors()) {
            // TODO: A Error checking en B error weergeven
            return "klant/klantenLijst";
        }

        // Save klant to repository
        klant.setDatumGewijzigd(new Date().toString());
        klant = klantDAO.makePersistent(klant);
        Hibernate.initialize(klant.getAdresGegevens());
        model.addAttribute(klant);

        // Terug naar klantenlijst, nieuwe klanten ophalen
        return showKlanten(model);
    }
}
