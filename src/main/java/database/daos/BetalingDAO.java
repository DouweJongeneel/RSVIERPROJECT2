package database.daos;

import model.Betaling;

public class BetalingDAO extends GenericDAOImpl<Betaling, Long>{
	/*
	 * In principe is onderstaande constructor voldoende om de generiekeDAO te implementeren. 
	 * Methodes kunnen toegevoegd of overschreven worden voor verdere implementatie.
	 */
	
	// Constructor geeft automatisch de entityClass (Betaling.class) door aan GenericDAOImpl
	protected BetalingDAO() {
		super(Betaling.class);
	}
	

}
