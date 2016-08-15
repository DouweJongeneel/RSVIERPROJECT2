package com.adm.database.daos;

import com.adm.domain.Bestelling;

public class BestellingDAO extends GenericDAOImpl<Bestelling, Long>{
	/*
	 * In principe is onderstaande constructor voldoende om de generiekeDAO te implementeren. 
	 * Methodes kunnen toegevoegd of overschreven worden voor verdere implementatie.
	 */
	
	// Constructor geeft automatisch de entityClass (Bestelling.class) door aan GenericDAOImpl
	protected BestellingDAO() {
		super(Bestelling.class);
	}

}
