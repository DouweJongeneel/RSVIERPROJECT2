package database.daos;

import model.Factuur;

public class FactuurDAO extends GenericDAOImpl<Factuur, Long>	{
	/*
	 * In principe is onderstaande constructor voldoende om de generiekeDAO te implementeren. 
	 * Methodes kunnen toegevoegd of overschreven worden voor verdere implementatie.
	 */
	
	// Constructor geeft automatisch de entityClass (Factuur.class) door aan GenericDAOImpl
	protected FactuurDAO() {
		super(Factuur.class);
	} 
}
