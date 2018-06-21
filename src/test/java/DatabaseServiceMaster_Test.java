import database.DatabaseProperties.*;
import database.DatabaseServiceMaster;
import org.joda.time.DateTime;

import java.util.HashMap;

import static database.DatabaseProperties.DB_Table.*;

public class DatabaseServiceMaster_Test
{
	static DatabaseServiceMaster dsm = new DatabaseServiceMaster();

	public static void main(String [] args)
	{
		addData();
	}

	static void addData()
	{
		HashMap<String, Object> climateData = new HashMap<>();
		climateData.put("timestamp", DateTime.now());
		climateData.put("temperature", 33.10f);
		climateData.put("humidity", 99.0f);

		dsm.addData(climateData, roomclimate);
	}
}
