Feature: Validating Place API's

@AddPlace @Regression
Scenario Outline: Verify if Place is being successfully added using AddPlaceAPI
	Given Add Place Payload with "<name>" "<language>" "<address>"
	When user calls "addPlaceAPI" with "POST" http request
	Then the API call got success with Status code 200
	And "status" in response body is "OK"
	And "scope" in response body is "APP"
	And verify place_Id created maps to "<name>" using "getPlaceAPI"
	
Examples:
	|  name  |  language  |     address      |
	|Gaurav	 |  English   |World Cross Center|
	|Suman	 |  Spanish   |Sea Cross Center|


@DeletePlace @Regression
Scenario: Verify if Delete Place funcionality is working
Given DeletePlace Payload
When user calls "deletePlaceAPI" with "POST" http request
Then the API call got success with Status code 200
And "status" in response body is "OK"
