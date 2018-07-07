package rest_api;

import database.DatabaseServiceMaster;
import database.IDatabaseService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;

import static database.DatabaseProperties.DB_Table.roomclimate;

@Controller
public class NodeMCURestController
{
	DatabaseServiceMaster databaseServiceMaster = new DatabaseServiceMaster();

	private final String url = "/controller";

	@RequestMapping(value = url + "/test", method = RequestMethod.GET)
	public ResponseEntity<?> test()
	{
		return new ResponseEntity("test successful!", HttpStatus.OK);
	}

	@RequestMapping(value = url + "/addClimateData", method = RequestMethod.POST)
	 public ResponseEntity<?> addData(@RequestParam("temperature") Float temperature,
									  @RequestParam("humidity") Float humidity)
	{
		try
		{
			HashMap<String, Object> dataMap = new HashMap<>();

			dataMap.put(roomclimate.getColumns()[0], IDatabaseService.convertJodaDateTimeToSqlTimestamp(DateTime.now()));
			dataMap.put(roomclimate.getColumns()[1], temperature);
			dataMap.put(roomclimate.getColumns()[2], humidity);

			databaseServiceMaster.addData(dataMap, roomclimate);
			return new ResponseEntity(HttpStatus.OK);
		} catch (SQLException e)
		{
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.CONFLICT);
		}
	}
}
