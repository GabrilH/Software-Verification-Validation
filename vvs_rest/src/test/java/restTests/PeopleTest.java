package restTests;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.junit.*;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;
//import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class PeopleTest {
	
	@Before
	public void setup() {
		RestAssured.baseURI = "http://localhost:8080/vvs_rest";
	}

	@Test
	public void pingTest() {
	    when().request("GET", "/services/people?id=")
		      .then()
		      .assertThat()
		      .statusCode(200);
	}

	@Test
	public void xmlTest() {
		given().param("id", "joao")
	       .when()
	       .get("/services/people")
	       .then()
		   .assertThat()
		   .contentType(ContentType.XML);
	}

	@Test
	public void responseTimeTest() {
		given().param("id", "joao")
	       .when()
	       .get("/services/people")
	       .then()
		   .time(lessThan(1000L), TimeUnit.MILLISECONDS);
	}

	@Test
	public void firstNameTest() {
		given().param("id", "joao")
		       .when()
		       .get("/services/people")
		       .then()
		       .body("info.firstname", equalTo("joao"));
	}
	
	@Test
	public void namesTest() {
		given().param("id", "joao")
		       .when()
		       .get("/services/people")
		       .then()
		       .root("info")  // no need to repeat tag info below
		       .body("firstname", equalTo("joao"))
		       .body("lastname",  equalTo("neto"));
	}

	@Test
	public void hobbiesTest() {
		given().param("id", "joao")
		       .when()
		       .get("/services/people")
		       .then()
		       .body("info.hobbies.hobby", hasItems("movies", "reading"));
	}
	
	@Test
	public void hobbiesCountTest() {
		given().param("id", "joao")
	       .when()
	       .get("/services/people")
	       .andReturn()
	       .xmlPath()
	       .getNode("//hobby")
	       .equals(3); // it should produce three hobbies
	}
}
