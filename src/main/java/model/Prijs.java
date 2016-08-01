package model;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity(name = "prijs")
public class Prijs {
	
	@Id
	@SequenceGenerator(name = "prijsId", sequenceName = "zprijs_sequence")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "prijsId")
	protected Long id;

	@ManyToOne
	@JoinTable(name = "prijsArtikel",
	joinColumns = @JoinColumn(name = "prijsId", nullable = false),
	inverseJoinColumns = @JoinColumn(name = "artikelId", nullable = false))
	Artikel artikel;
	
	@Column(nullable = false)
	BigDecimal prijs;
	
	@Column(nullable = false)
	Date datumAanmaak = new Date(System.currentTimeMillis());

	@OneToMany(mappedBy = "prijs")
	@Column(nullable = false)
	protected Set<BestelArtikel> bestelArtikel = new HashSet<BestelArtikel>();
	
	public Prijs(){}

	public Long getId() {
		return id;
	}

	public Set<BestelArtikel> getBestelArtikel() {
		return bestelArtikel;
	}
	public Date getDatumAanmaak() {
		return datumAanmaak;
	}

	public void setBestelArtikel(Set<BestelArtikel> bestelArtikel) {
		this.bestelArtikel = bestelArtikel;
	}
	public void setDatumAanmaak(Date datumAanmaak) {
		this.datumAanmaak = datumAanmaak;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
