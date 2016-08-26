package com.adm.web.controllers;

import com.adm.database.daos.KlantDAO;
import com.adm.database.service.KlantService;
import com.adm.domain.Klant;
import com.adm.web.forms.KlantRegisterForm;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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

	private String pictureFolder = "C:/harrie/uploads/data/profilePictures/"; // Windows
	//    private String pictureFolder = "/tmp/harrie/uploads/data/profilePictures/"; // Unix-Based

	@Autowired
	public KlantController(KlantService klantService) {
		this.klantDAO = klantService.getDAO();
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
		nieuweKlant = klantDAO.makePersistent(nieuweKlant);

		// Save profilePicture
		MultipartFile profilePicture = klantRegisterForm.getProfilePicture();
		profilePicture.transferTo(new File("/data/profilePictures/"
				+ nieuweKlant.getId() + ".jpg"));

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

		Hibernate.initialize(klant.getAdresGegevens()); //TODO: Is niet Hibernate-onafhankelijk

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

	// Tumble status methode
	@RequestMapping(value = "/delete/{id}", method = GET)
	public String tumbleStatusKlant(@PathVariable Long id, Model model) throws Exception {
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
	public String selectKlant(@PathVariable Long id, Model model) throws Exception {
		Klant klant = klantDAO.findById(id);

		byte[] array = Files.readAllBytes(new File(pictureFolder
				+ klant.getId() + ".jpg").toPath());

		String imageDataString = Base64.encode(array);

		model.addAttribute(klant);
		model.addAttribute("plaatje", imageDataString);

		return showProfile(model, klant);
	}
}




//    // Profielpagina(op basis van url met email)
//    @RequestMapping(value = "/{email}", method = GET)
//    public String showKlantProfile(@PathVariable String email, Model model, Klant globalKlant) {
//
//        if (!model.containsAttribute("klant")) {
//            Klant klantje = new Klant(null, "FOUT", "FOUT", "FOUT", "FOUT", "FOUT", null);
//            model.addAttribute("klant", klantje);
//        }
//
//        model.addAttribute("klant", globalKlant);
//
//        return "klant/klantProfile";
//    }
