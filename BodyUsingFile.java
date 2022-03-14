package pack;

import org.apache.http.HttpStatus;


import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class BodyUsingFile
{
	RequestSpecification requestSpecification;
	ResponseSpecification responseSpecification;
	
	@BeforeTest
	public void step()
	{
		RestAssured.baseURI="https://petstore.swagger.io";
		RestAssured.basePath="/v2";
		requestSpecification=RestAssured.given();
		requestSpecification.header("accept","application/json");
		requestSpecification.header("Content-Type","application/json");
		requestSpecification.log().all();
		responseSpecification=requestSpecification.expect();
	     responseSpecification.log().all();
	}
	@Test
public void testOrderSuccesfullyPlaced()
{
	
   
	
	
	String requestBody=ReadFileDetails.loadJsonFile("order.json");
	requestSpecification.body(requestBody);
	Response response=requestSpecification.post("/store/order");
	//Assert.assertEquals(response.getStatusCode(),HttpStatus.SC_OK);
	JsonPath jsonPath=response.jsonPath();
	Assert.assertEquals(jsonPath.getInt("id"),3);
	Assert.assertEquals(jsonPath.getInt("petId"), 9);
	Assert.assertEquals(jsonPath.getString("status"),"placed");
	Assert.assertEquals(jsonPath.getInt("quantity"),34);
	Assert.assertEquals(jsonPath.getString("shipDate"), "2022-02-20T00:00:00.000+0000");
	Assert.assertEquals(jsonPath.getBoolean("complete"), true);
	
	
	
	//System.out.println(response.asString());
}
	@Test
	public void testOrderSucccesfullyNotPlaced()
	{

		
		String requestBody=ReadFileDetails1.loadJsonFile("order_not_placed");
		requestSpecification.body(requestBody);
		Response response=requestSpecification.post("/store/order");
		Assert.assertEquals(response.getStatusCode(),HttpStatus.SC_BAD_REQUEST);
	}
	
	
	
	
	
}
