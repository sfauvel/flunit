package fr.sfvl.testunit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class SetterRecorderTest {

	public static class MonObjet {
		public MonObjet(String nom, int age) {
			super();
			this.nom = nom;
			this.age = age;
		}

		public MonObjet() {
			// TODO Auto-generated constructor stub
		}

		private String nom;
		private int age;

		public String getNom() {
			return nom;
		}

		public void setNom(String nom) {
			this.nom = nom;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}
	}

	@Test
	public void testGetterSetterSetValeur() {
		MonObjet monObjet = new MonObjet();
		SetterRecorder<MonObjet> assertGetSet = new SetterRecorder<MonObjet>(monObjet);

		assertGetSet.set("ma valeur").getNom();
		assertGetSet.set(30).getAge();

		assertEquals("ma valeur", monObjet.getNom());
		assertEquals(30, monObjet.getAge());
	}
	
	@Test
	public void testGetterSetterObjetDifferent() {
		MonObjet monObjet = new MonObjet();
		SetterRecorder<MonObjet> assertGetSet = new SetterRecorder<MonObjet>(monObjet);

		assertGetSet.set("ma valeur").getNom();
		assertGetSet.set(30).getAge();

		assertGetSet.assertEquals(new MonObjet("ma valeur", 30));
		// Pas d'exception, les objets sont égaux
		
		boolean exceptionLeve = false;
		try {
			assertGetSet.assertEquals(new MonObjet("autre valeur", 30));
		} catch (Throwable t) {
			exceptionLeve = true;
		}
		assertTrue("Une exception aurait du être levé", exceptionLeve);
		
		try {
			assertGetSet.assertEquals(new MonObjet("ma valeur", 40));
		} catch (Throwable t) {
			exceptionLeve = true;
		}
		assertTrue("Une exception aurait du être levé", exceptionLeve);

	}
	
}
