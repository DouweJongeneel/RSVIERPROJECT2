package com.adm.database.daos;

import com.adm.domain.Klant;

public class KlantDAO extends GenericDAOImpl<Klant, Long>{
	/*
	 * In principe is onderstaande constructor voldoende om de generiekeDAO te implementeren. 
	 * Methodes kunnen toegevoegd of overschreven worden voor verdere implementatie.
	 */
	
	// Constructor geeft automatisch de entityClass (Klant.class) door aan GenericDAOImpl
	protected KlantDAO() {
		super(Klant.class);
	}
	

}
