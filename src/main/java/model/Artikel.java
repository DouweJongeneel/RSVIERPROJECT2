package model;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity
public class Artikel implements Comparable<Artikel>{

	@Id
	@SequenceGenerator(name = "artikelId", sequenceName = "zArtikel_sequence")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "artikelId")
	private int id;

	@Column
	private String artikelNaam;

	@Column
	private BigDecimal artikelPrijs;

	@OneToMany
	private Set<Prijs> prijs;

	@Column
	private String datumAanmaak;

	@Column
	private int verwachteLevertijd;

	@Column
	private boolean inAssortiment;

	//Constructors
	public Artikel() {
		datumAanmaak = new Date(System.currentTimeMillis()).toString();
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
	public int getId() {
		return id;
	}

	public String getArtikelNaam() {
		return artikelNaam;
	}

	public BigDecimal getArtikelPrijs() {
		return artikelPrijs;
	}

	public Set<Prijs> getPrijs() {
		return prijs;
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

	public void setId(int id) {
		this.id = id;
	}

	public void setArtikelNaam(String artikelNaam) {
		this.artikelNaam = artikelNaam;
	}

	public void setArtikelPrijs(BigDecimal artikelPrijs) {
		this.artikelPrijs = artikelPrijs;
	}

	public void setPrijs(Set<Prijs> prijs) {
		this.prijs = prijs;
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
		return id == ((Artikel)o).getId();
	}

	@Override
	public String toString(){
		return "ARTIKEL: " + id + "\t " + artikelNaam + "\t $" + artikelPrijs.toPlainString()
		+ "\t " + datumAanmaak + "\t " + verwachteLevertijd + "\t "
		+ inAssortiment;
	}

	public int compareTo(Artikel o) {
		if (this.id == o.getId())
			return 0;
		else if (this.id > o.getId())
			return 1;
		else
			return -1;
	}
}