package database.test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import database.service.ArtikelService;
import model.Artikel;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ArtikelServiceTest {
	// Create Service
	private static ArtikelService artikelService = new ArtikelService();
	
	// Create entityManager
	private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("rsvierproject2PU");
	private static EntityManager entityManager = entityManagerFactory.createEntityManager();
	
	private static Artikel nieuwArtikel = new Artikel("Zeepaardje", new BigDecimal(750), 14, true);
	private Artikel updateArtikel = new Artikel("Zeepaardje", new BigDecimal(800), 21, true);
	
	private static Long id;
	
	@BeforeClass
	public static void setup() throws Exception {
		// Begin Transaction before each test
		entityManager.getTransaction().begin();
		artikelService.persist(nieuwArtikel);
		id = nieuwArtikel.getId();
	}
	
	@AfterClass
	public static void tearDown() throws Exception {
		// Commit and close transaction after each test
		entityManager.getTransaction().commit();
		entityManager.close();
	}

	// @Test persist gebeurt al tijdens @BeforeClass

	@Test
	public void test2FindById() {
		Artikel geretouneerdArtikel = artikelService.findById(id);
		
		assertThat(geretouneerdArtikel.getArtikelNaam(), containsString(nieuwArtikel.getArtikelNaam()));
	}

	@Test
	public void test3Update() {
		artikelService.update(updateArtikel);
		Artikel geretouneerdArtikel = artikelService.findById(id);
		
		assertThat(geretouneerdArtikel.getArtikelNaam(),is(equalTo(nieuwArtikel.getArtikelNaam())));
		assertThat(geretouneerdArtikel.getArtikelPrijs(),is(not(equalTo(nieuwArtikel.getArtikelPrijs()))));
	}

	@Test
	public void test4Delete() {
		artikelService.delete(nieuwArtikel);
		Artikel geretouneerdArtikel = haalArtikelUitDatabase(id);
	}

	@Test
	public void test5FindAll() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void test6DeleteAll() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void test7ArtikelDAO() {
		fail("Not yet implemented"); // TODO
	}
	
	//Utility
	public Artikel haalArtikelUitDatabase(Long id2) {
		return artikelService.findById(id2);
	}
	
}
