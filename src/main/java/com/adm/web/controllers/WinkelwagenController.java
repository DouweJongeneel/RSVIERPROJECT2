package com.adm.web.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.adm.domain.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import com.adm.database.daos.ArtikelDAO;
import com.adm.database.daos.PrijsDAO;
import com.adm.database.service.ArtikelService;
import com.adm.domain.Artikel;
import com.adm.domain.BestelArtikel;
import com.adm.domain.Prijs;

@Controller
@Component
@Transactional
@SessionAttributes({ "klant", "shoppingCart", "artikel"})
public class WinkelwagenController {

	private ArtikelDAO artikelDAO;
	private PrijsDAO prijsDAO;

	@Autowired(required=true)
	@Qualifier(value="artikelService")
	public void setArtikelDAO(ArtikelService as){
		artikelDAO = as.getArtikelDAO();
	}

	@Autowired(required=true)
	@Qualifier(value="prijsDAO")
	public void setPrijsDAO(PrijsDAO pd){
		prijsDAO = pd;
	}


	@RequestMapping(value = "/bestelling/{id}", method = RequestMethod.POST)
	public String addArticleShoppingCart(HttpSession session,
										 Model model, Artikel artikel,
										 int aantal, ShoppingCart shoppingCart) {

		Iterator<Prijs> itPrijs = artikel.getPrijzen().iterator();
		Prijs p = null;
		while(itPrijs.hasNext()){
			p = itPrijs.next();
		}

		@SuppressWarnings("unchecked")
//		HashSet<BestelArtikel> winkelwagen = (HashSet<BestelArtikel>)session.getAttribute("winkelwagen");

		BestelArtikel bestArt = new BestelArtikel(p, artikel, aantal);

		shoppingCart.getWinkelwagen().add(bestArt);

		model.addAttribute("artikelen", setCatalogusPrijs(artikelDAO.findAll()));
		model.addAttribute("winkelwagenCount", shoppingCart.getWinkelwagen().size());
		model.addAttribute("winkelwagen", shoppingCart.getWinkelwagen());

		return winkelwagen(model, session, shoppingCart); //TODO met redirect maken
	}

	@RequestMapping(value = "/bestelling/winkelwagen", method = RequestMethod.GET)
	public String winkelwagen(Model model, HttpSession session, ShoppingCart shoppingCart) {

		ArrayList<BestelArtikel> list = new ArrayList<BestelArtikel>();
		list.addAll(shoppingCart.getWinkelwagen());

		model.addAttribute("artikelen", list);
		model.addAttribute("totaalPrijs", totaalPrijsBestelling(shoppingCart.getWinkelwagen().iterator()));
		model.addAttribute("winkelwagen", shoppingCart.getWinkelwagen());

		return "bestelling/winkelwagen/winkelwagen";
	}

	@RequestMapping(value = "/bestelling/winkelwagen", method = RequestMethod.POST)
	public String editWinkelwagen(long artikelId, Model model, HttpSession session, int aantal, ShoppingCart shoppingCart) {

		Iterator<BestelArtikel> bestellingIterator = shoppingCart.getWinkelwagen().iterator();
		BestelArtikel bestArt = new BestelArtikel();

		while(bestellingIterator.hasNext()){
			bestArt = bestellingIterator.next();

			if(bestArt.getArtikel().getId() == artikelId){
				break;
			}

		}
		if(aantal > 0)
			bestArt.setAantal(aantal);
		else
			shoppingCart.getWinkelwagen().remove(bestArt);


		ArrayList<BestelArtikel> list = new ArrayList<BestelArtikel>();
		list.addAll(shoppingCart.getWinkelwagen());

		model.addAttribute("artikelen", list);
		model.addAttribute("totaalPrijs", totaalPrijsBestelling(shoppingCart.getWinkelwagen().iterator()));
		model.addAttribute("winkelwagen", shoppingCart.getWinkelwagen());

		return "bestelling/winkelwagen/winkelwagen";
	}


	/* Extra methoden voor verwerken data */


	/*
	 * Rekent de totaalprijs van de 
	 * bestelling uit 
	 * 
	 */

	private BigDecimal totaalPrijsBestelling(Iterator<BestelArtikel> bestelArtikelen){

		BigDecimal totaal = new BigDecimal("0");

		while(bestelArtikelen.hasNext()){

			BestelArtikel BA = bestelArtikelen.next();
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
	private List<BestelArtikel> setCatalogusPrijs(List<Artikel> artikelen){
		List<BestelArtikel> artikelenLijst = new ArrayList<BestelArtikel>();
		Iterator<Artikel> it = artikelen.iterator();
		Artikel art;
		Prijs pr = new Prijs();
		while(it.hasNext()){

			art = it.next();
			Iterator<Prijs> prijsIt = art.getPrijzen().iterator();

			while(prijsIt.hasNext())
				pr = prijsIt.next();
			art.setArtikelPrijs(pr.getPrijs());
			artikelenLijst.add(new BestelArtikel(pr, art, 0));
		}
		return artikelenLijst;
	}
}