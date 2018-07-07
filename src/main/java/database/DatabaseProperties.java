package database;

import database.database_services.ClimateDatabaseService;

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
	 */
	public enum DB_Table
	{
		roomclimate(
				"roomclimate",
				new String [] {"timestamp", "temperature", "humidity"},
				"id",
				ClimateDatabaseService.class
		);

		private final String name;
		private final String [] columns;
		private final String primaryKey;
		private Class DBS_Class;

		DB_Table(String name, String [] columns, String primaryKey, Class DBS_Class)
		{
			this.name = name;
			this.primaryKey = primaryKey;
			this.columns = columns;
			this.DBS_Class = DBS_Class;
		}

		public String getName()
		{
			return name;
		}

		public String [] getColumns()
		{
			return columns;
		}

		public Class getDBS_Class()
		{
			return DBS_Class;
		}

		public String getPrimaryKey()
		{
			return primaryKey;
		}
	}

	/*DatabaseProperties()
	{
		tables = DB_Table.class.getEnumConstants();
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
