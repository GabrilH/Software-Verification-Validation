package restTests;

import static org.junit.Assert.*;
import org.junit.*;

import io.restassured.RestAssured;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class TemperatureTest {
	
	@Before
	public void setup() {
		RestAssured.baseURI = "http://localhost:8080/vvs_rest";
	}

	@Test
	public void celsiusTest() {
		get("/services/c2f/10")
		   .then()
		   .body("ctofservice.celsius", equalTo("10.0"));
	}

	@Test
	public void fahrenheitTest() {
		get("/services/c2f/10")
		   .then()
		   .body("ctofservice.fahrenheit", equalTo("50.0"));
	}
}
