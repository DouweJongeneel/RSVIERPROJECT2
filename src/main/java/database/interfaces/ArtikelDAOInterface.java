package database.interfaces;

import java.io.Serializable;
import java.util.Set;

public interface ArtikelDAOInterface<E, Id extends Serializable> {
	
	public void persist(E entity);
	
	public void update(E entity);
	
	public E findById(Id id);
	
	public void delete(E entity);
	
	public Set<E> findAll();
	
	public void deleteALl();
}
