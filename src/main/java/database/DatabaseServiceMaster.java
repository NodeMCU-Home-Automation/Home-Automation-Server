package database;

import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import database.DatabaseProperties.*;

@Service
public class DatabaseServiceMaster
{
	private Connection connection;

	HashMap<DB_Table, IDatabaseService> databaseServices = new HashMap<>();

	public DatabaseServiceMaster()
	{
		try
		{
			connection = IDatabaseService.connect();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		// Generate all nessecary database-services
		IDatabaseService dbs;
		for(DB_Table t: DatabaseProperties.tables)
		{
			try
			{
				dbs = (IDatabaseService) t.getDBS_Class().getDeclaredConstructor(Connection.class)
						.newInstance(connection);
				databaseServices.put(t, dbs);
			} catch (InstantiationException e)
			{
				e.printStackTrace();
			} catch (IllegalAccessException e)
			{
				e.printStackTrace();
			} catch (InvocationTargetException e)
			{
				e.printStackTrace();
			} catch (NoSuchMethodException e)
			{
				e.printStackTrace();
			}
		}
	}

	public void addData(HashMap<String, Object> dataMap, DB_Table db_table)  throws SQLException
	{
		databaseServices.get(db_table).addData(dataMap);
	}

	public HashMap<String, Object> getDatasetById(int id, DB_Table db_table) throws SQLException
	{
		return databaseServices.get(db_table).getDatasetById(id);
	}

	public void deleteData(String valueColumn, Object value, DB_Table db_table) throws SQLException
	{
		databaseServices.get(db_table).deleteData(valueColumn, value);
	}

	public void updateData(HashMap<String, Object> dataMap, int id, DB_Table db_table) throws SQLException
	{
		databaseServices.get(db_table).updateData(dataMap, id);
	}

	public boolean existsData(int id, DB_Table db_table) throws SQLException
	{
		return databaseServices.get(db_table).existsData(id);
	}

}
