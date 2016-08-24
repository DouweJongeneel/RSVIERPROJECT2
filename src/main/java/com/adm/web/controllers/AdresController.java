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

import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by Milan_Verheij on 19-08-16.
 *
 * Address Controller
 */

@Controller
@Component
@RequestMapping("/klant/adres")
@Transactional
@SessionAttributes({"klant", "adres"})
public class AdresController {
    private AdresDAO adresDAO;
    private KlantDAO klantDAO;
    private MessageSource messageSource;

    @Autowired
    public AdresController(AdresService adresService, KlantService klantService, MessageSource messageSource) {
        this.adresDAO = adresService.getAdresDAO();
        this.klantDAO = klantService.getDAO();
        this.messageSource = messageSource;
    }

    @RequestMapping(value = "/register", method = GET)
    public String showRegistrationForm(Model model, Klant klant) {

        model.addAttribute("adresRegisterForm", new AdresRegisterForm());
        model.addAttribute("klant", klant);

        return "klant/adres/adresRegister";
    }

    @RequestMapping(value = "/register", method = POST)
    public String processRegistration(
            @Valid AdresRegisterForm adresRegisterForm,
            Errors errors,
            Klant klant)
            throws IOException {

        if (errors.hasErrors()) {
            return "klant/adres/adresRegister";
        }

        // Maak van adersForm -> adres
        Adres nieuwAdres = adresRegisterForm.toAdres();

        // Persisten
        nieuwAdres = adresDAO.makePersistent(nieuwAdres);
        klant.getAdresGegevens().put(nieuwAdres, nieuwAdres.getType());

        // Persist alles naar de klant
        klantDAO.makePersistent(klant);

        // Redirect to created profile
        return "redirect:/klant/profile";
    }

    // Tumble status methode
    @RequestMapping(value = "/tumble/{id}", method = GET)
    public String tumbleStatusAdres(@PathVariable Long id, Klant klant, Model model) throws Exception {

        //Achtung, the id is the id of the value in the map in the client
        Adres adres = adresDAO.findById(id);

        if (adres.getAdresActief().charAt(0) == '0')
            adres.setAdresActief("1");
        else
            adres.setAdresActief("0");

        adres.setDatumGewijzigd(new Date().toString());

        // Update status of address
        adresDAO.makePersistent(adres);

        // Update client info
        Hibernate.initialize(klant.getAdresGegevens());
        klant = klantDAO.makePersistent(klant);

        // Inject client into model for display
        model.addAttribute("klant", klant);

        // Redirect to created profile
        return "redirect:/klant/profile";
    }

    // Modify Client (from clientlist) TODO: From profile using session client
    @RequestMapping(value = "/modify/{id}", method = GET)
    public String modifyClient(@PathVariable Long id, Model model, Adres adres) throws Exception {
        adres = adresDAO.findById(id);

        // Fix language for adresType
        AdresType adresType = adres.getType();
        adres.setAdresTypeString(messageSource.getMessage(adresType.toString(), new Object[] {}, LocaleContextHolder.getLocale()));

        // Add attributes
        model.addAttribute("adres", adres);

        return "klant/adres/adresModify";
    }

    @RequestMapping(value = "/modify/{id}", method = POST)
    public String processRegistration(
            @Valid Adres adres,
            Errors errors,
            Model model,
            Klant klant)
            throws Exception {

        if (errors.hasErrors()) {
//             TODO: A Error checking en B error weergeven
            return "redirect:/klant/profile";
        }

        // TODO: ADRESTYPE?

        // Save adres to repository
        adres.setDatumGewijzigd(new Date().toString());
        adres = adresDAO.makePersistent(adres);

        // Update client info
        Hibernate.initialize(klant.getAdresGegevens());
        klant = klantDAO.makePersistent(klant);

        // Inject client into model for display
        model.addAttribute(klant);

        // Terug naar klantenlijst, nieuwe klanten ophalen
        return "redirect:/klant/profile";
    }

}
