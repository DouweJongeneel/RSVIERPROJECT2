package model;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created by Milan_Verheij on 08-07-16.
 *
 */
@Entity(name = "klantHeeftAdres")
public class KlantHeeftAdres {

    @Column(name = "adresIdAdres")
    private long adresIdAdres;

    @Column(name = "klantIdKlant")
    private long klantIdKlant;

    public long getAdresIdAdres() {
        return adresIdAdres;
    }

    public void setAdresIdAdres(long adresIdAdres) {
        this.adresIdAdres = adresIdAdres;
    }

    public long getKlantIdKlant() {
        return klantIdKlant;
    }

    public void setKlantIdKlant(long klantIdKlant) {
        this.klantIdKlant = klantIdKlant;
    }
}
