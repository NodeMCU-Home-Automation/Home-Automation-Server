package database;

public class QueryBuilder
{
	/**
	 * build SQL-Add-Query for adding data to a database-table.
	 * First value has to be the primary-key, which is beeing incremented automatically by the database.
	 * Query is built according to the following pattern:
	 *
	 * 		"INSERT INTO  database_name " +
	 *		".table_name(primary_key, column_1, column_2,... column_i) " +
	 *		"VALUES(DEFAULT, ?, ?,... ?)";
	 *
	 * @param tablename Name of database table name
	 * @param columns all the tables column names
	 * @return executable sql-query
	 */
	public static String buildAddQuery(String tablename, String [] columns)
	{
		String query = "INSERT INTO " + DatabaseProperties.getDatabase() + "." + tablename + "(";

		for(int i = 0; i < columns.length; i++)
		{
			query += columns[i];
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

	/**
	 *
	 * 		"SELECT primary_key, column_1, column_2,... column_i " +
	 *		"FROM database_name.table_name "
	 *		"WHERE primary_key = id"
	 * @param tablename
	 * @param id
	 * @return
	 */
	public static String buildGetDatasetByIdQuery(String tablename, int id, String [] columns)
	{
		String query = "SELECT ";

		for(int i = 0; i < columns.length; i++)
		{
			query += columns[i];
			if(i < columns.length -1)
				query += ", ";
		}

		query += " FROM " + DatabaseProperties.getDatabase() + "." + tablename +
				" WHERE " + columns[0] /*PK*/ + " = " + id;

		return query;
	}

	/**
	 * Builds query to delete
	 *
	 * 		"DELETE FROM database_name.table_name " +
	 *		"WHERE column = value";
	 * @return
	 */
	public static String buildDeleteQuery(String tablename, String valueColumn, Object value)
	{
		String query =
				"DELETE FROM " + DatabaseProperties.getDatabase() + "." + tablename +
				" WHERE " + valueColumn + " = " + value;

		return query;
	}

	public static String buildUpdateQuery(String tablename, String [] columns)

	public static String buildGetTableQuery(String tablename, String [] columns)
	{

		return null;
	}
}
