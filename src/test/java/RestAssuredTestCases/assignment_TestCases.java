package RestAssuredTestCases;

import static io.restassured.RestAssured.given;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dataproviderexample.valuesfromexcel;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import pojopackage.Category;
import pojopackage.Root;
import pojopackage.Tags;

public class assignment_TestCases {
	
	@DataProvider(name = "valuesfromExcel")
	public Object[][] exceldata() throws IOException {
		Object[][] data = valuesfromexcel.gettestdata();

		return data;

	}
	
	@Test(enabled = false, dataProvider = "valuesfromExcel")
	public void assignment1(String id, String name, String status, String catid, String catname,String tagid, String tagname, String array) throws JsonProcessingException{
		
		RestAssured.baseURI = "http://localhost:3000/";
		Root rb = new Root();
		Category ct = new Category();
		Tags tg = new Tags();
		
		List<String> photoUrlsobject = new ArrayList<String>();
        
        photoUrlsobject.add(array);
        
		
		ct.setId(catid);
		ct.setName(catname);
		
		tg.setId(tagid);
		tg.setName(tagname);
		
		rb.setId(id);
		rb.setCategory(ct);
		rb.setName(name);
		rb.setPhotoUrls(photoUrlsobject);
		rb.setTags(tg);
		rb.setStatus(status);

		ObjectMapper mapperobject = new ObjectMapper();
		
        String jsonbody = mapperobject.writerWithDefaultPrettyPrinter().writeValueAsString(rb);
		System.out.println(rb);
		
		Response obj = given()
		.headers("content-type","application/json")
			.body(jsonbody).
			   when()
			    .post("/details").
			    then()
			        .statusCode(201)
			        .log().all().extract().response();
		
		String resstatus = obj.jsonPath().getString("status");
		System.out.println(resstatus);
		Assert.assertEquals("reject", resstatus);
		
	}
	
	@Test(enabled=true)
	public void soapexample() throws IOException {
        RestAssured.baseURI = "http://www.dneonline.com";
        FileInputStream fis = new FileInputStream(".\\payload\\requestBody.xml");
        given()
            .headers("content-type","text/xml")
            .body(IOUtils.toString(fis, "UTF-8")).
        when()
            .post("/calculator.asmx").
        then()
            .log().all();
    }

}
