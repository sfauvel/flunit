package fr.sfvl.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.sfvl.sql.Sql.Into;

public class SqlRunner {
	Connection connection;
	public SqlRunner(Connection connection) {
		this.connection = connection;
	}

	public <T> T execute(Sql sql, Into<T> into) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(sql.toString());
		//statement.setObject(parameterIndex, x);
		ResultSet resultSet = statement.executeQuery();
		return into(resultSet, into);
	}

	public <T> T into(ResultSet resultSet, Into<T> into) {
		return into.getObjet(resultSet);
	}
}
