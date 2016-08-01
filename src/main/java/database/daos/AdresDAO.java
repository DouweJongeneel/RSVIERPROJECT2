package database.daos;

import model.Adres;

public class AdresDAO extends GenericDAOImpl<Adres, Long>{
	/*
	 * In principe is onderstaande constructor voldoende om de generiekeDAO te implementeren. 
	 * Methodes kunnen toegevoegd of overschreven worden voor verdere implementatie.
	 */
	
	// Constructor geeft automatisch de entityClass (Adres.class) door aan GenericDAOImpl
	protected AdresDAO() {
		super(Adres.class);
		// TODO Auto-generated constructor stub
	}

}
