package com.adm.database.daos;

import com.adm.domain.Account;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@Profile({"persistence", "production" })
public class AccountDAO extends GenericDAOImpl<Account, Long> {
	/*
	 * In principe is onderstaande constructor voldoende om de generiekeDAO te implementeren. 
	 * Methodes kunnen toegevoegd of overschreven worden voor verdere implementatie.
	 */
	
	// Constructor geeft automatisch de entityClass (Account.class) door aan GenericDAOImpl
	protected AccountDAO() {
		super(Account.class);
		// TODO Auto-generated constructor stub
	}

}
