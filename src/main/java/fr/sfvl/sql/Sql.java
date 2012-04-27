package fr.sfvl.sql;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import fr.sfvl.sql.Critere.CritereString;

public class Sql {

	static class Into<T> {
		public Into(Class<T> clazz, Map<String, Method> mappingChamp) {
			super();;
			this.clazz = clazz;
			this.mappingChamp = mappingChamp;
		}

		private Map<String, Method> mappingChamp;
		private Class<T> clazz;

		private <O> void set(O objet, Method methodGetter, Object valeur) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
			String nomSetter;
			if (methodGetter.getName().startsWith("get")) {
				nomSetter = methodGetter.getName().replaceFirst("get", "set");
			} else {
				nomSetter = methodGetter.getName().replaceFirst("is", "set");
			}

			Class<?> returnType = methodGetter.getReturnType();
			Method methodSetter = objet.getClass().getMethod(nomSetter, returnType);
				
			methodSetter.invoke(objet, valeur);		
		
		}
		
		public T getObjet(ResultSet resultSet) {
			try {
				T objet = clazz.getConstructor().newInstance();
				for (Map.Entry<String, Method> entry : mappingChamp.entrySet()) {
					Object valeur = resultSet.getObject(entry.getKey());
					set(objet, entry.getValue(), valeur);
				}
				
				return objet;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			
		}
	}
	
	private List<String> listeChamp = new ArrayList<String>();
	private String table;
	private String join = "";
	private Critere clauseWhere;
	private ArrayList<Object> listeParameters = new ArrayList<Object>();
	public static Sql select(String... listeChamp) {	
		Sql sql = new Sql();
		sql.listeChamp = new ArrayList<String>(Arrays.asList(listeChamp));
		return sql;
	}

	public Sql from(String table) {
		this.table = table;
		return this;
	}

	
	public String toString() {
		String sql = "select " + StringUtils.join(listeChamp, ", ")
			+ " from " + table
			+ join;
		
		if (clauseWhere != null) {
			sql += " where " + clauseWhere;
		}
		return sql;
	}

	public Sql where(String clauseWhere) {
		return where(new CritereString(clauseWhere));
	}

	public Sql where(Critere clauseWhere) {
		this.clauseWhere = clauseWhere;
		return this;
	}

	public List<Object> getParameters() {
		return clauseWhere.getParameters();
	}

	public Sql join(String tableJoin, String champTable, String champTableJoin) {
		join += " inner join " + tableJoin + " on " + champTable + "=" + champTableJoin;
		return this;
	}

	public Object join(String tableJoin, String champTable) {
		return join(tableJoin, champTable, champTable);
	}

}
