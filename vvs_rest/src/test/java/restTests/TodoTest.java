package restTests;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;

import static org.junit.Assert.*;
import org.junit.*;

import io.restassured.RestAssured;

public class TodoTest {
	
	@Before
	public void setup() {
		RestAssured.baseURI = "http://localhost:8080/vvs_rest";
	}

	@Test
	public void insertTodoTest() {
		// insert a todo
		given().param("id", "5")
		       .param("summary", "summary")
		       .param("description", "descriptionXPTO")
               .when()
               .post("/services/todos");
		
		// check if it's there
		get("/services/todos")
		   .then()
		   .body("todoElements.todoElement.description", hasItems("descriptionXPTO"));
		
		// The todo should be removed to keep the todo list as it was.
		// Move the todo inserts into section @Before and the removals to section @After
		// (after the remove service is implemented, of course)
	}

}
