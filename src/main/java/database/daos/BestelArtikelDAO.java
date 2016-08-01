package database.daos;

import model.BestelArtikel;

public class BestelArtikelDAO extends GenericDAOImpl<BestelArtikel, Long>{
	/*
	 * In principe is onderstaande constructor voldoende om de generiekeDAO te implementeren. 
	 * Methodes kunnen toegevoegd of overschreven worden voor verdere implementatie.
	 */
	
	// Constructor geeft automatisch de entityClass (BestelArtikel.class) door aan GenericDAOImpl
	protected BestelArtikelDAO() {
		super(BestelArtikel.class);
	}

}
