package com.adm.database.daos;

import com.adm.domain.AdresType;

public class AdresTypeDAO extends GenericDAOImpl<AdresType, Long>{
	/*
	 * In principe is onderstaande constructor voldoende om de generiekeDAO te implementeren. 
	 * Methodes kunnen toegevoegd of overschreven worden voor verdere implementatie.
	 */
	
	// Constructor geeft automatisch de entityClass (AdresType.class) door aan GenericDAOImpl
	protected AdresTypeDAO() {
		super(AdresType.class);
	}

}
