package com.adm.web.forms;

import com.adm.domain.Adres;

import javax.validation.constraints.NotNull;
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
    private String straatnaam;

    @NotNull
    private String postcode;

    private String toevoeging;

    @NotNull
    private String huisnummer;

    @NotNull
    private String woonplaats;

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

    // Extra methods
    public Adres toAdres() {
        return new Adres(straatnaam, postcode, toevoeging, Integer.parseInt(huisnummer), woonplaats, new Date().toString(), "", "1");
    }

}