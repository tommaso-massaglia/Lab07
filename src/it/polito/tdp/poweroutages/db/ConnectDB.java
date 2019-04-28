package it.polito.tdp.poweroutages.db;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariConfig;

/**
 * Utility class for connecting to the database
 * 
 * Uses the HikariCP library for managing a connection pool
 * 
 * @see <a href="https://brettwooldridge.github.io/HikariCP/">HikariCP</a>
 */

public class ConnectDB {

	private static final String jdbcURL = "jdbc:mysql://localhost/poweroutages";
	private static HikariDataSource ds;

	public static Connection getConnection() {

		if (ds == null) {
			HikariConfig config = new HikariConfig();
			config.setJdbcUrl(jdbcURL);
			config.setUsername("root");
			config.setPassword("tommaso");

			// configurazione MySQL
			config.addDataSourceProperty("cachePrepStmts", "true");
			config.addDataSourceProperty("prepStmtCacheSize", "250");
			config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

			ds = new HikariDataSource(config);
		}

		try {

			return ds.getConnection();

		} catch (SQLException e) {
			System.err.println("Errore connessione al DB");
			throw new RuntimeException(e);
		}
	}

}