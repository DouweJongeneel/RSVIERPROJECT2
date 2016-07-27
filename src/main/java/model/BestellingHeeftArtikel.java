package model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "bestellingHeeftArtikel")
public class BestellingHeeftArtikel {

    @Column(name = "bestellingId_best")
    long bestellingId_best;

    @Column(name = "artikelIdArt")
    long artikelIdArt;

    @Column(name = "prijsIdPrijs")
    long prijsIdPrijs;

    @Column(name = "aantal")
    long aantal;

    public long getBestellingId_best() {
        return bestellingId_best;
    }
    public void setBestellingId_best(long bestellingId_best) {
        this.bestellingId_best = bestellingId_best;
    }
    public long getArtikelIdArt() {
        return artikelIdArt;
    }
    public void setArtikelIdArt(long artikelIdArt) {
        this.artikelIdArt = artikelIdArt;
    }
    public long getPrijsIdPrijs() {
        return prijsIdPrijs;
    }
    public void setPrijsIdPrijs(long prijsIdPrijs) {
        this.prijsIdPrijs = prijsIdPrijs;
    }
    public long getAantal() {
        return aantal;
    }
    public void setAantal(long aantal) {
        this.aantal = aantal;
    }
}