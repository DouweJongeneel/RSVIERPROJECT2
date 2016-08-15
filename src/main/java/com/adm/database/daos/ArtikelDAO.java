package com.adm.database.daos;

import com.adm.domain.Artikel;

public class ArtikelDAO extends GenericDAOImpl<Artikel, Integer>{

	/*
	 * In principe is onderstaande constructor voldoende om de generiekeDAO te implementeren. 
	 * Methodes kunnen toegevoegd of overschreven worden voor verdere implementatie.
	 */
	
	// Constructor geeft automatisch de entityClass (Artikel.class) door aan GenericDAOImpl
	protected ArtikelDAO() {
		super(Artikel.class);
	}
	
	
}
