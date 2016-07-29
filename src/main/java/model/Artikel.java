package model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
public class Artikel implements Comparable<Artikel>{

	@Id
	@SequenceGenerator(name = "artikelId", sequenceName = "zArtikel_sequence")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "artikelId")
	private int id;

	@Column(nullable = false)
	private String artikelNaam;

	@Transient
	private BigDecimal artikelPrijs;

	@OneToMany
	@JoinTable(name = "prijsArtikel",
	joinColumns = @JoinColumn(name = "artikelId", nullable = false),
	inverseJoinColumns = @JoinColumn(name = "prijsId", nullable = false))
	private Set<Prijs> prijs;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "datumAanmaak", updatable = false, nullable = false)
	protected Date datumAanmaak = null;

	@Column
	private int verwachteLevertijd;

	@Column(nullable = false)
	private boolean inAssortiment;

	@OneToMany(mappedBy = "artikel")
	@Column(nullable = false)
	protected Set<BestelArtikel> bestelArtikel;

	//Constructors
	public Artikel() {
		this.datumAanmaak = new Date(System.currentTimeMillis());
	}

	public Artikel(String artikelNaam, BigDecimal artikelPrijs, int verwachteLevertijd, boolean inAssortiment) {
		this.artikelNaam = artikelNaam;
		this.artikelPrijs = artikelPrijs;
		this.datumAanmaak = new Date(System.currentTimeMillis());
		this.verwachteLevertijd = verwachteLevertijd;
		this.inAssortiment = inAssortiment;
	}

	//Getters and Setters
	public int getId() {
		return id;
	}

	public String getArtikelNaam() {
		return artikelNaam;
	}

	public BigDecimal getArtikelPrijs() {
		return artikelPrijs;
	}

	public Set<Prijs> getPrijs() {
		return prijs;
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

	public Set<BestelArtikel> getBestelArtikel() {
		return bestelArtikel;
	}

	public void setBestelArtikel(Set<BestelArtikel> bestelArtikel) {
		this.bestelArtikel = bestelArtikel;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setArtikelNaam(String artikelNaam) {
		this.artikelNaam = artikelNaam;
	}

	public void setArtikelPrijs(BigDecimal artikelPrijs) {
		this.artikelPrijs = artikelPrijs;
	}

	public void setPrijs(Set<Prijs> prijs) {
		this.prijs = prijs;
	}

	public void setDatumAanmaak(Date datumAanmaak) {
		this.datumAanmaak = datumAanmaak;
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
		return id == ((Artikel)o).getId();
	}

	@Override
	public String toString(){
		return "ARTIKEL: " + id + "\t " + artikelNaam + "\t $" + artikelPrijs.toPlainString()
		+ "\t " + datumAanmaak + "\t " + verwachteLevertijd + "\t "
		+ inAssortiment;
	}

	public int compareTo(Artikel o) {
		if (this.id == o.getId())
			return 0;
		else if (this.id > o.getId())
			return 1;
		else
			return -1;
	}
}