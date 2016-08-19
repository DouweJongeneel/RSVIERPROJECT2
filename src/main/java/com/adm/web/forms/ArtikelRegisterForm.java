package com.adm.web.forms;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import com.adm.domain.Artikel;

public class ArtikelRegisterForm {
	
	// input fields
	@NotNull
	@Size(min = 2, max = 30, message = "{productName.size}")
	private String artikelNaam;
	
	@NotNull
	@Min(value = 0, message = "{productPrice.min}")
	private BigDecimal artikelPrijs;
	
	@Min(value = 1, message = "{productDeliveryTime.min}")
	private Integer artikelLevertijd;
	
	private boolean artikelOpVoorraad;
	
	private MultipartFile artikelAfbeelding;
	
	public ArtikelRegisterForm() {
	}
	
	//Getters and Setters
	
	public String getArtikelNaam() {
		return artikelNaam;
	}

	public BigDecimal getArtikelPrijs() {
		return artikelPrijs;
	}

	public Integer getArtikelLevertijd() {
		return artikelLevertijd;
	}

	public boolean isArtikelOpVoorraad() {
		return artikelOpVoorraad;
	}

	public MultipartFile getArtikelAfbeelding() {
		return artikelAfbeelding;
	}

	public void setArtikelNaam(String artikelNaam) {
		this.artikelNaam = artikelNaam;
	}

	public void setArtikelPrijs(BigDecimal artikelPrijs) {
		this.artikelPrijs = artikelPrijs;
	}

	public void setArtikelLevertijd(Integer artikelLevertijd) {
		this.artikelLevertijd = artikelLevertijd;
	}

	public void setArtikelOpVoorraad(boolean artikelOpVoorraad) {
		this.artikelOpVoorraad = artikelOpVoorraad;
	}

	public void setArtikelAfbeelding(MultipartFile artikelAfbeelding) {
		this.artikelAfbeelding = artikelAfbeelding;
	}
	
	// TODO extra methode
	public Artikel toArtikel() {
		return new Artikel("BrianGriffin", new BigDecimal(10000), 3, true);
	}
}
