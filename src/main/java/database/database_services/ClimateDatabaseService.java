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

	public ClimateDatabaseService(Connection connection)
	{
		this.connection = connection;
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

		String query = QueryBuilder.buildAddQuery(roomclimate);

		PreparedStatement pst = connection.prepareStatement(query);

		pst.setObject(1, dataMap.get(roomclimate.getColumns()[0]));
		pst.setFloat(2, (Float) dataMap.get(roomclimate.getColumns()[1]));
		pst.setFloat(3, (Float) dataMap.get(roomclimate.getColumns()[2]));

		pst.executeUpdate();
	}

	@Override
	public HashMap<String, Object> getDatasetById(int id) throws SQLException
	{
		IDatabaseService.checkConnection(connection);

		String query = QueryBuilder.buildGetDatasetByIdQuery(roomclimate, id);

		PreparedStatement pst = connection.prepareStatement(query);

		ResultSet rs = pst.executeQuery();

		HashMap<String, Object> result = new HashMap<>();
		while(rs.next())
		{
			result.put(roomclimate.getPrimaryKey(), rs.getInt(roomclimate.getPrimaryKey()));
			result.put(roomclimate.getColumns()[0], IDatabaseService
					.convertSqlTimestampToJodaDateTime(rs.getTimestamp(roomclimate.getColumns()[0])));
			result.put(roomclimate.getColumns()[1], rs.getFloat(roomclimate.getColumns()[1]));
			result.put(roomclimate.getColumns()[2], rs.getFloat(roomclimate.getColumns()[2]));

			return result;
		}

		return null;
	}

	@Override
	public void deleteData(String valueColumn, Object value) throws SQLException
	{
		IDatabaseService.checkConnection(connection);

		String query = QueryBuilder
				.buildDeleteQuery(roomclimate, valueColumn, value);

		PreparedStatement pst = connection.prepareStatement(query);

		pst.executeUpdate();
	}

	@Override
	public void updateData(HashMap dataMap, int id) throws SQLException
	{
		IDatabaseService.checkConnection(connection);

		String query = QueryBuilder.buildUpdateQuery(roomclimate, id);

		PreparedStatement pst = connection.prepareStatement(query);

		pst.setObject(1, dataMap.get(roomclimate.getColumns()[0]));
		pst.setFloat(2, (Float) dataMap.get(roomclimate.getColumns()[1]));
		pst.setFloat(3, (Float) dataMap.get(roomclimate.getColumns()[2]));

		pst.executeUpdate();
	}

	@Override
	public boolean existsData(int id) throws SQLException
	{
		String query = QueryBuilder.buildExistsDatasetWithIdQuery(roomclimate, id);

		PreparedStatement pst = connection.prepareStatement(query);

		ResultSet rs = pst.executeQuery();

		if(rs.next() && rs.getInt(roomclimate.getPrimaryKey()) != 0)
			return true;

		return false;
	}
}
