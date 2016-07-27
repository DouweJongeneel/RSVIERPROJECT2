package database.factories;

import database.interfaces.AdresDAO;
import database.interfaces.ArtikelDAO;
import database.interfaces.BestellingDAO;
import database.interfaces.KlantDAO;
import database.daos.mysql.AdresDAOMySQL;
import database.daos.mysql.ArtikelDAOMySQL;
import database.daos.mysql.BestellingDAOMySQL;
import database.daos.mysql.KlantDAOMySQL;
import exceptions.GeneriekeFoutmelding;

/**
 * @author Milan_Verheij, Douwe Jongeneel
 * <p>
 * Deze concrete factory  maakt DAO's aan voor de database (MySQL)
 * en geeft deze terug aan de gebruiker.
 *
 */

public class DAOFactory{

	/**
	 * Methode om een KlantDAO te maken.
     */
	public KlantDAO getKlantDAO() throws GeneriekeFoutmelding {
		return new KlantDAOMySQL();
	}

	/**
	 * Methode om de een AdresDAO te maken.

     */
	public AdresDAO getAdresDAO() throws GeneriekeFoutmelding {
		return new AdresDAOMySQL();
	}

    /**
     * Methode om de een BestellingDAO te maken.
     */
	public BestellingDAO getBestellingDAO() throws GeneriekeFoutmelding {
		return new BestellingDAOMySQL();
	}

    /**
     * Methode om de een ArtikelDAO te maken.
     */
	public ArtikelDAO getArtikelDAO() throws GeneriekeFoutmelding {
		return new ArtikelDAOMySQL();
	}

}
