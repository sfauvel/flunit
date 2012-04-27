package fr.sfvl.testunit;

import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;

import org.junit.Test;

public class MapBuilderTest extends TestCase {
	@Test
	public void testMapBuilder() {
		Map<String, Set<String>> liste = new MapBuilder<String, String>()
		 		 	.ajouter("Cle1", "ValeurA", "ValeurB", "ValeurC")
		 		 	.ajouter("Cle2", "ValeurA")
		 		 	.getMap();
		
		assertTrue(liste.get("Cle1").contains("ValeurA"));
		assertTrue(liste.get("Cle1").contains("ValeurB"));
		assertTrue(liste.get("Cle1").contains("ValeurC"));
		assertFalse(liste.get("Cle1").contains("ValeurX"));

		assertTrue(liste.get("Cle2").contains("ValeurA"));
		assertFalse(liste.get("Cle2").contains("ValeurX"));
		
		assertFalse(liste.containsKey("CleX"));
	}
	
	@Test
	public void testMapBuilderStatic() {
		Map<String, Set<String>> liste = MapBuilder
			.build("Cle1", "ValeurA", "ValeurB", "ValeurC")
		 	.build("Cle2", "ValeurA");
		
		assertTrue(liste.get("Cle1").contains("ValeurA"));
		assertTrue(liste.get("Cle1").contains("ValeurB"));
		assertTrue(liste.get("Cle1").contains("ValeurC"));
		assertFalse(liste.get("Cle1").contains("ValeurX"));

		assertTrue(liste.get("Cle2").contains("ValeurA"));
		assertFalse(liste.get("Cle2").contains("ValeurX"));
		
		assertFalse(liste.containsKey("CleX"));
	}
}
