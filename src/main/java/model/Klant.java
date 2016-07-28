package model;

import java.sql.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity
public class Klant{

	@Id
	@SequenceGenerator(name = "klantId", sequenceName = "zKlant_sequence")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "klantId")
	private long id;

	@Column
	private String voornaam;

	@Column
	private String achternaam;

	@Column
	private String tussenvoegsel;

	@Column
	private String email;

	@Column
	private String datumAanmaak;

	@Column
	private String datumGewijzigd;

	@Column
	private String klantActief;

	@ManyToMany
	@JoinTable(name = "klant_adres_adrestype",
		joinColumns = @JoinColumn(name = "klant_id"),
		inverseJoinColumns = @JoinColumn(name = "adrestype_id"))
	@MapKeyJoinColumn(name = "adres_id")
	protected Map<Adres, AdresType> adresGegevens = new HashMap<Adres, AdresType>();

	@OneToMany(mappedBy = "klant")
	protected Set<Bestelling> bestellingen = new HashSet<Bestelling>();

	//  Default public no-arg constructor
	public Klant() {
	}

	// Standaard public constructor met basis parameters
	public Klant(long id,
			String voornaam,
			String achternaam,
			String tussenvoegsel,
			String email,
			Map<Adres, AdresType> adresGegevens) {

		if (adresGegevens != null){
			this.adresGegevens = adresGegevens;
		}

		this.id = id;
		this.voornaam = voornaam;
		this.achternaam = achternaam;
		this.tussenvoegsel = tussenvoegsel;
		this.datumAanmaak = new Date(System.currentTimeMillis()).toString();
		this.email = email;
	}

	// Constructor voor alle variabelen, wordt over het algemeen gebruikt tijdens testwerkzaamheden
	// en bij het opvragen van gegevens via de DAO's
	public Klant(long id,
			String voornaam,
			String achternaam,
			String tussenvoegsel,
			String email,
			String datumAanmaak,
			String datumGewijzigd,
			String klantActief,
			Map<Adres, AdresType> adresGegevens,
			Set<Bestelling> bestellingGegevens) {

		if (adresGegevens != null){
			this.adresGegevens = adresGegevens;
		}

		this.id = id;
		this.voornaam = voornaam;
		this.achternaam = achternaam;
		this.tussenvoegsel = tussenvoegsel;
		this.email = email;
		this.datumAanmaak = datumAanmaak;
		this.datumGewijzigd = datumGewijzigd;
		this.klantActief = klantActief;
		this.bestellingen = bestellingGegevens;
	}

	// Getters & setters
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
		this.datumGewijzigd = new Date(System.currentTimeMillis()).toString();

	}
	public String getVoornaam() {
		return voornaam;
	}
	public void setVoornaam(String voornaam) {
		this.voornaam = voornaam;
	}
	public String getAchternaam() {
		return achternaam;
	}
	public void setAchternaam(String achternaam) {
		this.achternaam = achternaam;
	}
	public String getTussenvoegsel() {
		return tussenvoegsel;
	}
	public void setTussenvoegsel(String tussenvoegsel) {
		this.tussenvoegsel = tussenvoegsel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Map<Adres, AdresType> getAdresGegevens() {
		return adresGegevens;
	}
	public String getDatumAanmaak() {
		return datumAanmaak;
	}
	public void setDatumAanmaak(String datumAanmaak) {
		this.datumAanmaak = datumAanmaak;
	}
	public String getDatumGewijzigd() {
		return datumGewijzigd;
	}
	public void setDatumGewijzigd(String datumGewijzigd) {
		this.datumGewijzigd = datumGewijzigd;
	}
	public String getKlantActief() {
		return klantActief;
	}
	public void setKlantActief(String klantActief) {
		this.klantActief = klantActief;
	}
	public void setAdresGegevens(Map<Adres, AdresType> adresGegevens) {
		this.adresGegevens = adresGegevens;
	} 

	public Set<Bestelling> getBestellingen() {
		return bestellingen;
	}
	public void setBestellingen(Set<Bestelling> bestellingGegevens) {
		this.bestellingen = bestellingGegevens;
	}

	// Overrided methoden van Object etc.

	@Override
	public String toString() {
		return "[" + id + ", " +
				voornaam + ", " +
				achternaam + ", " +
				tussenvoegsel + ", " +
				email + ", " +
				"Adresgegevens aanwezig:" +
				(adresGegevens != null ? " ja" : " nee") +
				"]";
	}

	/**
	 * Een klant-object wordt geacht gelijk te zijn als het id hetzelfde is.
	 *
	 * @param obj Klant-Object om mee te vergelijken.
	 * @return Een waarde true of false als de klant gelijk is.
	 */
	@Override
	public boolean equals(Object obj) {
		if (id == ((Klant)obj).getId())
			return true;
		return false;
	}
}
