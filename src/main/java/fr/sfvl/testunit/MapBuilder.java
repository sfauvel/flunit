package fr.sfvl.testunit;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Classe permettant de construire des map de mani�re plus concise.
 * Cela est utile pour la lisibilit� des tests et les initialisations de variables statiques.
 *Exemple:
 * protected static Map<String, Set<String>> liste = new MapBuilder<String, String>()
 *		 	.ajouter("Cle1", "ValeurA", "ValeurB", "ValeurC")
 *		 	.ajouter("Cle2", "ValeurA")
 *		 	.getMap();
 *	
 * @param <K>
 * @param <V>
 */
public class MapBuilder<K, V> {
	Map<K, Set<V>> map = new HashMap<K, Set<V>>();
	
	public MapBuilder<K, V> ajouter(K cle, V... listeValeur) {
		map.put(cle, new HashSet<V>(Arrays.asList(listeValeur)));
		return this;
	}

	public Map<K, Set<V>> getMap() {
		return map;
	}

	static class MapInterne<K, V> extends HashMap<K, Set<V> > {
		public MapInterne<K, V> build(K cle, V... listeValeur) {
			put(cle, new HashSet<V>(Arrays.asList(listeValeur)));
			return this;
		}
	}
	
	public static <K, V> MapInterne<K, V> build(K cle, V... listeValeur) {
		MapInterne<K, V> map = new MapInterne<K, V>();
		map.build(cle, listeValeur);
		return map;
	}
	
}
