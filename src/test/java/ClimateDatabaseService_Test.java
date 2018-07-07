import database.DatabaseProperties.*;
import database.DatabaseServiceMaster;
import database.IDatabaseService;
import org.joda.time.DateTime;

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

		dsm.addData(climateData, roomclimate);
	}

	static void getDatasetById(int id)
	{
		HashMap<String, Object> climateData = dsm.getDatasetById(id, roomclimate);

		for(Map.Entry<String, Object> entry: climateData.entrySet())
		{
			System.out.println(entry.getKey() + "\t" + ((entry.getValue() == "timestamp") ?
					IDatabaseService.convertSqlTimestampToJodaDateTime((Timestamp) entry.getValue()).toString() : entry.getValue()));
		}
	}

	static void deleteData(int value)
	{
		dsm.deleteData("id", value, roomclimate);
	}

	static void updateData(int id)
	{
		HashMap<String, Object> climateData = new HashMap<>();
		climateData.put("timestamp", IDatabaseService.convertJodaDateTimeToSqlTimestamp(DateTime.now()));
		climateData.put("temperature", 191919.0f);
		climateData.put("humidity", 191919.0f);

		dsm.updateData(climateData, id, roomclimate);
	}

	static boolean existsDataWithId(int id)
	{
		return dsm.existsData(id, roomclimate);
	}
}
