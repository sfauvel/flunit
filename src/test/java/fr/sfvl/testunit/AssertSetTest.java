package fr.sfvl.testunit;

import static fr.sfvl.testunit.AssertList.assertSet;
import static fr.sfvl.testunit.FluentAssertList.assertList;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class AssertSetTest {
	
	public static class MaClasse {
		private String nom;
		public MaClasse() {
			
		}
		public MaClasse(String nom) {
			this.nom = nom;
		}

		public String getNom() {
			return nom;
		}

		public void setNom(String nom) {
			this.nom = nom;
		}
	}
	
	@Test
	public void testSet() {
		List<MaClasse> listeMaClasse = new ArrayList<MaClasse>();
		listeMaClasse.add(new MaClasse("valeurA"));
		listeMaClasse.add(new MaClasse("valeurB"));
		
		AssertList.assertSet(MaClasse.class, listeMaClasse, "valeurA", "valeurB").getNom();
		
		{
			boolean exceptionLeve = false;
			try {
				assertSet(MaClasse.class, listeMaClasse, "valeurA", "valeurB", "valeurX").getNom();
			} catch (Throwable t) {
				exceptionLeve = true;
			}
			assertTrue("Une exception aurait du être levé", exceptionLeve);
		}
		{
			boolean exceptionLeve = false;
			try {
				assertSet(MaClasse.class, listeMaClasse, "valeurA").getNom();
			} catch (Throwable t) {
				exceptionLeve = true;
			}
			assertTrue("Une exception aurait du être levé", exceptionLeve);
		}
		{
			boolean exceptionLeve = false;
			try {
				assertSet(MaClasse.class, listeMaClasse, "valeurA", "valeurX").getNom();
			} catch (Throwable t) {
				exceptionLeve = true;
			}
			assertTrue("Une exception aurait du être levé", exceptionLeve);
		}

	}

	@Test
	public void testFluentList() {
		List<MaClasse> listeMaClasse = new ArrayList<MaClasse>();
		listeMaClasse.add(new MaClasse("valeurA"));
		listeMaClasse.add(new MaClasse("valeurB"));
		

		assertList(listeMaClasse).contains("valeurA", "valeurB").on(MaClasse.class).getNom();
		
		{
			boolean exceptionLeve = false;
			try {
				assertList(listeMaClasse).contains("valeurA", "valeurB", "valeurX").on(MaClasse.class).getNom();
			} catch (Throwable t) {
				exceptionLeve = true;
			}
			assertTrue("Une exception aurait du être levé", exceptionLeve);
		}
		{
			boolean exceptionLeve = false;
			try {
				assertList(listeMaClasse).contains("valeurA").on(MaClasse.class).getNom();
			} catch (Throwable t) {
				exceptionLeve = true;
			}
			assertTrue("Une exception aurait du être levé", exceptionLeve);
		}
		{
			boolean exceptionLeve = false;
			try {
				assertList(listeMaClasse).contains("valeurA", "valeurX").on(MaClasse.class).getNom();
			} catch (Throwable t) {
				exceptionLeve = true;
			}
			assertTrue("Une exception aurait du être levé", exceptionLeve);
		}

		assertList(listeMaClasse).contains("valeurA", "valeurB").on(MaClasse.class).getNom();
		
	}
}
