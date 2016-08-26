package com.adm.database.daos;

import com.adm.domain.Artikel;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@Component
@Profile({"persistence", "production" })
public class ArtikelDAO extends GenericDAOImpl<Artikel, Long>{

	/*
	 * In principe is onderstaande constructor voldoende om de generiekeDAO te implementeren. 
	 * Methodes kunnen toegevoegd of overschreven worden voor verdere implementatie.
	 */
	
	// Constructor geeft automatisch de entityClass (Artikel.class) door aan GenericDAOImpl
	protected ArtikelDAO() {
		super(Artikel.class);
	}
	
	
}
