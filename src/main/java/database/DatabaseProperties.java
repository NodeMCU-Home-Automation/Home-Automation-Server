package database;

import java.util.HashMap;

public class DatabaseProperties
{
	// Die folgenden Attribute müssen beim Einrichten der Datenbank verwendet werden:
	private static final String url = "jdbc:mysql://localhost:3306/?verifyServerCertificate=false&useSSL=true";
	private static final String database = "homeautomation";
	private static final String username = "DatabaseService";
	private static final String password = "password";
	public static final DB_Table[] tables = DB_Table.class.getEnumConstants();

	/**
	 * Definition of all DB-Tables and their DatabaseService-Class
	 * Primary Key (PK) has to be incremented automatically by the database.
	 * PK hast to contain the String "id"
	 * No other column-name is allowed to contain String "id"
	 */
	public enum DB_Table
	{
		roomclimate(
				"roomclimate",
				new HashMap<String, Class>() {
					{
					put("id", Integer.class);
					put("timestamp", Object.class);
					put("temperature", Float.class);
					put("humidity", Float.class);}
				}
		);

		private final String name;
		private final HashMap<String, Class> columns;

		DB_Table(String name, HashMap<String, Class> columns)
		{
			this.name = name;
			this.columns = columns;
		}

		public String getName()
		{
			return name;
		}

		public HashMap<String, Class> getColumns()
		{
			return this.columns;
		}

		public Object [] getColumnsArray()
		{
			return columns.keySet().toArray();
		}
	}

	/*DatabaseProperties()
	{
		columns = DB_Table.class.getEnumConstants();
	}*/

	public static String getUrl() {
		return url;
	}

	public static String getDatabase() { return database; }

	public static String getUsername() {
		return username;
	}

	public static String getPassword() {
		return password;
	}
}
