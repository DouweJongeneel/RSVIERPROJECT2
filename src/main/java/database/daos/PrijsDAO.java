package database.daos;

import model.Prijs;

public class PrijsDAO extends GenericDAOImpl<Prijs, Long>	 {
	/*
	 * In principe is onderstaande constructor voldoende om de generiekeDAO te implementeren. 
	 * Methodes kunnen toegevoegd of overschreven worden voor verdere implementatie.
	 */
	
	// Constructor geeft automatisch de entityClass (Artikel.class) door aan GenericDAOImpl
	protected PrijsDAO() {
		super(Prijs.class);
	}

}
