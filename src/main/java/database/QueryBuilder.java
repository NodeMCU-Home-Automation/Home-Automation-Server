package database;

import  database.DatabaseProperties.*;

public class QueryBuilder
{
	/**
	 * build SQL-Add-Query for adding data to a database-table.
	 * First value has to be the primary-key, which is beeing incremented automatically by the database.
	 * Query is built according to the following pattern:
	 *
	 * 		"INSERT INTO  database_name " +
	 *		".table_name(primary_key, column_2, column_3,... column_i) " +
	 *		"VALUES(DEFAULT, ?, ?,... ?)";
	 *
	 * @param table enum of DB_Table specified in {@Link DatabaseProperties}
	 * @return executable sql-query
	 */
	public static String buildAddQuery(DB_Table table)
	{
		String query = "INSERT INTO " + DatabaseProperties.getDatabase() + "." + table.name() + "(" + table.getPrimaryKey() + ", ";

		for(int i = 0; i < table.getColumns().length; i++)
		{
			query += table.getColumns()[i];
			if(i < table.getColumns().length - 1)
				query += ", ";
		}

		query += ") VALUES(DEFAULT, ";

		for(int i = 0; i < table.getColumns().length; i++)
		{
			query += "?";
			if(i < table.getColumns().length - 1)
				query += ", ";
		}

		query += ")";

		return query;
	}

	/**
	 *
	 * 		"SELECT primary_key, column_2, column_3,... column_i " +
	 *		"FROM database_name.table_name "
	 *		"WHERE primary_key = " + id
	 * @param table enum of DB_Table specified in {@Link DatabaseProperties}
	 * @return
	 */
	public static String buildGetDatasetByIdQuery(DB_Table table, int id)
	{
		String query = "SELECT " + table.getPrimaryKey() + ", ";

		for(int i = 0; i < table.getColumns().length; i++)
		{
			query += table.getColumns()[i];
			if(i < table.getColumns().length - 1)
				query += ", ";
		}

		query += " FROM " + DatabaseProperties.getDatabase() + "." + table.getName() +
				" WHERE " + table.getPrimaryKey() + " = " + id;

		return query;
	}

	/**
	 * Builds query to delete
	 *
	 * 		"DELETE FROM database_name.table_name " +
	 *		"WHERE column = " + value
	 *
	 * @param table enum of DB_Table specified in {@Link DatabaseProperties}
	 * @return
	 */
	public static String buildDeleteQuery(DB_Table table, String valueColumn, Object value)
	{
		String query =
				"DELETE FROM " + DatabaseProperties.getDatabase() + "." + table.getName() +
				" WHERE " + valueColumn + " = " + value;

		return query;
	}

	/**
	 *
	 * 		"UPDATE database_name.table_name " +
	 *		"SET column_1 = ?, column_2 = ?,... column_i = ? " +
	 *		"WHERE primary_key = " + id
	 *
	 * @param table enum of DB_Table specified in {@Link DatabaseProperties}
	 * @return
	 */
	public static String buildUpdateQuery(DB_Table table, int id)
	{
		String query = "UPDATE " + DatabaseProperties.getDatabase() + "." + table.getName() +
				" SET ";

		for(int i = 0; i < table.getColumns().length; i++)
		{
			query += table.getColumns()[i] + " = ?";
			if(i < table.getColumns().length - 1)
				query += ", ";
		}

		query += " WHERE " + table.getPrimaryKey() + " = " + id;

		return query;
	}

	/**
	 *
	 * 		"SELECT primary_key FROM database_Name.table_name " +
	 *		"WHERE primary_key = " + id
	 *
	 * @param table enum of DB_Table specified in {@Link DatabaseProperties}
	 * @param id id of dataset which existence shall be checker
	 * @return
	 */
	public static String buildExistsDatasetWithIdQuery(DB_Table table, int id)
	{
		return "SELECT " + table.getPrimaryKey() + " FROM " + DatabaseProperties.getDatabase() + "." +
				table.getName() + " WHERE " + table.getPrimaryKey() + " = " + id;
	}
}
