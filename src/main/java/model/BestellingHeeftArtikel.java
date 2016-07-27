package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "bestellingHeeftArtikel")
@org.hibernate.annotations.Immutable
public class BestellingHeeftArtikel {
	
	/*
	 * Encapsulate de composite key(bestellingId, artikelId)
	 */
	@Embeddable
	public static class Id implements Serializable {
		
		@Column(name = "bestellingIdBest")
		protected Long bestellingId;
		
		@Column(name = "artikelIdArt")
		protected Long artikelId;
		
		@Column(name = "prijsIdPrijs")
		protected Long prijsId;
		
		public Id() {
		}
		public Id(Long bestellingId, Long artikelId, Long prijsId) {
			this.bestellingId = bestellingId;
			this.artikelId = artikelId;
			this.prijsId = prijsId;
		}
		
		public boolean equals(Object obj) {
			if (obj != null && obj instanceof Id) {
				Id that = (Id) obj;
				return this.bestellingId.equals(that.bestellingId) 
						&& this.artikelId.equals(that.artikelId)
						&& this.prijsId.equals(that.prijsId);
			}
			return false;
		}
		public int hashCode() {
			return bestellingId.hashCode() + artikelId.hashCode() + prijsId.hashCode();
		}
	}
		
	@EmbeddedId // --> mapped id property en composite key kolommen
	protected Id id = new Id();
	
	@Column(name = "aantal")
	protected int aantal;
	
	@ManyToOne
	@JoinColumn(name = "bestellingIdBest", insertable = false, updatable = false)
	protected Bestelling bestelling;
	
	@ManyToOne
	@JoinColumn(name = "artikelIdArt", insertable = false, updatable = false)
	protected Artikel artikel;
	
	@ManyToOne
	@JoinColumn(name = "prijsIdPrijs", insertable = false, updatable = false)
	protected Prijs prijs;
	
	public BestellingHeeftArtikel(Bestelling bestelling, Artikel artikel, Prijs prijs, int aantal) {
		this.bestelling = bestelling;
		this.artikel = artikel;
		this.prijs = prijs;
		this.aantal = aantal;
		
		this.id.bestellingId = bestelling.getBestellingId();
		this.id.artikelId = artikel.getArtikelId();
		this.id.prijsId = prijs.getPrijsId();
		
		// TODO - guarantee referential integrety if made bidirectional blz 194
		
	}
}
	
    