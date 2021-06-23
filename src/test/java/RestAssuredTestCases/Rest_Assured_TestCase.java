package RestAssuredTestCases;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class Rest_Assured_TestCase {
	
	
	@Test
	public void firsttest()
	{
		Response resp = RestAssured.get("https://petstore.swagger.io/v2/pet/922296814049804047");
		
		//print the response code
		System.out.println("Response Code => "+resp.statusCode());
		
		//print the Response Body
		System.out.println("Response Body => "+resp.asString());
		
		//print the Status Line
		System.out.println("Response Status Line => "+resp.statusLine());
		
		
		//Print Response body in Pretty Format
		System.out.println("Response in Pretty Format => "+resp.prettyPrint());
				
		
	}

}
