package rest_api;

import database.DatabaseProperties.DB_Table;
import database.DatabaseServiceMaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;

import static database.DatabaseProperties.DB_Table.roomclimate;

public class NodeMCURestController
{
	@Autowired
	DatabaseServiceMaster databaseServiceMaster = new DatabaseServiceMaster();

	@RequestMapping(value = "/controller/{db_table}", method = RequestMethod.POST)
	 public ResponseEntity<?> addData(@PathVariable("db_table") String db_table,
									  // TODO Überprüfen, ob HashMap mit NodeMCU übertragbar ist
									  @RequestBody HashMap<String, Object> dataMap)
	{
		DB_Table dbt = null;
		switch(db_table)
		{
			case "roomclimate": dbt = roomclimate;
				break;
		}

		databaseServiceMaster.addData(dataMap, dbt);
		return new ResponseEntity(HttpStatus.OK);
	}
}
