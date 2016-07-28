package model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class Account {

	@Id
	@SequenceGenerator(name = "accountId", sequenceName = "zaccount_sequence")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "accountId")
	private long id;
	
	@Column
	private String naam;
	
	@ManyToOne
	Klant klant;
	
	@Column
	Date creatieDatum;

	public long getId() {
		return id;
	}

	public String getNaam() {
		return naam;
	}

	public Klant getKlant() {
		return klant;
	}

	public Date getCreatieDatum() {
		return creatieDatum;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

	public void setKlant(Klant klant) {
		this.klant = klant;
	}

	public void setCreatieDatum(Date creatieDatum) {
		this.creatieDatum = creatieDatum;
	}
	
	
	
	
}
