package RestAssuredTestCases;

import static io.restassured.RestAssured.given;

import java.io.FileInputStream;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dataproviderexample.valuesfromexcel;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pojopackage.pojoclass;

import java.io.IOException;
//import org.apache.commons.io.IOUtils;
import static org.hamcrest.Matchers.*;

public class RestAssuredBasics {

	@Test(enabled = false)
	public void firsttest() {

		Response obj = RestAssured.get("https://petstore.swagger.io/v2/pet/9223127516080556901");

		// Print Status code
		System.out.println("Status code of my request is " + obj.getStatusCode());

		// Print the Raw Response body
		System.out.println("Response Body " + obj.asString());

		// Print the Response Status line
		System.out.println("Response status line " + obj.statusLine());

		// Print the Response in pretty Format
		System.out.println("Response body in pretty format " + obj.prettyPrint());

	}

	@Test(enabled = false)
	public void secondtest(ITestContext abc) {

		RestAssured.baseURI = "https://petstore.swagger.io/v2/";
		abc.setAttribute("statuscode", "200");
		given().delete("pet/9223127516080556901").then().statusCode(404);
	}

	@Test(enabled = false)
	public void thirdtest1(ITestContext obj) {

		RestAssured.baseURI = "https://petstore.swagger.io/v2/";
		String statuscode = obj.getAttribute("statuscode").toString();
		System.out.println(statuscode);
		given().delete("pet/9223127516080556901").then().statusCode(404);
	}

	@Test(enabled = false)
	public void getmethod() {

		RestAssured.baseURI = "http://localhost:3000/";

		given().get("students").then().statusCode(200).log().all();

	}

	@Test(enabled = false)
	public void postmethod() {

		RestAssured.baseURI = "http://localhost:3000/";
		String reqbody = "{\"stream\":\"science\",\"firstname\":\"preeti\",\"lastname\":\"sharma\"}";

		Response resp = given().headers("Content-Type", "Application/JSON").body(reqbody).when().post("students").then()
				.extract().response();

		String actualstream = resp.jsonPath().getString("stream");

		Assert.assertEquals("science", actualstream);

	}

	@Test(enabled = false)
	public void putmethod() {

		RestAssured.baseURI = "http://localhost:3000/";
		String reqbody = "{\"stream\":\"science\",\"firstname\":\"radhika\",\"lastname\":\"mishra\"}";

		given().headers("Content-Type", "Application/JSON").body(reqbody).when().put("students/2").then()
				.statusCode(200).log().all();

	}

	@Test(enabled = false)
	public void patchmethod() {

		RestAssured.baseURI = "http://localhost:3000/";
		String reqbody = "{\"stream\":\"commerce\"}";

		given().headers("Content-Type", "Application/JSON").body(reqbody).when().patch("students/2").then()
				.statusCode(200).log().all();

	}

	@Test(enabled = false)
	public void deletemethod() {

		RestAssured.baseURI = "http://localhost:3000/";

		given().delete("students/4001").then().log().all();

	}

	@Test(enabled = false)
	public void jsonbody() {

		RestAssured.baseURI = "http://localhost:3000/";
		JSONObject rootbodyobject = new JSONObject();
		JSONObject categoryobject = new JSONObject();
		JSONObject tagsobject = new JSONObject();
		rootbodyobject.put("id", 0);
		rootbodyobject.put("name", "amit");
		rootbodyobject.put("status", "available");

		String name = "vineeta";
		categoryobject.put("id", 0);
		categoryobject.put("name", "abcd");

		tagsobject.put("id", 0);
		tagsobject.put("name", name);

		// Adding the Category and Tags object into the Rootbody
		rootbodyobject.put("category", categoryobject);
		rootbodyobject.put("tags", tagsobject);

		// JSON Array Body
		JSONArray arraybody = new JSONArray();
		arraybody.add("abc");
		arraybody.add("values1");

		// Adding the Arrayobject into Root body
		rootbodyobject.put("photoUrls", arraybody);

		System.out.println(rootbodyobject);

		/*
		 * given() .body(obj.toJSONString())
		 * .headers("content-type","Application/JSON"). when()
		 * .post("students"). then() .log() .all();
		 */

		// given()
		// .delete("students/4001").
		// then()
		// .log().all();
		//
	}

	@Test(enabled = false)
	public void fetchjsonfile() throws IOException {

		RestAssured.baseURI = "http://localhost:3000/";

		FileInputStream fis = new FileInputStream(".\\Payload\\postrequestbody.json");

		/*
		 * given() .headers("content-type","Application/json").headers("","")
		 * .body(IOUtils.toString(fis,"UTF-8")). when() .post("students").
		 * then() .body("lastname",equalTo("luthra")).log().all();
		 */

	}

	@Test(enabled = false)
	public void pojoexample() throws JsonProcessingException {

		RestAssured.baseURI = "http://localhost:3000/";

		pojoclass pojoobject = new pojoclass();

		pojoobject.setStream("science");
		pojoobject.setFirstname("amit");
		pojoobject.setLastname("luthra");

		// WE need to map our java object to JSON String

		System.out.println(pojoobject.getFirstname().toString());

		ObjectMapper obj = new ObjectMapper(); // we need to use
												// com.fasterxml.jackson.databind.ObjectMapper;

		String reqbody = obj.writerWithDefaultPrettyPrinter().writeValueAsString(pojoobject);

		System.out.println(reqbody);

		given().contentType(ContentType.JSON).body(reqbody).when().post("students").then().statusCode(201).log().all();

	}

	@Test(enabled = false)
	public void queryparameterexample() {

		RestAssured.baseURI = "https://petstore.swagger.io/v2/";

		given().param("status", "sold").log().all().when().get("pet/findByStatus").then().statusCode(200).log().all();

	}

	@Test(enabled = false, dataProvider = "valuesfromExcel")
	public void datasourceexample(String firstname, String lastname, String stream) {
		RestAssured.baseURI = "http://localhost:3000/";
		JSONObject rootbodyobject = new JSONObject();

		rootbodyobject.put("firstname", firstname);
		rootbodyobject.put("lastname", lastname);
		rootbodyobject.put("stream", stream);

		given().body(rootbodyobject.toJSONString()).headers("content-type", "Application/JSON").when().post("students")
				.then().log().all();

	}

	@DataProvider(name = "sample1")
	public Object[][] data() {
		Object[][] data = new Object[2][3];
		data[0][0] = "amit";
		data[0][1] = "luthra";
		data[0][2] = "science";
		data[1][0] = "preeti";
		data[1][1] = "sharma";
		data[1][2] = "commerce";

		return data;

	}

	@DataProvider(name = "valuesfromExcel")
	public Object[][] exceldata() throws IOException {
		Object[][] data = valuesfromexcel.gettestdata();

		return data;

	}

	@Test
	public void soapexample() throws IOException {
		RestAssured.baseURI = "http://www.dneonline.com";

		FileInputStream fis = new FileInputStream(".\\Payload\\addreq.xml");

		given()
			.headers("content-type","text/xml")
			.body(IOUtils.toString(fis, "UTF-8")).
		when()
			.post("/calculator.asmx").
		then()
			.log().all();
	}

}
