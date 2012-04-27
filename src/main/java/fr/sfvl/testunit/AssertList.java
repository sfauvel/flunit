package fr.sfvl.testunit;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.junit.Assert;

public class AssertList {
	public static <T> T assertSet(Class<?> clazz, final List<T> listeMaClasse, Object... listeValeur) {
		final List<Object> listeAttendu = new ArrayList(Arrays.asList(listeValeur));
		Enhancer e = new Enhancer();
		e.setSuperclass(clazz);
		e.setCallback(new MethodInterceptor() {

				public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
					for (T objet : listeMaClasse) {
						Object valeur = method.invoke(objet, null);
						Assert.assertTrue("Valeur non attendue:" + valeur,  listeAttendu.remove(valeur));
					}
					Assert.assertTrue(listeAttendu.isEmpty());
					return null;
				}
				
			});;
		return (T) e.create();
	}

	
	
}
