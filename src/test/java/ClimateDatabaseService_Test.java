import database.DatabaseProperties.*;
import database.DatabaseServiceMaster;
import database.IDatabaseService;
import org.joda.time.DateTime;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import static database.DatabaseProperties.DB_Table.*;

public class ClimateDatabaseService_Test
{
	static DatabaseServiceMaster dsm = new DatabaseServiceMaster();

	public static void main(String [] args)
	{
		addData();
		System.out.println(existsDataWithId(916) ? "existiert" : "existiert nicht");
		getDatasetById(916);
		updateData(916);
		//deleteData(928);
	}

	static void addData()
	{
		HashMap<String, Object> climateData = new HashMap<>();
		climateData.put("timestamp", IDatabaseService.convertJodaDateTimeToSqlTimestamp(DateTime.now()));
		climateData.put("temperature", 33.10f);
		climateData.put("humidity", 99.0f);

		try
		{
			dsm.addData(climateData, roomclimate);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	static void getDatasetById(int id)
	{
		HashMap<String, Object> climateData = null;
		try
		{
			climateData = dsm.getDatasetById(id, roomclimate);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		for(Map.Entry<String, Object> entry: climateData.entrySet())
		{
			System.out.println(entry.getKey() + "\t" + ((entry.getValue() == "timestamp") ?
					IDatabaseService.convertSqlTimestampToJodaDateTime((Timestamp) entry.getValue()).toString() : entry.getValue()));
		}
	}

	static void deleteData(int value)
	{
		try
		{
			dsm.deleteData("id", value, roomclimate);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	static void updateData(int id)
	{
		HashMap<String, Object> climateData = new HashMap<>();
		climateData.put("timestamp", IDatabaseService.convertJodaDateTimeToSqlTimestamp(DateTime.now()));
		climateData.put("temperature", 191919.0f);
		climateData.put("humidity", 191919.0f);

		try
		{
			dsm.updateData(climateData, id, roomclimate);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	static boolean existsDataWithId(int id)
	{
		try
		{
			return dsm.existsData(id, roomclimate);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}
}
