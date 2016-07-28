package model;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity(name = "prijs")
public class Prijs {
	
	@Id
	@SequenceGenerator(name = "prijsId", sequenceName = "zprijs_sequence")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "prijsId")
	protected long id;
	
	/*
	 * @ManyToOne --> eigenaar in de relatie
	 * optional = false betekent dat een prijs niet afzonderlijk van een artikel kan bestaan!
	 * zelfde kun je bereiken door @JoinColumn(nullable = false)
	 */

	
	@OneToMany
	protected Set<BestelArtikel> bestelArtikel;
	
	@Column
	protected BigDecimal prijs;
	
	@Column
	Date datumAanmaak = new Date(System.currentTimeMillis());

	@Column
	private Artikel artikel;
	
	public Prijs(){
	}
	public Prijs(BigDecimal prijs) {
		this.prijs = prijs;
	}

	public long getId() {
		return id;
	}
	public BigDecimal getPrijs() {
		return prijs;
	}

	public Set<BestelArtikel> getBestelArtikel() {
		return bestelArtikel;
	}
	public Date getDatumAanmaak() {
		return datumAanmaak;
	}
	public Artikel getArtikel() {
		return artikel;
	}
	public void setArtikel(Artikel artikel) {
		this.artikel = artikel;
	}
	public void setBestelArtikel(Set<BestelArtikel> bestelArtikel) {
		this.bestelArtikel = bestelArtikel;
	}
	public void setDatumAanmaak(Date datumAanmaak) {
		this.datumAanmaak = datumAanmaak;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setPrijs(BigDecimal prijs) {
		this.prijs = prijs;
	}
}
