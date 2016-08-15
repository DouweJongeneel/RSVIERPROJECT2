package com.adm.web.forms;

import com.adm.domain.Klant;
import org.hibernate.validator.constraints.Email;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by Milan_Verheij on 15-08-16.
 *
 * Klant Form class
 *
 */
public class KlantRegisterForm {

    // Input fields
    @NotNull
    @Size(min=3, max=16, message="{firstName.size}")
    private String voornaam;

    @NotNull
    @Size(min=3, max=16, message="{lastName.size}")
    private String achternaam;

    @Size(max=10, message="{prefix.size}")
    private String tussenvoegsel;

    @NotNull
    @Email
    private String email;

    private MultipartFile profilePicture;

    // Setters & Getters
    public String getVoornaam() {
        return voornaam;
    }
    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }
    public String getAchternaam() {
        return achternaam;
    }
    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }
    public String getTussenvoegsel() {
        return tussenvoegsel;
    }
    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public MultipartFile getProfilePicture() {
        return profilePicture;
    }
    public void setProfilePicture(MultipartFile profilePicture) {
        this.profilePicture = profilePicture;
    }

    // Extra methods
    public Klant toKlant() {
        return new Klant(null, voornaam, achternaam, tussenvoegsel, email,
                        (new Date()).toString(), "", "1", null, null);
    }
}
