package database.test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
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
	private ArtikelService artikelService = new ArtikelService();
	
	// Create entityManager
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("rsvierproject2PU");
	private static EntityManager entityManager = emf.createEntityManager();
	
	private Artikel nieuwArtikel = new Artikel("Zeepaardje", new BigDecimal(750), 14, true);
	private Artikel updateArtikel = new Artikel("Zeepaardje", new BigDecimal(800), 21, true);
	
	@BeforeClass
	public static void setup() throws Exception {
		// Begin Transaction before each test
		entityManager.getTransaction().begin();
	}

	@AfterClass
	public static void tearDown() throws Exception {
		// Comit and close transaction after each test
		entityManager.getTransaction().commit();
		entityManager.close();
	}

	@Test
	public void test1Persist() {
		artikelService.persist(nieuwArtikel);
	}

	@Test
	public void test2FindById() {
		Artikel geretouneerdArtikel = artikelService.findById(nieuwArtikel.getId());
		
		System.out.println(geretouneerdArtikel.toString() + "\n" 
				+ nieuwArtikel.toString());
		assertThat(geretouneerdArtikel.getArtikelNaam(), containsString(nieuwArtikel.getArtikelNaam()));
	}

	@Test
	public void test3Update() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void test4Delete() {
		fail("Not yet implemented"); // TODO
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

}
