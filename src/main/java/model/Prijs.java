package model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity(name = "prijs")
public class Prijs {
	
	@Id
	@GeneratedValue
	private long prijsId;
	
	@OneToMany(mappedBy = "artikel",
			fetch = FetchType.LAZY	
	)
	
	@Column(name = "prijs")
	private BigDecimal prijs;
	
	// @Column(name = "datumAanmaak")
	// Todo datum;
	
	@JoinColumn
	private Artikel artikel;

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
