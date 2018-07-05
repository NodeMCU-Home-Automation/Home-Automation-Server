package database;

import org.joda.time.DateTime;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;

import static database.Log.logErr;
import static database.Log.logInf;

/**
 * To implement a database-service for a new database-Table use this Interface.
 * Îf sufficient, use {@Link QueryBuilder} to auto-generate query-Strings.
 * Every new database-table with its name, columns and implemented service-class
 * has to be populated into {@Link DatabaseProperties}.
 *
 * @author marvin mai
 */
public interface IDatabaseService
{
	/**
	 * Establishes connection to the mysql-server.
	 * @throws IllegalStateException if database is not reachable.
	 */
	static Connection connect() throws Exception {
		logInf("Connecting database...");

		Connection connection;

		try{
			connection = DriverManager.getConnection(DatabaseProperties.getUrl(), DatabaseProperties.getUsername(),
					DatabaseProperties.getPassword());
			logInf("Database connected!");
		}catch(SQLException e) {
			logErr("Verbindung zur Datenbank fehlgeschlagen!");
			e.printStackTrace();
			throw new Exception();
		}
		return connection;
	}

	/**
	 * Checks, if connection is currently established. If not established, initiates new attempt to connect.
	 * @param connection
	 */
	static void checkConnection(Connection connection)
	{
		try {
			if(connection.isClosed())
			{
				try
				{
					IDatabaseService.connect();
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {e.printStackTrace();}
	}

	/**
	 * Konvertiert ein joda.time.DateTime in einen Timestamp, der in der SQL-Datenbank gespeicher weren kann
	 * @param dateTime zu konvertierendes joda.time.DateTime
	 * @return Timestamp
	 */
	static Object convertJodaDateTimeToSqlTimestamp(DateTime dateTime)
	{
		// Convert sql-Timestamp to joda.DateTime
		return new Timestamp(dateTime.getMillis());
	}

	/**
	 * Konvertiert einen Timestamp aus der SQL-Datenbank in eine joda.time.DateTime.
	 * @param sqlTimestamp zu konvertierender Timestamp
	 * @return jode.time.DateTime
	 */
	static DateTime convertSqlTimestampToJodaDateTime(Timestamp sqlTimestamp)
	{
		//Convert joda.DateTime to sql-Timestamp
		return new DateTime(sqlTimestamp);
	}

	String getTablename();

	String [] getColumns();

	/**
	 * Adds dataset to the database-table.
	 * @param dataMap dataset, which will be added.
	 * @throws SQLException when mysql-server reports error, e.g. connection timeout.
	 */
	void addData(HashMap<String, Object> dataMap) throws SQLException;

	/**
	 * Gets dataset from the database-table.
	 * @param id id (PK) of the dataset that will be retrieved.
	 * @return dataset from database with given id.
	 * @throws SQLException
	 */
	HashMap<String, Object> getDatasetById(int id) throws SQLException;

	/**
	 * Deletes dataset from database-table.
	 * @param valueColumn column, that will be searched for the value to find datasets to delete.
	 * @param value value that has to be in a dataset to be deleted.
	 * @throws SQLException
	 */
	void deleteData(String valueColumn, Object value) throws SQLException;

	void updateData(HashMap<String, Object> dataMap, int id) throws SQLException;

	boolean existsData(String column, Object value) throws SQLException;
}
