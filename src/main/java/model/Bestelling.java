package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "Bestelling")
public class Bestelling {

	@Id
	@GeneratedValue()
	@Column(name = "bestellingId")
	private long bestellingId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "klantId", nullable = false)
	protected Klant klant = new Klant();
	
	@OneToMany(mappedBy = "bestelling")
	protected Set<BestellingHeeftArtikel> besteldeArtikelen = new HashSet<>();
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "datumAanmaak", updatable = false, nullable = false)
	private Date datumAanmaak = new Date();

	@Column(name = "bestelingActief")
	private boolean bestellingActief = true;

	private ArrayList<Artikel> artikelLijst;

	public Bestelling(){
		artikelLijst = new ArrayList<Artikel>();
	}

	public Bestelling(long bestellingId, Long klantId, ArrayList<Artikel> artikelLijst, Date datumAanmaak){
		this.bestellingId = bestellingId;
		this.klant.setKlantId(klantId); // ? checken of dit werkt.
		this.artikelLijst = artikelLijst;
		this.datumAanmaak = datumAanmaak;
		bestellingActief = true;
	}

	public Bestelling(long bestellingId, Long klantId, ArrayList<Artikel> artikelLijst){
		this.bestellingId = bestellingId;
		this.klant.setKlantId(klantId); // ? checken of dit werkt.
		this.artikelLijst = artikelLijst;
		bestellingActief = true;
	}

	public void setBestellingId(long bestellingId) {
		this.bestellingId = bestellingId;
	}
	public void setKlantId(long klantId) {
		this.klant.setKlantId(klantId);
	}
	public void setArtikelLijst(ArrayList<Artikel> artikelLijst) {
		this.artikelLijst = artikelLijst;
	}
	public long getBestellingId() {
		return bestellingId;
	}
	public long getKlantId() {
		return klant.getKlantId();
	}
	public ArrayList<Artikel> getArtikelLijst() {
		return artikelLijst;
	}

	public void voegArtikelToe(Artikel artikel){
		if(artikelLijst == null)
			artikelLijst = new ArrayList<Artikel>();
		artikelLijst.add(artikel);
	}

	public void verwijderArtikel(Artikel artikel){
		if(artikelLijst.contains(artikel))
			artikelLijst.remove(artikel);
	}

	public Date getDatumAanmaak() {
		return datumAanmaak;
	}

	public void setDatumAanmaak(Date datumAanmaak) {
		this.datumAanmaak = datumAanmaak;
	}

	public boolean getBestellingActief() {
		return bestellingActief;
	}

	public void setBestellingActief(boolean bestellingActief) {
		this.bestellingActief = bestellingActief;
	}

	@Override
	public String toString() {
		StringBuilder string = new StringBuilder("BESTELLING: " + bestellingId + " van klant " + klant.getKlantId() +
				" status actief = " + bestellingActief + "\n");

		Iterator<Artikel> artikelen = getArtikelLijst().iterator();

		while (artikelen.hasNext()) {
			Artikel artikel = artikelen.next();
			string.append("\t\t" + artikel + "\n");
		}

		return string.toString();
	}
}