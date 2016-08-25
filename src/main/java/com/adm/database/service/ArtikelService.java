package com.adm.database.service;

import com.adm.database.daos.ArtikelDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@Profile({"persistence", "production" })
public class ArtikelService {
	
    private ArtikelDAO artikelDAO;

    @Autowired
    ArtikelService(ArtikelDAO artikelDAO) {
        this.artikelDAO = artikelDAO;
    }

    public ArtikelDAO getArtikelDAO() {
        return artikelDAO;
    }
}
