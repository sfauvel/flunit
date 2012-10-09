package fr.sfvl.testunit;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * 
 */
public class GetterSetter<T> extends Enhancer {

	static final List<Object> LISTE_STRING = Arrays.asList((Object) null, "TUTU", "TITI", null);
	static final List<Object> LISTE_INT = Arrays.asList((Object) 1, 2, 3);
	public T on;

	/**
     * 
     */
	public GetterSetter(Class<?> clazz) {
		setSuperclass(clazz);
		on = withDefault();
	}
	
	public static <T> GetterSetter<T> create(Class<T> clazz) {
		return new GetterSetter<T>(clazz);
	}

	public T withDefault() {
		setCallback(new MethodInterceptorGetterSetter() {

			@Override
			public List<Object> getMapping(Class<?> returnType) {
				if (String.class.equals(returnType)) {
					return LISTE_STRING;
				} else if (int.class.equals(returnType)) {
					return LISTE_INT;
				}
				return Arrays.asList();
			}

		});
		return (T) create();
	}
	
	public T with(Object... args) {
		setCallback(new MethodInterceptorGetterSetter(args));
		return (T) create();
	}

	public T withNumber() {
		return with(LISTE_INT);
	}

	public T withString() {
		return with(LISTE_STRING.toArray());
	}

	class MethodInterceptorGetterSetter implements MethodInterceptor {

		private Object[] arguments;

		/**
		 * Liste des arguments qui seront utilisés par les getter et setter
		 * 
		 * @param arguments
		 */
		MethodInterceptorGetterSetter(Object... arguments) {
			this.arguments = arguments;
		}

		/**
		 * @param returnType
		 * @return
		 */
		public List<Object> getMapping(Class<?> returnType) {
			return new ArrayList<Object>();
		}

		public Object intercept(Object proxy, Method methodGetter, Object[] args, MethodProxy methodProxy)
				throws Throwable {

		
			Method methodSetter = getSetter(proxy.getClass(), methodGetter);
			if (methodSetter != null) {
				List<Object> listeArg = new ArrayList<Object>(Arrays.asList(arguments));
				listeArg.addAll(getMapping(methodGetter.getReturnType()));

				Assert.assertFalse("Aucun argument n'a été fourni pour " + methodGetter.getName() + ". Type "
						+ methodGetter.getReturnType().getName(),
						listeArg.isEmpty());

				for (Object arg : listeArg) {
					methodSetter.invoke(proxy, arg);
					Assert.assertEquals(arg, methodProxy.invokeSuper(proxy, args));
				}

			} else {
				return methodProxy.invokeSuper(proxy, args);
			}
			return null;

		}
	}


	public static <T> Method getSetter(Class<T> clazz, Method methodGetter) throws SecurityException, NoSuchMethodException {

		if (methodGetter.getName().startsWith("get") || methodGetter.getName().startsWith("is")) {
			String nomSetter;
			if (methodGetter.getName().startsWith("get")) {
				nomSetter = methodGetter.getName().replaceFirst("get", "set");
			} else {
				nomSetter = methodGetter.getName().replaceFirst("is", "set");
			}
	
			Class<?> returnType = methodGetter.getReturnType();
			Method methodSetter = clazz.getMethod(nomSetter, returnType);
			return methodSetter;
		} else {
			return null;
		}
	}
}
