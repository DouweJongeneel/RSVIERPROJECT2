package model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import model.Prijs;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/*
 * Created by Douwe_Jongeneel on 06-06-16.
 *
 * Dit is de Artikel Pojo + GS zodat er Artikel objecten
 * in een LinkedHasMap kunnen worden opgeslagen in de database.
 */
@Entity(name = "artikel")
public class Artikel implements Comparable<Artikel>{
	
	@Id
	@GeneratedValue
	@Column(name = "artikelId")
	protected long artikelId;
	
	@Column(name = "omschrijving")
	protected String artikelNaam;
	
	/*
	 * @oneToMany --> cascadeType.PERSIST zorgt ervoor dat wanneer een artikel opgeslagen wordt 
	 * zijn prijs ook opgeslagen wordt.
	 * @OnDelete --> Staat meestal aan de ManyToOne kant, maar als de mapping bidirectioneel is 
	 * herkent Hibernate deze annotatie alleen aan de OneToMany kant.
	 */
	@OneToMany(mappedBy = "artikel", fetch = FetchType.LAZY,	cascade = CascadeType.PERSIST)
	@org.hibernate.annotations.OnDelete( action = org.hibernate.annotations.OnDeleteAction.CASCADE)
	protected Set<Prijs> prijzen = new HashSet<>(); // als het goed is default dit naar prijsId!!!
	
	// bestellingheeftartikel
	@OneToMany(mappedBy = "artikel", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@org.hibernate.annotations.OnDelete( action = org.hibernate.annotations.OnDeleteAction.CASCADE)
	protected Set<BestellingHeeftArtikel> BestelLijstVanDitArtikel = new HashSet<>();
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "datumAanmaak", updatable = false, nullable = false)
	protected Date datumAanmaak = new Date();
	
	@Column(name = "verwachteLevertijd")
	protected int verwachteLevertijd;
	
	@Column(name = "inAssortiment")
	protected boolean inAssortiment;

	//Constructors
	public Artikel() {
	}
	public Artikel(String artikelNaam) {
		this.artikelNaam = artikelNaam;
	}
	public Artikel(String artikelNaam, Prijs artikelPrijs,
			Date datumAanmaak, int verwachteLevertijd, boolean inAssortiment) {
		this.artikelNaam = artikelNaam;
		this.prijzen.add(artikelPrijs);
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
	public long getPrijsId() {
		return prijs.getPrijsId();
	}
	public BigDecimal getArtikelPrijs() {
		return artikelPrijs;
	}
	public int getAantalBesteld() {
		return aantalBesteld;
	}
	public Date getDatumAanmaak() {
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
	public void setPrijsId(long prijs_id) {
		this.prijs.setPrijsId(prijs_id);
	}
	public void setArtikelPrijs(BigDecimal artikelPrijs) {
		this.artikelPrijs = artikelPrijs;
	}
	public void setAantalBesteld(int aantalBesteld) {
		this.aantalBesteld = aantalBesteld;
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