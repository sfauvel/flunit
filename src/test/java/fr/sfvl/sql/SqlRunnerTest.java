package fr.sfvl.sql;

import static fr.sfvl.sql.Sql.select;
import static org.junit.Assert.assertEquals;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import org.easymock.EasyMock;
import org.junit.Test;

import fr.sfvl.sql.Sql.Into;

public class SqlRunnerTest {
	public static class Objet {
		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getNom() {
			return nom;
		}

		public void setNom(String nom) {
			this.nom = nom;
		}

		private String id;
		private String nom;
	}

	// @Test
	// public void testSelectInto() throws SecurityException,
	// NoSuchMethodException {
	// Map<String, Method> mappingChamp = new HashMap<String, Method>();
	// mappingChamp.put("ID", Objet.class.getMethod("getId"));
	// mappingChamp.put("NOM", Objet.class.getMethod("getNom"));
	//
	// Into mapper = new Into(Objet.class, mappingChamp);
	//
	// Sql sql = select("ID", "NOM").from("TABLE");
	// Objet objet = sql.into(mapper);
	//
	// Assert.assertEquals("A", objet.getId());
	// Assert.assertEquals("B", objet.getNom());
	// }
	@Test
	public void testExecuteInto() throws Exception {

		Sql sql = select("ID", "NOM").from("TABLE");

		ResultSet resultSet = null;

		PreparedStatement statementMock = EasyMock.createMock(PreparedStatement.class);
		EasyMock.expect(statementMock.executeQuery()).andReturn(resultSet);
		EasyMock.replay(statementMock);

		Connection connectionMock = EasyMock.createMock(Connection.class);
		EasyMock.expect(connectionMock.prepareStatement(sql.toString())).andReturn(statementMock);
		EasyMock.replay(connectionMock);

		SqlRunner runner = new SqlRunner(connectionMock);

		Into<Objet> into = new Into<Objet>(Objet.class, null);

		Objet objet = runner.execute(sql, into);

		EasyMock.verify(connectionMock);
	}

	@Test
	public void testInto() throws Exception {
		SqlRunner runner = new SqlRunner(null);

		Map<String, Method> mappingChamp = new HashMap<String, Method>();
		mappingChamp.put("ID", Objet.class.getMethod("getId"));
		mappingChamp.put("NOM", Objet.class.getMethod("getNom"));

		Into into = new Into(Objet.class, mappingChamp);

		ResultSet resultSetMock = EasyMock.createMock(ResultSet.class);
		EasyMock.expect(resultSetMock.getObject("ID")).andReturn("A");
		EasyMock.expect(resultSetMock.getObject("NOM")).andReturn("B");
		EasyMock.replay(resultSetMock);

		Objet objet = runner.into(resultSetMock, into);
		assertEquals((Object) "A", objet.getId());
		assertEquals((Object) "B", objet.getNom());
	}

}
