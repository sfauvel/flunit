package fr.sfvl.testunit;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 */
public class SetBuilder<K, V> {

	public static <T> Set<T> build(T... listeValeur) {
		return new HashSet<T>(Arrays.asList(listeValeur));
	}
	
}
