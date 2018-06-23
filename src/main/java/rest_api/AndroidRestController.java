package rest_api;

import database.DatabaseServiceMaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class AndroidRestController
{
	@Autowired
	DatabaseServiceMaster databaseService = new DatabaseServiceMaster();

	private final String baseUrl = "/mobile";

	@RequestMapping(value = baseUrl + "/climatedata", method = RequestMethod.GET)
	public ResponseEntity<?> getClimatedata()
	{
		return null;
	}
}
