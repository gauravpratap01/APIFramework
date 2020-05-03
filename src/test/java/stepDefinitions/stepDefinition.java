package stepDefinitions;
import static org.junit.Assert.*;
import static io.restassured.RestAssured.given;

import java.io.FileNotFoundException;
import java.io.IOException;

import resources.APIResources;
//import java.util.ArrayList;
//import java.util.List;
//import pojo.AddPlace;
//import pojo.Location;
import resources.TestDataBuild;
import resources.Utils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
//import io.restassured.RestAssured;
//import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class stepDefinition extends Utils {

	RequestSpecification requestSpec;
	ResponseSpecification responseSpec;
	Response response;
	static String Place_Id;
	
	TestDataBuild data=new TestDataBuild();


	@Given("Add Place Payload with {string} {string} {string}")
	public void add_Place_Payload_with(String name, String language, String address) throws IOException {

		requestSpec=given().spec(requestSpecification()).body(data.addPlacePayLoad(name,language,address));
	}



	@When("user calls {string} with {string} http request")
	public void user_calls_with_http_request(String resource, String method) {

		APIResources resourceAPI=APIResources.valueOf(resource);
		System.out.println(resourceAPI.getResource());
		System.out.println(resourceAPI.name().toString());

		responseSpec=new ResponseSpecBuilder().expectStatusCode(200).
				expectContentType(ContentType.JSON).build();

		if(method.equalsIgnoreCase("POST"))
			response=requestSpec.when().post(resourceAPI.getResource());
		else if(method.equalsIgnoreCase("GET"))
			response=requestSpec.when().get(resourceAPI.getResource());

	}



	@Then("the API call got success with Status code {int}")
	public void the_API_call_got_success_with_Status_code(Integer int1) {
		assertEquals(response.getStatusCode(),200);
	}



	@Then("{string} in response body is {string}")
	public void in_response_body_is(String keyValue, String expectedValue) {
		
		assertEquals(getJsonPath(response, keyValue),expectedValue);
	}
	
	@Then("verify place_Id created maps to {string} using {string}")
	public void verify_place_Id_created_maps_to_using(String expectedName, String resource) throws IOException {
		
		Place_Id=getJsonPath(response, "place_id");
		requestSpec=given().spec(requestSpecification()).queryParam("place_id", Place_Id);
		//System.out.println("PlaceID="+Place_Id);
		
		user_calls_with_http_request(resource,"GET");
		
		String actualName=getJsonPath(response, "name");
		assertEquals(actualName,expectedName);		
	}
	
	@Given("DeletePlace Payload")
	public void deleteplace_Payload() throws IOException {
		requestSpec=given().spec(requestSpecification()).body(data.deletePlacePayload(Place_Id));
	}
}
