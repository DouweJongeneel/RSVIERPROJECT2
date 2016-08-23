package com.adm.web.controllers;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;
import com.sun.org.apache.xml.internal.security.utils.Base64;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.adm.database.daos.ArtikelDAO;
import com.adm.database.daos.PrijsDAO;
import com.adm.domain.Artikel;
import com.adm.domain.Prijs;
import com.adm.exceptions.DuplicateEntryException;
import com.adm.web.forms.KlantRegisterForm;
import com.adm.web.forms.ArtikelRegisterForm;

@Controller
@Component
@RequestMapping("/artikel")
@Transactional
@SessionAttributes({"artikel","plaatje"})
public class ArtikelController {

	private static ArtikelDAO artikelDAO;
	private PrijsDAO prijsDAO;

	@Autowired
	public ArtikelController(ArtikelDAO artikelDAO, PrijsDAO prijsDAO) {
		this.artikelDAO = artikelDAO;
		this.prijsDAO = prijsDAO;
	}

	@SuppressWarnings("restriction")
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String showAlleArtikelen(Model model) throws Exception {
		List<Artikel> artikelList = maakArtikelLijst();

		model.addAttribute("artikelList", artikelList);

		return "artikel/artikelLijst";
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String showRegistrationForm(Model model) {
		model.addAttribute("artikelRegisterForm", new ArtikelRegisterForm());
		return "artikel/artikelRegisterForm";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String processRegistration(
			@Valid ArtikelRegisterForm artikelRegisterForm, 
			Errors errors, RedirectAttributes model) throws Exception {

		if (errors.hasErrors()) {
			return "/artikel/artikelRegisterForm";
		}

		try {
			// Save artikel
			Artikel nieuwArtikel = new Artikel(
					hanteerNaamConventie(artikelRegisterForm.getArtikelNaam()),
					artikelRegisterForm.getArtikelPrijs(), 
					artikelRegisterForm.getArtikelLevertijd(),
					artikelRegisterForm.isArtikelOpVoorraad());

			nieuwArtikel = artikelDAO.makePersistent(nieuwArtikel);
			nieuwArtikel.setArtikelPrijs(artikelRegisterForm.getArtikelPrijs());
			prijsDAO.makePersistent(new Prijs(artikelRegisterForm.getArtikelPrijs(), nieuwArtikel));

			// Save artikelAfbeelding
			MultipartFile artikelAfbeelding = artikelRegisterForm.getArtikelAfbeelding();
			artikelAfbeelding.transferTo(new File("/data/productPictures/" +
					nieuwArtikel.getId() + ".jpg"));

			// Save flash attribute
			model.addFlashAttribute("artikel", nieuwArtikel);
			model.addAttribute("id", nieuwArtikel.getId());
		}
		catch (DataIntegrityViolationException ex) {
			throw new DuplicateEntryException();
		}

		// Redirect to created profile
		return "redirect:/artikel/{id}";

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String showProduct(
			@PathVariable("id") Long id, Model model, Artikel globalArtikel) {

		model.addAttribute("artikel", globalArtikel);
		return "artikel/artikelProfile";

	}
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String showProduct(Model model, Artikel artikel) {
		model.addAttribute("artikel", artikel);
		return "artikel/artikelProfile";
	}

	@RequestMapping(value = "/select/{id}", method = RequestMethod.GET)
	public String selectArtikel(@PathVariable Long id, Model model) throws Exception {
		Artikel artikel = artikelDAO.findById(id);
		String imageDataString = getProductPictureDataString(id);

		model.addAttribute("plaatje", imageDataString);
		model.addAttribute(artikel);

		return showProduct(model, artikel);
	}

	/*
	 * Methodes om een artikel te wijzigen
	 */
	@RequestMapping(value = "/wijzigen/{id}", method = RequestMethod.GET)
	public String modifyArtikel(@PathVariable Long id, Model model) {

		// Verkrijg actuele artikel en prijs gegevens
		Artikel teWijzigenArtikel = artikelDAO.findById(id);
		Set<Prijs> artikelPrijzen = teWijzigenArtikel.getPrijzen();

		ArtikelRegisterForm artikelModificationForm = new ArtikelRegisterForm(
				teWijzigenArtikel.getArtikelNaam(),
				((Prijs)artikelPrijzen.toArray()[artikelPrijzen.size()-1]).getPrijs(),
				teWijzigenArtikel.getVerwachteLevertijd(),
				teWijzigenArtikel.isInAssortiment());

		model.addAttribute("artikelRegisterForm", artikelModificationForm);
		model.addAttribute("plaatje", getProductPictureDataString(id));

		return "artikel/artikelModificationForm";
	}

	@RequestMapping(value = "/wijzigen/{id}", method = RequestMethod.POST)
	public String procesArtikelModification(@PathVariable Long id, @Valid ArtikelRegisterForm artikelRegisterForm, 
			Errors errors, RedirectAttributes model) throws Exception {

		if (errors.hasErrors()) {
			return "/artikel/artikelModificationForm";
		}
		return "redirect:/artikel/{id}"; // TODO - ?
	}


	// Utility methods
	/*
	 * Methode die een lijst met alle artikelen teruggeeft inclusief afbeelding
	 */
	public static List<Artikel> maakArtikelLijst() {
		List<Artikel> artikelList = artikelDAO.findAll();
		Set<Prijs> tempPrijsSet = null;

		// Laad product afbeeldingen en prijzen
		for (int i = 0; i < artikelList.size(); i++) {

			artikelList.get(i).setPlaatje(getProductPictureDataString(artikelList.get(i).getId()));

			/*
			 * Haal de prijslijst uit het artikel dat zich op de i'de index bevind en wijs de actuele
			 * prijs toe aan het artikel object dat zich in de arikelLijst bevind zodat de actuele prijzen 
			 * in de lijst met producten wordt getoond. 
			 * 
			 * TODO - Controleer of prijzen actueel zijn!!!
			 */ 
			tempPrijsSet = artikelList.get(i).getPrijzen();
			artikelList.get(i).setArtikelPrijs(((Prijs)tempPrijsSet.toArray()[tempPrijsSet.size()-1]).getPrijs());
			// Wanneer je door een collectie iterate wordt deze opgehaald uit de DB bij een actieve connectie

		}
		return artikelList;
	}
	/*
	 * Method to get picture data String
	 */
	@SuppressWarnings("restriction")
	public static String getProductPictureDataString(Long id) {
		byte[] array = null;

		try {
			array = Files.readAllBytes(new File("/tmp/harrie/uploads/data/productPictures/"
					+ id + ".jpg").toPath());
		}
		catch (IOException ex) {
			// TODO -
		}

		return Base64.encode(array);
	}

	/*
	 * Methode om ervoor te zorgen dat een artikel naam begin met een hoofdletter, en na elke spatie een hoofdletter.
	 */
	public static String hanteerNaamConventie(String string) {
		StringBuilder builder = new StringBuilder();
		int indexToUpperCase = 0;

		// Start with a lowercase string
		String s = string.toLowerCase();
		// Split on whitespace
		String[] tokens = s.split("[\\s]");
		// Iterate over each wordt and capitalize the first character
		for(int i = 0; i < tokens.length; i++) {
			if (i > 0) {
				builder.append(' ');
				indexToUpperCase = builder.length();
			}
			builder.append(tokens[i]);
			builder.setCharAt(indexToUpperCase, Character.toUpperCase(tokens[i].charAt(0)));
		}
		return builder.toString();
	}


}
