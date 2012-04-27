package fr.sfvl.sql;

import static fr.sfvl.sql.Critere.and;
import static fr.sfvl.sql.Critere.eq;
import static fr.sfvl.sql.Critere.or;
import junit.framework.TestCase;

import org.junit.Test;

import fr.sfvl.sql.Critere.CritereChamp;
import fr.sfvl.sql.Critere.CritereString;

public class CritereTest extends TestCase {

	@Test
	public void testCritereString() throws Exception {
		{
			Critere critere = new CritereString("NOM='Durand'");
			assertEquals("NOM='Durand'", critere.toString());
			assertTrue(critere.getParameters().isEmpty());
		}
	}
	
	@Test
	public void testCritereEq() throws Exception {
		{
			Critere critere = new CritereChamp("NOM", "=", "Durand");
			assertEquals("NOM=?", critere.toString());
			assertEquals("Durand", critere.getParameters().get(0));
		}
		{
			Critere critere = Critere.eq("NOM", "Durand");
			assertEquals("NOM=?", critere.toString());
			assertEquals("Durand", critere.getParameters().get(0));
		}
	}
	
	@Test
	public void testCritereAnd() throws Exception {
		{
			Critere critere = Critere.and(Critere.eq("NOM", "Durand"), Critere.eq("PRENOM", "Jean"));
			assertEquals("(NOM=? and PRENOM=?)", critere.toString());
			assertEquals("Durand", critere.getParameters().get(0));
			assertEquals("Jean", critere.getParameters().get(1));
		}
	}
	
	@Test
	public void testCritereOr() throws Exception {
		{
			Critere critere = Critere.or(Critere.eq("NOM", "Durand"), Critere.eq("PRENOM", "Jean"));
			assertEquals("(NOM=? or PRENOM=?)", critere.toString());
			assertEquals("Durand", critere.getParameters().get(0));
			assertEquals("Jean", critere.getParameters().get(1));
		}
	}
	
	@Test
	public void testCritereMixOrAnd() throws Exception {
		Critere critere = 
			or(
				eq("C1", "A"), 
				and(
					eq("C2", "B"), 
					eq("C3", "C")), 
				eq("C4", "D")
			);
		
		assertEquals("(C1=? or (C2=? and C3=?) or C4=?)", critere.toString());
		assertEquals("A", critere.getParameters().get(0));
		assertEquals("B", critere.getParameters().get(1));
		assertEquals("C", critere.getParameters().get(2));
		assertEquals("D", critere.getParameters().get(3));
		
	}
}
