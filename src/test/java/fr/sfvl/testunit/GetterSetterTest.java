package fr.sfvl.testunit;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import junit.framework.Assert;

import org.junit.Test;

/**
 * 
 */
public class GetterSetterTest {

	public static class MonObjet {
		private String nom;
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
	}

    @Test
    public void testGetterSetter() {
        MonObjet monObjet = new MonObjet();
        Assert.assertNull(monObjet.getNom());
        monObjet.setNom("Toto");
        Assert.assertEquals("Toto", monObjet.getNom());
        monObjet.setNom(null);
        Assert.assertNull(monObjet.getNom());
    }

    @Test
    public void testGetterSetterGeneric() {

        GetterSetter<MonObjet> assertGetSet = new GetterSetter<MonObjet>(MonObjet.class);

        assertGetSet.on.getNom();
        assertGetSet.on.getAge();

        assertGetSet.withString().getNom();
       
         assertGetSet.with("MaValeur", "AutreValeur").getNom();
      
    }
    
    @Test
    public void testGetterSetterMethod() throws SecurityException, NoSuchMethodException {

    	assertEquals(MonObjet.class.getMethod("setNom", String.class), 
    			GetterSetter.getSetter(MonObjet.class, MonObjet.class.getMethod("getNom")));
    	
    	assertEquals(MonObjet.class.getMethod("setMarie", boolean.class), 
    			GetterSetter.getSetter(MonObjet.class, MonObjet.class.getMethod("isMarie")));
    	
    	assertNull(GetterSetter.getSetter(MonObjet.class, MonObjet.class.getMethod("setNom", String.class)));
    	
    }
   
}
