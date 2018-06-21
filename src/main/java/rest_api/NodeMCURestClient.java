package rest_api;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

public class NodeMCURestClient
{
	private final String nodemcu_url = "http://192.168.0.30";

	// TODO
	public HashMap<String, Float> getClimate()
	{
		RestTemplate restTemplate = new RestTemplate();

		try {
			ResponseEntity<HashMap<String, Float>> responseEntity =
					restTemplate.exchange(
							nodemcu_url + "/climate", HttpMethod.GET,
							null, new ParameterizedTypeReference<HashMap<String, Float>>(){}
					);
			return responseEntity.getBody();
		} catch(HttpClientErrorException e) {
			System.out.println("Error");
		}
		return null;
	}


}
