package com.adm.database.daos;

import com.adm.domain.Betaalwijze;

public class BetaalwijzeDAO extends GenericDAOImpl<Betaalwijze, Long>{
	/*
	 * In principe is onderstaande constructor voldoende om de generiekeDAO te implementeren. 
	 * Methodes kunnen toegevoegd of overschreven worden voor verdere implementatie.
	 */
	
	// Constructor geeft automatisch de entityClass (Betaalwijze.class) door aan GenericDAOImpl
	protected BetaalwijzeDAO() {
		super(Betaalwijze.class);
	}

}
