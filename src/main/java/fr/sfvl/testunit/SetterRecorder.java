package fr.sfvl.testunit;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.junit.Assert;

/**
 * 
 */
public class SetterRecorder<T> extends Enhancer {

	class Propriete {
		public Propriete(Object valeur, Method methodGetter, Method methodSetter) {
			super();
			this.valeur = valeur;
			this.methodGetter = methodGetter;
			this.methodSetter = methodSetter;
		}
		Object valeur;
		Method methodGetter;
		Method methodSetter;
	}
	
	private List<Propriete> listePropriete = new ArrayList<Propriete>();
	private T objetManipule;
	private T objetProxy;
	private Object derniereValeur;
	/**
     * 
     */
	public SetterRecorder(T objet) {
		setSuperclass(objet.getClass());
		setCallback(new MethodInterceptorGetterSetter());
		objetProxy = (T) create();
		objetManipule = objet;
	}

	class MethodInterceptorGetterSetter implements MethodInterceptor {

		public Object intercept(Object proxy, Method methodGetter, Object[] args, MethodProxy methodProxy)
				throws Throwable {

			if (methodGetter.getName().startsWith("get") || methodGetter.getName().startsWith("is")) {
				String nomSetter;
				if (methodGetter.getName().startsWith("get")) {
					nomSetter = methodGetter.getName().replaceFirst("get", "set");
				} else {
					nomSetter = methodGetter.getName().replaceFirst("is", "set");
				}

				Class<?> returnType = methodGetter.getReturnType();
				Method methodSetter = objetManipule.getClass().getMethod(nomSetter, returnType);
				
				listePropriete.add(new Propriete(derniereValeur, methodGetter, methodSetter));
				
				methodSetter.invoke(objetManipule, derniereValeur);		
			}
			return null;

		}
	}

	public T creer() {
		setCallback(new MethodInterceptorGetterSetter());
		objetManipule = (T) create();
		return objetManipule;
	}


	public T set(Object valeur) {
		derniereValeur = valeur;
		return objetProxy;
	}


	public void assertEquals(T actual) {
		for (Propriete propriete : listePropriete) {
			try {
				Assert.assertEquals("Erreur sur " + propriete.methodGetter.getName(), 
						propriete.valeur, propriete.methodGetter.invoke(actual, null));
			} catch (Exception e) {
				Assert.fail("Erreur lors de l'invocation de " + propriete.methodGetter.getName());
			}
		}
	}
}
