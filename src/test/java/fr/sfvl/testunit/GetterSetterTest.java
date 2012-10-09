package fr.sfvl.testunit;


import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import junit.framework.Assert;
import junit.framework.AssertionFailedError;

import org.junit.Test;

/**
 * 
 */
public class GetterSetterTest {

	/**
	 * 
	 */
	public static class MonObjet {
		private String nom;
		
		/**
		 * Champ avec une erreur dans le setter.
		 */
		private String erreur;
		private int age;
		private boolean marie;
		
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

		public boolean isMarie() {
			return marie;
		}

		public void setMarie(boolean marie) {
			this.marie = marie;
		}

		public String getErreur() {
			return erreur;
		}

		public void setErreur(String erreur) {
			// Erreur dans le setter.
			//this.erreur = erreur;
		}
	}

    /**
	     * Exemple de test des méthodes get et set sur l'objet.
	     */
	    @Test
	    public void testCreateterSetter() {
	        MonObjet monObjet = new MonObjet();
	        Assert.assertNull(monObjet.getNom());
	        monObjet.setNom("Toto");
	        Assert.assertEquals("Toto", monObjet.getNom());
	        monObjet.setNom(null);
	        Assert.assertNull(monObjet.getNom());
	    }

    /**
	     * Création d'un objet permettant de tester les getter et les setter.
	     */
	    @Test
	    public void testCreateterSetterGeneric() {
	
	        GetterSetter<MonObjet> assertGetSet = GetterSetter.create(MonObjet.class);
	
			assertGetSet.on.getNom();
	        assertGetSet.on.getAge();
	
	        assertGetSet.withString().getNom();
	       
	        assertGetSet.with("MaValeur", "AutreValeur").getNom();
	    }
    
    @Test
	    public void testCreateterSetterGenericSurChampEnErreur() {
	    	GetterSetter<MonObjet> assertGetSet = new GetterSetter<MonObjet>(MonObjet.class);
	
	    	try {
	    		assertGetSet.on.getErreur();
	    		fail("Une exception aurait du être levée.");
	    	} catch (AssertionFailedError e) {
	
	    	}
	
	    }
    
    @Test
	    public void testCreateterSetterMethod() throws SecurityException, NoSuchMethodException {
	
	    	assertEquals(MonObjet.class.getMethod("setNom", String.class), 
	    			GetterSetter.getSetter(MonObjet.class, MonObjet.class.getMethod("getNom")));
	    	
	    	assertEquals(MonObjet.class.getMethod("setMarie", boolean.class), 
	    			GetterSetter.getSetter(MonObjet.class, MonObjet.class.getMethod("isMarie")));
	    	
	    	assertNull(GetterSetter.getSetter(MonObjet.class, MonObjet.class.getMethod("setNom", String.class)));
	    	
	    }
   
}
