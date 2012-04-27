package fr.sfvl.testunit;

import java.util.Set;

import junit.framework.TestCase;

import org.junit.Test;

public class SetBuilderTest extends TestCase {
	@Test
	public void testBuild() {
		Set<String> liste = SetBuilder.build("ValeurA", "ValeurB", "ValeurC");
		
		assertTrue(liste.contains("ValeurA"));
		assertTrue(liste.contains("ValeurB"));
		assertTrue(liste.contains("ValeurC"));
		assertFalse(liste.contains("ValeurX"));
	}
	
	/**
	 * Vérification de la construction de la liste avec le bon type.
	 */
	@Test
	public void testBuildVide() {
		Set<String> listeString = SetBuilder.build();
		assertTrue(listeString.isEmpty());
		listeString.add("Valeur");
		
		Set<Integer> listeInt = SetBuilder.build();
		assertTrue(listeInt.isEmpty());
		listeInt.add(5);
	}
}
