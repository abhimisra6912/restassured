package RestAssuredTestCases;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class RestTestCase {
	
	@Test
	public void testcase()
	{
		RestAssured.baseURI = "https://petstore.swagger.io/v2";
		given().header("content-type","Application/JSON").param("status", "available")
			.get("/pet/findByStatus")
			.then().body("name",hasItems("Doggie","test","fish")).log().status();
		
		/*String abc ="{\"id\":0,\"username\":\"string\",\"firstName\":\"string\",\"lastName\":\"string\",\"email\":\"string\",\"password\":\"string\",\"phone\":\"string\",\"userStatus\":0}";
		
		Response resp = given().header("content-type","Application/JSON").body(abc)
		.post("/user")
		.then().log().all().extract().response();
		String code = resp.jsonPath().getString("type");
		System.out.println("hello" + code);*/
		
	}
	
	

}
