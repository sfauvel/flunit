package fr.sfvl.sql;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;


abstract class Critere {

	static class CritereString extends Critere {
		private String critere;
		public CritereString(String critere) {
			super();
			this.critere = critere;
		}
		
		public String toString() {
			return critere;
		}
		
		public List<Object> getParameters() {
			return new ArrayList<Object>();
		}
	}
	
	static class CritereChamp extends Critere {
		private String champ;
		private String operateur;
		private Object parametre;
		public CritereChamp(String champ, String operateur, Object parametre) {
			super();
			this.champ = champ;
			this.operateur = operateur;
			this.parametre = parametre;
		}
		
		public String toString() {
			String clauseWhere = champ + operateur;
			if (parametre != null) {
				clauseWhere += "?";
			}
			return clauseWhere;
		}
		
		public List<Object> getParameters() {
			List<Object> parameters = new ArrayList<Object>();
			parameters.add(parametre);
			return parameters;
		}
	}
	
	
	static class CritereListe extends Critere {
		private Critere[] listeCritere;
		private String separateur;
		public CritereListe(String separateur, Critere... listeCritere) {
			super();
			this.separateur = " " + separateur.trim() + " ";
			this.listeCritere = listeCritere;
		}
		
		public String toString() {
			return "(" + StringUtils.join(listeCritere, separateur) + ")";
		}
		
		public List<Object> getParameters() {
			List<Object> listeParametre = new ArrayList<Object>();
			for (int i = 0; i < listeCritere.length; i++) {
				Critere critere = listeCritere[i];
				listeParametre.addAll(critere.getParameters());
			}
			return listeParametre;
		}
	}
	
	public abstract List<Object> getParameters();
	
	public static Critere eq(String champ, Object param) {
		return new CritereChamp(champ, "=", param);
	}
	
	public static Critere and(Critere... listeCritere) {
		return new CritereListe("and", listeCritere);
	}

	public static Critere or(Critere... listeCritere) {
		return new CritereListe("or", listeCritere);
	}
}