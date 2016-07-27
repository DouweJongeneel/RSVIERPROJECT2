package model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity(name = "prijs")
public class Prijs {
	
	@Id
	@GeneratedValue
	protected long prijsId;
	
	/*
	 * @ManyToOne --> eigenaar in de relatie
	 * optional = false betekent dat een prijs niet afzonderlijk van een artikel kan bestaan!
	 * zelfde kun je bereiken door @JoinColumn(nullable = false)
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = false) // --> defaults naar EAGER
	@JoinColumn(name = "artikelId")
	protected Artikel artikel;
	
	@Column(name = "prijs")
	protected BigDecimal prijs;
	
	// @Column(name = "datumAanmaak")
	// Todo datum;
	
	public Prijs(){
	}
	public Prijs(BigDecimal prijs) {
		this.prijs = prijs;
	}
	public Prijs(Artikel artikel, BigDecimal prijs){
		this.artikel = artikel;
		this.prijs = prijs;
	}

	public long getPrijsId() {
		return prijsId;
	}
	public BigDecimal getPrijs() {
		return prijs;
	}
	public Artikel getArtikel() {
		return artikel;
	}

	public void setPrijsId(long prijsId) {
		this.prijsId = prijsId;
	}
	public void setPrijs(BigDecimal prijs) {
		this.prijs = prijs;
	}
	public void setArtikel(Artikel artikel) {
		this.artikel = artikel;
	}
	
	

}
