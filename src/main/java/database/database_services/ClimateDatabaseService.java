package database.database_services;

import database.IDatabaseService;
import database.QueryBuilder;
import org.joda.time.DateTime;

import java.sql.*;
import java.util.HashMap;

import static database.DatabaseProperties.DB_Table.roomclimate;

public class ClimateDatabaseService implements IDatabaseService
{
	private Connection connection;

	public ClimateDatabaseService()
	{
		// TODO get connection as transfer-parameter from DatabaseServiceMaster
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
	public void addData(HashMap dataMap) throws SQLException
	{
		IDatabaseService.checkConnection(connection);

		String query = QueryBuilder.buildAddQuery(roomclimate.getName(), roomclimate.getColumns());

		PreparedStatement pst = connection.prepareStatement(query);

		pst.setObject(1, dataMap.get(roomclimate.getColumns()[1]));
		pst.setFloat(2, (Float) dataMap.get(roomclimate.getColumns()[2]));
		pst.setFloat(3, (Float) dataMap.get(roomclimate.getColumns()[3]));

		pst.executeUpdate();
	}

	@Override
	public HashMap<String, Object> getDatasetById(int id) throws SQLException
	{
		IDatabaseService.checkConnection(connection);

		String query = QueryBuilder.buildGetDatasetByIdQuery(roomclimate.getName(), id, roomclimate.getColumns());

		PreparedStatement pst = connection.prepareStatement(query);

		ResultSet rs = pst.executeQuery();

		HashMap<String, Object> result = new HashMap<>();
		while(rs.next())
		{
			result.put(roomclimate.getColumns()[0], rs.getInt(roomclimate.getColumns()[0]));
			result.put(roomclimate.getColumns()[1], IDatabaseService
					.convertSqlTimestampToJodaDateTime(rs.getTimestamp(roomclimate.getColumns()[1])));
			result.put(roomclimate.getColumns()[2], rs.getFloat(roomclimate.getColumns()[2]));
			result.put(roomclimate.getColumns()[3], rs.getFloat(roomclimate.getColumns()[3]));

			return result;
		}

		return null;
	}

	@Override
	public void deleteData(String valueColumn, Object value) throws SQLException
	{
		IDatabaseService.checkConnection(connection);

		String query = QueryBuilder
				.buildDeleteQuery(roomclimate.getName(), valueColumn, value);

		PreparedStatement pst = connection.prepareStatement(query);

		pst.executeUpdate();
	}

	@Override
	public void updateData(HashMap dataMap, int id) throws SQLException
	{
		IDatabaseService.checkConnection(connection);

		String query = QueryBuilder.buildUpdateQuery(roomclimate.getName(), roomclimate.getColumns(), )
	}

	@Override
	public boolean existsData(String column, Object value) throws SQLException
	{
		// TODO
		return false;
	}
}
