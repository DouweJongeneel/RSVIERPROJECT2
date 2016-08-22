package com.adm.database.interfaces;

import javax.persistence.LockModeType;
import java.io.Serializable;
import java.util.List;

/**
 * An interface shared by all business data access objects.
 * <p>
 * All CRUD (create, read, update, delete) basic data access operations are
 * isolated in this interface and shared across all DAO implementations.
 * The current design is for a state-management oriented persistence layer
 * (for example, there is no UPDATE statement function) which provides
 * automatic transactional dirty checking of business objects in persistent
 * state.
 */

public interface GenericDAO<E, ID extends Serializable> extends Serializable {
	
	/* Deze methode moet aangeroepen worden voor elke DAO voordat data wordt opgeslagen, het zorgt
	 * ervoor dat hibernate de persistence context flushed voordat data gelezen wordt.
	 */
	void joinTransaction();

    E findById(ID id);

    E findById(ID id, LockModeType lockModeType);

    E findReferenceById(ID id);

    List<E> findAll();

    Long getCount();

    E makePersistent(E entity);

    void makeTransient(E entity);

    void checkVersion(E entity, boolean forceUpdate);

}
