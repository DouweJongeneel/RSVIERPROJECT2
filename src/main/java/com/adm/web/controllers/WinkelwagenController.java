package com.adm.web.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;


import com.adm.domain.ShoppingCart;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import com.adm.domain.Artikel;
import com.adm.domain.BestelArtikel;
import com.adm.domain.Prijs;

@Controller
@Component
@Transactional
@SessionAttributes({ "klant", "shoppingCart", "artikel"})
public class WinkelwagenController {

	/** METHOD TO ADD AN PRODUCT TO THE SHOPPING CART **/
	@RequestMapping(value = "/bestelling/{id}", method = RequestMethod.POST)
	public String addArticleShoppingCart(Model model, Artikel artikel,
										 int aantal, ShoppingCart shoppingCart) {

		Iterator<Prijs> itPrijs = artikel.getPrijzen().iterator();
		Prijs p = null;
		while(itPrijs.hasNext()){
			p = itPrijs.next();
		}

		BestelArtikel bestArt = new BestelArtikel(p, artikel, aantal);

		shoppingCart.getWinkelwagen().add(bestArt);

		model.addAttribute("shoppingCart", shoppingCart);

		return "redirect:/bestelling/winkelwagen";
	}

	/** METHOD FOR SHOWING THE SHOPPING CART **/
	@RequestMapping(value = "/bestelling/winkelwagen", method = RequestMethod.GET)
	public String winkelwagen(Model model, ShoppingCart shoppingCart) {

		ArrayList<BestelArtikel> list = new ArrayList<BestelArtikel>();
		list.addAll(shoppingCart.getWinkelwagen());

		model.addAttribute("artikelen", list);
		model.addAttribute("totaalPrijs", totaalPrijsBestelling(shoppingCart.getWinkelwagen().iterator()));
		model.addAttribute("winkelwagen", shoppingCart.getWinkelwagen());

		return "bestelling/winkelwagen/winkelwagen";
	}

	/** METHOD FOR UPDATING THE SHOPPING CART **/
	@RequestMapping(value = "/bestelling/winkelwagen", method = RequestMethod.POST)
	public String editWinkelwagen(long artikelId, Model model, int aantal, ShoppingCart shoppingCart) {

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

	private BigDecimal totaalPrijsBestelling(Iterator<BestelArtikel> bestelArtikelen) {

        BigDecimal totaal = new BigDecimal("0");

        while (bestelArtikelen.hasNext()) {

            BestelArtikel BA = bestelArtikelen.next();
            BigDecimal prijs = BA.getPrijs().getPrijs();
            BigDecimal aantal = new BigDecimal(BA.getAantal());
            totaal = totaal.add(prijs.multiply(aantal));

        }

        return totaal;
    }
}