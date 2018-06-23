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
 * Interface zum Erstellen eines DatenbankServices für eine neue Tabelle in der Datenbank
 *
 */
public interface IDatabaseService
{
	/**
	 * Stellt eine Verbindung zur MySQL-Datenbank her.
	 * @throws IllegalStateException wenn die Datenbank nicht erreichbar ist.
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

	static void checkConnection(Connection connection)
	{
		try {
			if(connection.isClosed())
				try
				{
					IDatabaseService.connect();
				} catch (Exception e)
				{
					e.printStackTrace();
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

	/**
	 *
	 * 		"SELECT primary_key, column_1, column_2,... column_i " +
	 *		"FROM database_name.table_name"
	 * @param tablename
	 * @param columns
	 * @return
	 */
	static String buildGetQuery(String tablename, String [] columns)
	{
		// TODO
		return null;
	}

	/**
	 * build SQL-Add-Query after following pattern:
	 *
	 * 		"INSERT INTO  database_name " +
	 *		".table_name(primary_key, column_1, column_2,... column_i) " +
	 *		"VALUES(DEFAULT, ?, ?,... ?)";
	 *
	 * @param tablename Name of database table name
	 * @param columns all the tables column names
	 * @return executable sql-query
	 */
	static String buildAddQuery(String tablename, Object [] columns)
	{
		String query = "INSERT INTO " + DatabaseProperties.getDatabase() + "." + tablename + "(";

		for(int i = 0; i < columns.length; i++)
		{
			query += (String) columns[i];
			if(i < columns.length - 1)
				query += ", ";
		}

		query += ") VALUES(DEFAULT, ";

		for(int i = 0; i < columns.length-1; i++)
		{
			query += "?";
			if(i < columns.length - 2)
				query += ", ";
		}

		query += ")";

		return query;
	}



	String getTablename();

	String [] getColumns();

	HashMap<String, Object> getData() throws SQLException;

	void addData(HashMap<String, Object> dataMap) throws SQLException;

	void editData(HashMap<String, Object> dataMap) throws SQLException;

	void deleteData(int key) throws SQLException;

	boolean existsData(String column, Object value) throws SQLException;
}
