package database.database_services;

import database.IDatabaseService;
import org.joda.time.DateTime;

import java.sql.*;
import java.util.HashMap;

import static database.DatabaseProperties.DB_Table.roomclimate;

public class ClimateDatabaseService implements IDatabaseService
{
	private Connection connection;

	public ClimateDatabaseService()
	{
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
		return roomclimate.getColumns();
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

		String query = IDatabaseService.buildAddQuery(roomclimate.getName(), roomclimate.getColumns());

		PreparedStatement pst = connection.prepareStatement(query);

		// TODO generische Variante entwickeln, wenn möglich und praktikabel
		// Ansatz: In DB_Table enum Datentyp der Variablen hinterlegen

		pst.setObject(1, dataMap.get(roomclimate.getColumns()[1]));
		pst.setFloat(2, (Float) dataMap.get(roomclimate.getColumns()[2]));
		pst.setFloat(3, (Float) dataMap.get(roomclimate.getColumns()[3]));

		pst.executeUpdate();
	}
}
