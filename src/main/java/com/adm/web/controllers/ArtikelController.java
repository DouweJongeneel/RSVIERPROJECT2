package com.adm.web.controllers;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.adm.database.daos.ArtikelDAO;
import com.adm.domain.Artikel;
import com.adm.web.forms.KlantRegisterForm;
import com.adm.web.forms.ArtikelRegisterForm;

@RequestMapping("/artikel")
@Controller
public class ArtikelController {
	
	private ArtikelDAO artikelDAO;
	
	@Autowired
	public ArtikelController(ArtikelDAO artikelDAO) {
		this.artikelDAO = artikelDAO;
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String showAllProducts() { //
		return "artikel/artikel";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String showRegistrationForm(Model model) {
        model.addAttribute("artikelRegisterForm", new ArtikelRegisterForm());
        return "artikel/artikelRegisterForm";
    }
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String processRegistration(
			@Valid ArtikelRegisterForm artikelRegisterForm, 
			Errors errors, RedirectAttributes model) throws IOException {
		
		if (errors.hasErrors()) {
			return "/artikel/artikelRegisterForm";
		}
		
		// Save artikel
		Artikel nieuwArtikel = new Artikel(
				artikelRegisterForm.getArtikelNaam(),
				artikelRegisterForm.getArtikelPrijs(), 
				artikelRegisterForm.getArtikelLevertijd(),
				artikelRegisterForm.isArtikelOpVoorraad());
		
		artikelDAO.makePersistent(nieuwArtikel);
		
		// Save artikelAfbeelding
		MultipartFile artikelAfbeelding = artikelRegisterForm.getArtikelAfbeelding();
		artikelAfbeelding.transferTo(new File("/data/productPictures/" +
		nieuwArtikel.getArtikelNaam()));
		// Save flash attribute
		model.addFlashAttribute("artikel", nieuwArtikel);
		
		// Redirect to created profile
		return "redirect:artikel/{Id}";
	}
	
	@RequestMapping(value = "/{Id}", method = RequestMethod.GET)
	public String showProduct(
			@PathVariable("Id") Integer Id, Model model) {
		
//		if (!model.containsAttribute("artikel")) {
//			Artikel artikel = new Artikel("Zeehond", new BigDecimal(500), 4, true);
//			model.addAttribute("artikel", artikel);
//		}
		Artikel artikel = artikelDAO.findById(Id);
	    model.addAttribute(artikel);
	    return "redirect: artikel/artikelProfile";
		
	}

}
