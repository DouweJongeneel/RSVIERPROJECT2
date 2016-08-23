package com.adm.web.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.adm.database.daos.ArtikelDAO;
import com.adm.database.daos.BestellingDAO;
import com.adm.database.service.ArtikelService;
import com.adm.database.service.BestellingService;
import com.adm.domain.Artikel;
import com.adm.domain.BestelArtikel;
import com.adm.domain.Bestelling;
import com.adm.domain.Klant;
import com.adm.domain.Prijs;

@Controller
@Component
@Transactional
@SessionAttributes({ "klant", "plaatje"})
public class BestellingController {

	private BestellingDAO bestellingDAO;
	private ArtikelDAO artikelDAO;

	@Autowired(required=true)
	@Qualifier(value="bestellingService")
	public void setBestellingService(BestellingService bs){
		bestellingDAO = bs.getBestellingDAO();
	}

	@Autowired(required=true)
	@Qualifier(value="artikelService")
	public void setArtikelDAO(ArtikelService as){
		artikelDAO = as.getArtikelDAO();
	}

	@RequestMapping(value = "/bestelling", method = RequestMethod.GET)
	public String listBestelling(Model model, Klant klant) {
		List<Bestelling> bestellingen = bestellingDAO.findBestellingByKlantId(klant.getId());
		model.addAttribute("bestellingList", bestellingen);
		return "bestelling/bestellingLijst";
	}

	@RequestMapping(value = "/bestelling/select/{bestelId}", method = RequestMethod.GET)
	public String listBestelling(@PathVariable("bestelId") long bestelId, Model model) {
		List<BestelArtikel> bestelArtikelen = bestellingDAO.findBestellingArtikelen(bestelId);

		model.addAttribute("bestelArtikelen", bestelArtikelen);
		model.addAttribute("leegBestelArtikel", new BestelArtikel());
		model.addAttribute("totaalPrijs", totaalPrijsBestelling(bestelArtikelen));

		return "bestelling/bestelling";
	}

	@RequestMapping(value = "/bestelling/select/{bestelId}", method = RequestMethod.POST)
	public String editBestelling(@PathVariable("bestelId") long bestelId, long bestArtikelId, Model model, BestelArtikel bestelArtikel, int aantal) {
		Bestelling bestelling = bestellingDAO.findById(bestelId);

		Iterator<BestelArtikel> bestellingIterator = bestelling.getBestelArtikelSet().iterator();
		BestelArtikel bestArt = new BestelArtikel();

		bestArt.setId(0L);

		while(bestellingIterator.hasNext() && (bestArt.getId() != bestArtikelId))
			bestArt = bestellingIterator.next();

		bestArt.setAantal(aantal);

		model.addAttribute("bestelArtikelen", bestelling.getBestelArtikelSet());
		return "bestelling/bestelling";
	}


	@RequestMapping(value = "/bestelling/catalogus", method = RequestMethod.GET)
	public String maakBestelling(Model model) {
		List<Artikel> artikelen = artikelDAO.findAll();
		setCatalogusPrijs(artikelen);
		model.addAttribute("artikelen", artikelen);
		model.addAttribute("bestelLijst", new ArrayList<BestelArtikel>());
		return "bestelling/catalogus";
	}

	@RequestMapping(value = "/bestelling/catalogus/", method = RequestMethod.POST)
	public String voegToeAanBestelling(BestelArtikel bestArt, Bestelling best) {
		best.voegArtikelToe(bestArt);
		return "bestelling/catalogus";
	}

	@RequestMapping(value = "/bestelling/catalogus/bevestig", method = RequestMethod.POST)
	public String bewaarBestelling(Bestelling bestelling) {
		bestellingDAO.makePersistent(bestelling);
		return "bestelling/catalogus";
	}

	/* Extra methoden voor verwerken data */


	/*
	 * Rekent de totaalprijs van de 
	 * bestelling uit 
	 * 
	 */

	private BigDecimal totaalPrijsBestelling(List<BestelArtikel> bestelArtikelen){

		BigDecimal totaal = new BigDecimal("0");
		Iterator<BestelArtikel> it = bestelArtikelen.iterator();

		while(it.hasNext()){

			BestelArtikel BA = it.next();
			BigDecimal prijs = BA.getPrijs().getPrijs();
			BigDecimal aantal = new BigDecimal(BA.getAantal());
			totaal = totaal.add( prijs.multiply(aantal) );

		}

		return totaal;
	}

	/*
	 * Loopt alle artikelen door om
	 * de laatste prijsnotatie in de
	 * artikelPrijs te zetten voor
	 * de catalogus weergave
	 * 
	 */
	private void setCatalogusPrijs(List<Artikel> artikelen){

		Iterator<Artikel> it = artikelen.iterator();
		Artikel art;
		Prijs pr = new Prijs();
		while(it.hasNext()){

			art = it.next();
			Iterator<Prijs> prijsIt = art.getPrijs().iterator();

			while(prijsIt.hasNext())
				pr = prijsIt.next();
			art.setArtikelPrijs(pr.getPrijs());
		}
	}
}