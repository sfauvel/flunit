package fr.sfvl.sql;

import static fr.sfvl.sql.Critere.eq;
import static fr.sfvl.sql.Sql.select;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class SqlTest {

	@Test
	public void testSelect() {
		assertThat(
				select("ID", "NOM").from("TABLE").toString(),
				equalTo("select ID, NOM from TABLE"));
	}
	
	@Test
	public void testJoin() {
		assertThat(
				select("*").from("TABLE").join("OTHER", "T_ID", "O_ID").toString(),
				equalTo("select * from TABLE inner join OTHER on T_ID=O_ID"));
		
		assertThat(
				select("*").from("TABLE").join("OTHER", "ID").toString(),
				equalTo("select * from TABLE inner join OTHER on ID=ID"));
	}
 
	@Test
	public void testWhere() {
		assertThat(
				select("*").from("TABLE").where("ID=1").toString(),
				equalTo("select * from TABLE where ID=1"));
	}
	
	@Test
	public void testWhereAvecParametre() {
		Sql sql = select("*").from("TABLE").where(eq("ID", 1));
		Assert.assertThat(sql.toString(),
				equalTo("select * from TABLE where ID=?"));
		Assert.assertEquals(1, sql.getParameters().get(0));
	}
	
	@Test
	public void testWhereAvecCritere() {
		Critere critereMock = new Critere() {

			@Override
			public String toString() {
				return "ID=? or NOM=?";
			}

			@Override
			public List<Object> getParameters() {
				return Arrays.asList((Object)"A", "B");
			}
			
		};
		
		Sql sql = select("*").from("TABLE").where(critereMock);
		
		Assert.assertThat(sql.toString(),
				equalTo("select * from TABLE where ID=? or NOM=?"));
		Assert.assertEquals("A", sql.getParameters().get(0));
		Assert.assertEquals("B", sql.getParameters().get(1));
	}
	
	

}
