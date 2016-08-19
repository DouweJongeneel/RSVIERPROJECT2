package com.adm.database.service;

import com.adm.database.daos.AdresDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Milan_Verheij on 19-08-16.
 *
 * Adres service
 */

@Component
@Transactional
@Profile({"persistence", "production" })
public class AdresService {
    private AdresDAO adresDAO;

    @Autowired
    AdresService(AdresDAO adresDAO) {
        this.adresDAO = adresDAO;
    }

    public AdresDAO getAdresDAO() {
        return adresDAO;
    }
}
