package com.adm.web.forms;

import com.adm.domain.Adres;
import com.adm.domain.AdresType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by Milan_Verheij on 15-08-16.
 *
 * Klant Form class
 *
 */
public class AdresRegisterForm {

    // Fields
    @NotNull
    @Size(min=6, max=30, message="{address.street.size}")
    private String straatnaam;

    @NotNull
    @Size(min=3, max=16, message="{address.zipCode.size}")
    private String postcode;

    @Size(max=6, message="{address.prefix.size")
    private String toevoeging;

    @NotNull
    @Size(min=1, max=10, message="{address.number.size}")
    private String huisnummer;

    @NotNull
    @Size(min=3, max=50, message="{address.city.size}")
    private String woonplaats;

    @NotNull
    private String adresType;

    private String adresActief;


    // Constructors
    public AdresRegisterForm() {}

    public AdresRegisterForm(String straatnaam,
                             String postcode,
                             String toevoeging,
                             String huisnummer,
                             String woonplaats,
                             String adresType,
                             String adresActief) {
        this.straatnaam = straatnaam;
        this.postcode = postcode;
        this.toevoeging = toevoeging;
        this.huisnummer = huisnummer;
        this.woonplaats = woonplaats;
        this.adresType = adresType;
        this.adresActief = adresActief;
    }

    // Getters and setters
    public String getStraatnaam() {
        return straatnaam;
    }
    public void setStraatnaam(String straatnaam) {
        this.straatnaam = straatnaam;
    }
    public String getPostcode() {
        return postcode;
    }
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
    public String getToevoeging() {
        return toevoeging;
    }
    public void setToevoeging(String toevoeging) {
        this.toevoeging = toevoeging;
    }
    public String getHuisnummer() {
        return huisnummer;
    }
    public void setHuisnummer(String huisnummer) {
        this.huisnummer = huisnummer;
    }
    public String getWoonplaats() {
        return woonplaats;
    }
    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
    }
    public String getAdresType() {
        return adresType;
    }
    public void setAdresType(String adresType) {
        this.adresType = adresType;
    }
    public String getAdresActief() {
        return adresActief;
    }
    public void setAdresActief(String adresActief) {
        this.adresActief = adresActief;
    }

    // Extra methods
    public Adres toAdres() {

        // Set adres-type to correct index
        AdresType adresType = new AdresType();
        adresType.setAdres_type(Integer.parseInt(this.adresType));

        return new Adres(straatnaam, postcode, toevoeging, Integer.parseInt(huisnummer),
                woonplaats, adresType, new Date().toString(), "", "1");
    }

}