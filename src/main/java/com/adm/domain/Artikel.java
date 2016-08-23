package com.adm.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "artikelNaam"))
public class Artikel implements Comparable<Artikel>, Serializable{

	@Id
	@SequenceGenerator(name = "artikelId", sequenceName = "zArtikel_sequence", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "artikelId")
	private Long id; // hibernate returned nooit null wanneer het een primitiev type is

	@Column(nullable = false)
	private String artikelNaam;

	@Transient
	private BigDecimal artikelPrijs;

	@OneToMany(mappedBy = "artikel", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
//	@JoinTable(name = "prijsArtikel",
//	joinColumns = @JoinColumn(name = "artikelId", nullable = false),
//	inverseJoinColumns = @JoinColumn(name = "prijsId", nullable = false))
	private Set<Prijs> prijzen = new LinkedHashSet<>();
	
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
	
	@Transient
	private String plaatje;

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
	public Long getId() {
		return id;
	}

	public String getArtikelNaam() {
		return artikelNaam;
	}

	public BigDecimal getArtikelPrijs() {
		return artikelPrijs;
	}

	public Set<Prijs> getPrijzen() {
		return prijzen;
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

	public String getPlaatje() {
		return plaatje;
	}

	public void setPlaatje(String plaatje) {
		this.plaatje = plaatje;
	}

	public void setBestelArtikel(Set<BestelArtikel> bestelArtikel) {
		this.bestelArtikel = bestelArtikel;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setArtikelNaam(String artikelNaam) {
		this.artikelNaam = artikelNaam;
	}

	public void setArtikelPrijs(BigDecimal artikelPrijs) {
		this.artikelPrijs = artikelPrijs;
	}

	public void setPrijzen(Set<Prijs> prijzen) {
		this.prijzen = prijzen;
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
		return "ARTIKEL: " + artikelNaam + "\t $" + artikelPrijs.toPlainString()
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