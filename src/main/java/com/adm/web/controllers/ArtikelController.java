package com.adm.web.controllers;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.List;

import javax.validation.Valid;

import com.sun.org.apache.xml.internal.security.utils.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.adm.database.daos.ArtikelDAO;
import com.adm.database.daos.PrijsDAO;
import com.adm.domain.Artikel;
import com.adm.domain.Prijs;
import com.adm.exceptions.DuplicateEntryException;
import com.adm.web.forms.ArtikelRegisterForm;

@Controller
@Component
@RequestMapping("/artikel")
@Transactional
@SessionAttributes({"artikel","plaatje", "shoppingCart"})
public class ArtikelController {

	private static ArtikelDAO artikelDAO;
	private static PrijsDAO prijsDAO;

//    private static String pictureFolder = "c:/harrie/uploads/data/productPictures/"; // Windows
  private static String pictureFolder = "/tmp/harrie/uploads/data/productPictures/"; // Unix-Based

	@SuppressWarnings("static-access")
	@Autowired
	public ArtikelController(ArtikelDAO artikelDAO, PrijsDAO prijsDAO) {
		this.artikelDAO = artikelDAO;
		this.prijsDAO = prijsDAO;
	}
	
	/*
	 * Controller die een overzicht van alle artikelen toont
	 */
	@RequestMapping(value = "/{type}", method = RequestMethod.GET)
	public String showAllArtikelen(Model model, @PathVariable String type) throws Exception {

		if(type.equals("all"))
			type = "%";

		if(type.equals("H"))
			type = "H%";

		if(type.equals("M"))
			type = "M%";


		// verkrijg een lijst met alle artikelen
		List<Artikel> artikelList = maakArtikelLijst(type);
		
		// stop de lijst met alle artikelen in het model
		model.addAttribute("artikelList", artikelList);
		
		// toon alle artikelen
		return "artikel/artikelLijst";
	}
	
	/*
	 * Controller die het artikelregistratieformulier toont
	 */
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	@Secured ({"ROLE_ADMIN"})
	public String showRegistrationForm(Model model) {
		model.addAttribute("artikelRegisterForm", new ArtikelRegisterForm());
		return "artikel/artikelRegisterForm";
	}
	
	/*
	 * Controller die het artikelRegistratieformulier verwerkt
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String processRegistration(
			@Valid ArtikelRegisterForm artikelRegisterForm, 
			Errors errors, RedirectAttributes model) throws Exception {
		
		// Toon fouten wanneer het artikelRegisterForm verkeerd is ingevuld
		if (errors.hasErrors()) {
			return "/artikel/artikelRegisterForm";
		}

		try {
			// Haal de artikelgegevens uit het artikelformulier
			Artikel artikel = new Artikel(
//					hanteerNaamConventie(artikelRegisterForm.getArtikelNaam()),
					artikelRegisterForm.getArtikelNaam(),
					artikelRegisterForm.getArtikelType(),
					artikelRegisterForm.getArtikelPrijs(),
					artikelRegisterForm.getArtikelLevertijd(),
					artikelRegisterForm.isArtikelOpVoorraad());

			artikel = artikelDAO.makePersistent(artikel);
			prijsDAO.makePersistent(new Prijs(artikelRegisterForm.getArtikelPrijs(), artikel));

			// Sla de afbeelding op
			if (artikelRegisterForm.getArtikelAfbeelding() != null) {
				slaAfbeeldingOp(artikel.getId(), artikelRegisterForm.getArtikelAfbeelding());
				artikel.setPlaatje(getProductPictureDataString(artikel.getId()));
			}
			
			// Save flash attribute
			model.addFlashAttribute("artikel", artikel);
			model.addAttribute("id", artikel.getId());
		}
		catch (DataIntegrityViolationException ex) {
			throw new DuplicateEntryException();
		}

		// Redirect to created profile
		return "redirect:/artikel/select/{id}";

	}

	/*
	 * Controler die een enkel artikel toont
	 */
	@RequestMapping(value = "/select/{id}", method = RequestMethod.GET)
	public String selectArtikel(@PathVariable Long id,
								Model model) throws Exception {

		// Verkrijg artikelgegevens en prijs
		Artikel artikel = artikelDAO.findById(id);
		artikel.setPlaatje(getProductPictureDataString(id));
		stopDeActuelePrijsInHetArtikel(artikel);

		// Stop artikel met prijs en plaatje in model
		model.addAttribute("plaatje", getProductPictureDataString(id));
		model.addAttribute(artikel);

		return "artikel/artikelProfile";
	}

	/*
	 * Controler die het formulier om een artikel te wijzigen toont
	 */
	@RequestMapping(value = "/wijzigen/{id}", method = RequestMethod.GET)
	@Secured({"ROLE_ADMIN"})
	public String modifyArtikel(@PathVariable Long id, Model model) {

		// Verkrijg actuele artikel en prijs gegevens
		Artikel teWijzigenArtikel = artikelDAO.findById(id);
		teWijzigenArtikel.setPlaatje(getProductPictureDataString(id));
		stopDeActuelePrijsInHetArtikel(teWijzigenArtikel);

		ArtikelRegisterForm artikelModificationForm = new ArtikelRegisterForm(
				teWijzigenArtikel.getArtikelNaam(),
				teWijzigenArtikel.getArtikelPrijs(),
				teWijzigenArtikel.getArtikelType(),
				teWijzigenArtikel.getVerwachteLevertijd(),
				teWijzigenArtikel.isInAssortiment());

		model.addAttribute("artikelRegisterForm", artikelModificationForm);
		model.addAttribute("artikel", teWijzigenArtikel);
		model.addAttribute("plaatje", getProductPictureDataString(id));

		return "artikel/artikelModificationForm";
	}
	
