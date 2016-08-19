package com.adm.domain;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.io.Serializable;
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
@Component
@Scope(
		value= WebApplicationContext.SCOPE_GLOBAL_SESSION,
		proxyMode = ScopedProxyMode.INTERFACES)
public class Klant implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "klantId")
	@SequenceGenerator(name = "klantId", sequenceName = "zKlant_sequence", allocationSize = 1)
	private Long id;

	@Column(nullable = false)
	private String voornaam;

	@Column(nullable = false)
	private String achternaam;

	@Column(nullable = false)
	private String tussenvoegsel;

	@Column(nullable = false)
	private String email;

    @Column(nullable = false)
    private String password;

	@Column(nullable = false)
	private String datumAanmaak;

	@Column(nullable = false)
	private String datumGewijzigd;

	@Column(nullable = false)
	private String klantActief;

	@ManyToMany
	@JoinTable(name = "klantAdresAdresType",
		joinColumns = @JoinColumn(name = "klantId", nullable = false),
		inverseJoinColumns = @JoinColumn(name = "adresTypeId", nullable = false))

	@MapKeyJoinColumn(name = "adresId", nullable = false)
	protected Map<Adres, AdresType> adresGegevens = new HashMap<Adres, AdresType>();

	@OneToMany(mappedBy = "klant")
	@Column(nullable = false)
	protected Set<Bestelling> bestellingen = new HashSet<Bestelling>();

	//  Default public no-arg constructor
	public Klant() {
	}

	// Standaard public constructor met basis parameters
	public Klant(Long id,
			String voornaam,
			String achternaam,
			String tussenvoegsel,
			String email, String password,
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
        this.password = password;
	}

	// Constructor voor alle variabelen, wordt over het algemeen gebruikt tijdens testwerkzaamheden
	// en bij het opvragen van gegevens via de DAO's
	public Klant(Long id,
			String voornaam,
			String achternaam,
			String tussenvoegsel,
			String email,
                 String password,
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
        this.password = password;
		this.datumAanmaak = datumAanmaak;
		this.datumGewijzigd = datumGewijzigd;
		this.klantActief = klantActief;
		this.bestellingen = bestellingGegevens;
	}

	// Getters & setters
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
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
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
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
