package database;

import database.DatabaseProperties;
import database.IDatabaseService;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import static database.DatabaseProperties.DB_Table;
import static database.DatabaseProperties.DB_Table.*;

public class DatabaseServiceGeneric implements IDatabaseService
{
	private Connection connection;

	private DatabaseProperties.DB_Table db_table;

	public DatabaseServiceGeneric(DB_Table db_table)
	{
		this.db_table = db_table;

		try
		{
			this.connection = IDatabaseService.connect();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public String getTablename()
	{
		return roomclimate.getName();
	}

	@Override
	public String [] getColumns()
	{
		//return roomclimate.getColumnsArray();
		return null;
	}

	@Override
	public HashMap<String, Object> getData() throws SQLException
	{
		// TODO
		return null;
	}

	@Override
	public void deleteData(int key) throws SQLException
	{
		// TODO
	}

	@Override
	public boolean existsData(String column, Object value) throws SQLException
	{
		// TODO
		return false;
	}

	@Override
	public void editData(HashMap dataMap) throws SQLException
	{
		// TODO
	}

	@Override
	public void addData(HashMap dataMap) throws SQLException
	{
		IDatabaseService.checkConnection(connection);

		String query = IDatabaseService.buildAddQuery(db_table.getName(), db_table.getColumnsArray());

		PreparedStatement pst = connection.prepareStatement(query);

		// TODO generische Variante entwickeln
		int counter = 0;
		for(Map.Entry<String, Class> columnEntry: db_table.getColumns().entrySet())
		{
			//  primary key is added automatically
			if(counter == 0) {
				counter++;
				continue;
			}

			Class datatype = columnEntry.getValue();

			if(datatype == Object.class)
				pst.setObject(counter, dataMap.get(columnEntry.getKey()));
			else if(datatype == Float.class)
				pst.setFloat(counter, (Float) dataMap.get(columnEntry.getKey()));
			else if(datatype == Integer.class)
				pst.setInt(counter, (Integer) dataMap.get(columnEntry.getKey()));

			counter++;
		}

		/*pst.setObject(1, dataMap.get(roomclimate.getColumnsArray()[1]));
		pst.setFloat(2, (Float) dataMap.get(roomclimate.getColumnsArray()[2]));
		pst.setFloat(3, (Float) dataMap.get(roomclimate.getColumnsArray()[3]));
*/
		pst.executeUpdate();
	}
}