	/*
	 * Controller die de wijziging van het artikel verwerkt
	 */
	@RequestMapping(value = "/wijzigen/{id}", method = RequestMethod.POST)
	@Secured({"ROLE_ADMIN"})
	public String procesArtikelModification(@PathVariable Long id, Artikel artikel, 
			@Valid ArtikelRegisterForm artikelForm, 
			Errors errors, RedirectAttributes model) throws Exception {

		if (errors.hasErrors()) {
			return "/artikel/artikelModificationForm";
		}
		
		// Vergelijk de prijs voor en na de wijziging en geef aan of de prijs gewijzigd is
		BigDecimal prijsVoorWijziging = artikel.getPrijzen().get(artikel.getPrijzen().size()-1).getPrijs();
		boolean prijsIsGewijzigd = (prijsVoorWijziging.compareTo(artikelForm.getArtikelPrijs()) != 0);
		
		// Zet de gegevens uit het artikel formulier in het artikel
		artikel.setArtikelNaam(artikelForm.getArtikelNaam());
		artikel.setArtikelPrijs(artikelForm.getArtikelPrijs());
		artikel.setVerwachteLevertijd(artikelForm.getArtikelLevertijd());
		artikel.setInAssortiment(artikelForm.isArtikelOpVoorraad());
		
		// Sla het artikel op in de database
		artikel = artikelDAO.makePersistent(artikel);
		
		// Wanneer de prijs gewijzigd is wordt er een nieuwe prijs opgeslagen in de database
		if (prijsIsGewijzigd) {
			prijsDAO.makePersistent(new Prijs(artikelForm.getArtikelPrijs(), artikel));
		}
		
		// Sla de afbeelding op wanneer deze gewijzigd is
		if (!artikelForm.getArtikelAfbeelding().getOriginalFilename().isEmpty()) {
			slaAfbeeldingOp(artikel.getId(), artikelForm.getArtikelAfbeelding());
		}
		
		model.addFlashAttribute("artikel", artikel);
		
		return "redirect:/artikel/select/{id}"; // TODO - ?
	}

	/* 
	 * Controller die ervoor zorgt dat een artikel niet meer op voorraad is
	 */
	@RequestMapping(value = "/verwijderen/{id}")
	@Secured({"ROLE_ADMIN"})
	public String processArtikelOutOfStock(@PathVariable Long id, RedirectAttributes model,
			@RequestParam(value="fromProfile", defaultValue="0") int fromProfilePage){
		Artikel artikel = artikelDAO.findById(id);
		
		if (artikel.isInAssortiment()){
			artikel.setInAssortiment(false);
		}
		else {
			artikel.setInAssortiment(true);
		}
		
		artikelDAO.makePersistent(artikel);
		
		model.addFlashAttribute("artikel", artikel);
		
		
		// If the direct is from the product profile page, redirect to profilepage instead of product list.
        if (fromProfilePage == 1) {
            return "redirect:/artikel/select/{id}";
        }

        // Return to the productlist page
        return "redirect:/artikel/";
	}
	
	// Utility methods
	/*
	 * Methode die een lijst met alle artikelen teruggeeft inclusief afbeelding
	 */
	public List<Artikel> maakArtikelLijst(String type) {
		List<Artikel> artikelList = artikelDAO.findByType(type);

		// Iterate door artikelList en haal voor elk artikel de actuele prijs en afbeelding op
		for (int i = 0; i < artikelList.size(); i++) {

			Artikel tempArtikel = artikelList.get(i);
			tempArtikel.	setPlaatje(getProductPictureDataString(tempArtikel.getId()));
			stopDeActuelePrijsInHetArtikel(tempArtikel);

		}
		return artikelList;
	}
	
	/*
	 * Methode die de afbeelding van een artikel opslaat
	 */
	public static void slaAfbeeldingOp(Long id, MultipartFile afbeelding) {
		try {
		afbeelding.transferTo(new File("/data/productPictures/" +
				id + ".jpg"));
		}
		catch (IOException ex) {

		}
	}
	
	/*
	 * Method to get picture data String
	 */

	public static String getProductPictureDataString(Long id) {
		byte[] array = null;

		try {
			array = Files.readAllBytes(new File(pictureFolder
					+ id + ".jpg").toPath());
		}
		catch (IOException ex) {

		}

		return Base64.encode(array);
	}
	
	/*
	 * Methode om de huidige prijs van het artikel op te vragen
	 */
	public static void stopDeActuelePrijsInHetArtikel(Artikel artikel) { // TODO - Controleer of de prijs actueel is
		
		List<Prijs> tempPrijsList = artikel.getPrijzen();
		artikel.setArtikelPrijs(((Prijs)tempPrijsList.get(tempPrijsList.size()-1)).getPrijs());
	}
	
	/*
	 * Methode om ervoor te zorgen dat een artikel naam begin met een hoofdletter, en na elke spatie een hoofdletter.
	 */
	public static String hanteerNaamConventie(String string) {
		StringBuilder builder = new StringBuilder();

		// Start with a lowercase string
		String s = string.toLowerCase();

		// Split on whitespace
		String[] tokens = s.split("[\\s]");

		// Iterate over each word and capitalize the first character
		for(int i = 0; i < tokens.length; i++) {
			Character.toUpperCase(tokens[i].charAt(0));
			builder.append(tokens[i]);
			builder.append(' ');
		}
		return builder.toString().trim();
	}
}