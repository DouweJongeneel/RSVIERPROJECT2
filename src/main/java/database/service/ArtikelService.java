package database.service;

import java.util.Set;

import database.daos.ArtikelDAO;
import model.Artikel;

public class ArtikelService {
	
	private static ArtikelDAO artikelDAO;
	
	public ArtikelService() {
		if (artikelDAO == null) {
			artikelDAO = new ArtikelDAO();
		}
	}
	
	public void persist(Artikel entity) {
		artikelDAO.persist(entity);
	}
	
	public Artikel findById(String id){
		return artikelDAO.findById(id);
	}
	
	public void update(Artikel entity) {
		artikelDAO.update(entity);
	}
	
	public void delete(Artikel entity) {
		artikelDAO.delete(entity);
	}
	
	public Set<Artikel> findAll() {
		return artikelDAO.findAll();
	}
	
	public void deleteAll() {
		artikelDAO.deleteALl();
	}
	
	public ArtikelDAO artikelDAO() {
		return artikelDAO;
	}
}
