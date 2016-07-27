package model;

import java.math.BigDecimal;

import model.Prijs;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/*
 * Created by Douwe_Jongeneel on 06-06-16.
 *
 * Dit is de Artikel Pojo + GS zodat er Artikel objecten
 * in een LinkedHasMap kunnen worden opgeslagen in de database.
 */
@Entity(name = "artikel")
public class Artikel implements Comparable<Artikel>{
	
	@ManyToOne //Many to one side is nooit mappedBy, dit is de eigenaars kant
	@JoinColumn(name = "prijsID", updatable = false, insertable = false) // many to one has to disable writing
	
	@Id
	@GeneratedValue
	@Column(name = "artikelId")
	private long artikelId;
	
	@Column(name = "omschrijving")
	private String artikelNaam;
	
	// TODO - bestellingheeftartikel
	private int aantalBesteld;
	
	@Column(name = "prijs") //TODO- prijstabel
	private BigDecimal artikelPrijs;
	
	// nullable = false --> om het juiste schema te genereren moet de joinColumn als notnull verklaard worden, schema
	// generatie in hibernate is afhankelijk van de joinColumn aan de manyToOne side, aangezien de join column
	// 2 x gedefinieert wordt moet aangegeven worden welke van de twee hibernate pakt.
	
	@JoinColumn(name = "prijsId", nullable = false)
	private Prijs prijs; //default naar prijsId
	
	@Column(name = "datumAanmaak")
	private String datumAanmaak;
	
	@Column(name = "verwachteLevertijd")
	private int verwachteLevertijd;
	
	@Column(name = "inAssortiment")
	private boolean inAssortiment;

	//Constructors
	public Artikel() {
	}
	public Artikel(String artikelNaam, BigDecimal artikelPrijs,
			String datumAanmaak, int verwachteLevertijd, boolean inAssortiment) {
		this.artikelNaam = artikelNaam;
		this.artikelPrijs = artikelPrijs;
		this.datumAanmaak = datumAanmaak;
		this.verwachteLevertijd = verwachteLevertijd;
		this.inAssortiment = inAssortiment;
	}

	//Getters and Setters
	public long getArtikelId() {
		return artikelId;
	}
	public String getArtikelNaam() {
		return artikelNaam;
	}
	public int getAantalBesteld() {
		return aantalBesteld;
	}
	public BigDecimal getArtikelPrijs() {
		return artikelPrijs;
	}
	public long getPrijsId() {
		return prijs.getPrijsId();
	}
	public String getDatumAanmaak() {
		return datumAanmaak;
	}
	public int getVerwachteLevertijd() {
		return verwachteLevertijd;
	}
	public boolean isInAssortiment() {
		return inAssortiment;
	}

	public void setArtikelId(int artikelId) {
		this.artikelId = artikelId;
	}
	public void setArtikelNaam(String artikelNaam) {
		this.artikelNaam = artikelNaam;
	}
	public void setAantalBesteld(int aantalBesteld) {
		this.aantalBesteld = aantalBesteld;
	}
	public void setArtikelPrijs(BigDecimal artikelPrijs) {
		this.artikelPrijs = artikelPrijs;
	}
	public void setPrijsId(long prijs_id) {
		this.prijs.setPrijsId(prijs_id);
	}
	public void setDatumAanmaak(String datumAanmaak) {
		this.datumAanmaak = datumAanmaak;
	}
	public void setVerwachteLevertijd(int verwachteLevertijd) {
		this.verwachteLevertijd = verwachteLevertijd;
	}
	public void setInAssortiment(boolean inAssortiment) {
		this.inAssortiment = inAssortiment;
	}

	// Methodes die overschreven worden
	@Override
	public int hashCode(){
		return artikelNaam.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		return artikelId == ((Artikel)o).getArtikelId();
	}

	@Override
	public String toString(){
		return "ARTIKEL: " + artikelId + "\t " + artikelNaam + "\t $" + artikelPrijs.toPlainString()
		+ "\t prijs id " + prijs.getPrijsId() + "\t " + datumAanmaak + "\t " + verwachteLevertijd + "\t "
		+ inAssortiment;
	}

	@Override
	public int compareTo(Artikel o) {
		if (this.artikelId == o.getArtikelId())
			return 0;
		else if (this.artikelId > o.getArtikelId())
			return 1;
		else
			return -1;
	}
}