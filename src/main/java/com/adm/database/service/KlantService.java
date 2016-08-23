package com.adm.database.service;

import com.adm.database.daos.KlantDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Milan_Verheij on 19-08-16.
 *
 * Klant service methode voor
 */

@Service
@Component
@Transactional
@Profile({"persistence", "production" })
public class KlantService {

    private KlantDAO klantDAO;

    @Autowired
    public KlantService(KlantDAO klantDAO) {
        this.klantDAO = klantDAO;
    }

    public KlantDAO getDAO() {
        return klantDAO;
    }
}
