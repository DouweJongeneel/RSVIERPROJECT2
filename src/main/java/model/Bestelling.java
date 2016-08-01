package model;

import java.util.HashSet;
import java.util.Set;

import model.BestelArtikel;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity
public class Bestelling{

	@Id
	@SequenceGenerator(name = "bestellingId", sequenceName = "zBestelling_sequence")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "bestellingId")
	private Long id;

	@Column(nullable = false)
	private String bestelNummer;
	
	@Column(nullable = false)
	private boolean bestellingActief = true;

	@Column(nullable = false)
	private String datumAanmaak;
	
	@OneToMany(mappedBy = "bestelling")
	@Column(nullable = false)
	private Set<Factuur> factuurSet = new HashSet<Factuur>();
	
	@ManyToOne(optional = false)
	private Klant klant;

	@OneToMany(mappedBy = "bestelling")
	@Column(nullable = false)
	protected Set<BestelArtikel> bestelArtikelSet = new HashSet<BestelArtikel>();

	public Bestelling(){
		bestelArtikelSet = new HashSet<BestelArtikel>();
	}

	public Bestelling(Long id, Klant klant, Set<BestelArtikel> bestelArtikelSet, String datumAanmaak){
		this.id = id;
		this.klant = klant;
		this.bestelArtikelSet = bestelArtikelSet;
		this.datumAanmaak = datumAanmaak;
		bestellingActief = true;
	}

	public Bestelling(Long id, Klant klant, Set<BestelArtikel> bestelArtikelSet){
		this.id = id;
		this.klant = klant;
		this.bestelArtikelSet = bestelArtikelSet;
		bestellingActief = true;
	}

	
	public Long getId() {
		return id;
	}

	public String getBestelNummer() {
		return bestelNummer;
	}

	public boolean isBestellingActief() {
		return bestellingActief;
	}

	public String getDatumAanmaak() {
		return datumAanmaak;
	}

	public Klant getKlant() {
		return klant;
	}

	public Set<BestelArtikel> getBestelArtikelSet() {
		return bestelArtikelSet;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setBestelNummer(String bestelNummer) {
		this.bestelNummer = bestelNummer;
	}

	public void setBestellingActief(boolean bestellingActief) {
		this.bestellingActief = bestellingActief;
	}

	public void setDatumAanmaak(String datumAanmaak) {
		this.datumAanmaak = datumAanmaak;
	}

	public void setKlant(Klant klant) {
		this.klant = klant;
	}

	public void setBestelArtikelSet(Set<BestelArtikel> bestelArtikelSet) {
		this.bestelArtikelSet = bestelArtikelSet;
	}

	public void voegArtikelToe(BestelArtikel bestelArtikel){
		if(bestelArtikelSet == null)
			bestelArtikelSet = new HashSet<BestelArtikel>();
		bestelArtikelSet.add(bestelArtikel);
	}

	public void verwijderArtikel(BestelArtikel bestelArtikel){
		if(bestelArtikelSet.contains(bestelArtikel))
			bestelArtikelSet.remove(bestelArtikel);
	}

}