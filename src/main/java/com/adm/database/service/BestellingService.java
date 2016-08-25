package com.adm.database.service;

import com.adm.database.daos.BestellingDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@Profile({"persistence", "production" })
public class BestellingService {
	
    private BestellingDAO bestellingDAO;

    @Autowired
    BestellingService(BestellingDAO bestellingDAO) {
        this.bestellingDAO = bestellingDAO;
    }

    public BestellingDAO getBestellingDAO() {
        return bestellingDAO;
    }
}
