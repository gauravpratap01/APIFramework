package stepDefinitions;
import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {

	@Before("@DeletePlace")
	public void beforeScenario() throws IOException
	{
		stepDefinition m=new stepDefinition();
		
		//Execute this code when Place ID is null		
		if(stepDefinition.Place_Id==null)
		{
		
		m.add_Place_Payload_with("Shetty", "French", "Asia");
		m.user_calls_with_http_request("addPlaceAPI", "POST");
		m.verify_place_Id_created_maps_to_using("Shetty", "getPlaceAPI");
		}
	}
}
