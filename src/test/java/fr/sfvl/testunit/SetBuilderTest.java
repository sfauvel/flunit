package fr.sfvl.testunit;

import java.util.Set;

import junit.framework.TestCase;

import org.junit.Test;

public class SetBuilderTest extends TestCase {
	@Test
	public void testSetBuilder() {
		Set<String> liste = SetBuilder.build("ValeurA", "ValeurB", "ValeurC");
		
		assertTrue(liste.contains("ValeurA"));
		assertTrue(liste.contains("ValeurB"));
		assertTrue(liste.contains("ValeurC"));
		assertFalse(liste.contains("ValeurX"));
	}
}
