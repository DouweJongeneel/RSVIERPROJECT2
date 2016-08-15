package com.adm.database.service;

import java.util.Set;

import com.adm.database.daos.ArtikelDAONietGeneriek;
import com.adm.domain.Artikel;

public class ArtikelService {
	
	private static ArtikelDAONietGeneriek artikelDAO;
	
	public ArtikelService() {
		if (artikelDAO == null) {
			artikelDAO = new ArtikelDAONietGeneriek();
		}
	}
	
	public void persist(Artikel entity) {
		artikelDAO.persist(entity);
	}
	
	public Artikel findById(Long id){
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
	
	public ArtikelDAONietGeneriek artikelDAO() {
		return artikelDAO;
	}
}
