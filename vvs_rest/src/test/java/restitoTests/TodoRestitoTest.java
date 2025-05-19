package restitoTests;

import static org.junit.Assert.*;
import org.junit.*;

import io.restassured.RestAssured;
import static org.hamcrest.Matchers.equalTo;

import org.glassfish.grizzly.http.Method;
import org.glassfish.grizzly.http.util.HttpStatus;
import static com.xebialabs.restito.builder.stub.StubHttp.whenHttp;
import static com.xebialabs.restito.semantics.Action.*;
import static com.xebialabs.restito.semantics.Condition.*;
import com.xebialabs.restito.server.StubServer;
import static com.xebialabs.restito.builder.verify.VerifyHttp.*;

public class TodoRestitoTest {

	private static StubServer server;
	
	private static String todoEg = 
	  "<todoElement>"
	  + "<id>3</id>"
	  + "<summary>ArticleX</summary>"
	  + "<description>Read article.html</description>"
	  + "</todoElement>";
	
    @Before
    public void start() {
        server = new StubServer().run();
        RestAssured.port = server.getPort();
        
        whenHttp(server)
          .match(get("/services/todos/check"), parameter("id", "3"))
          .then(status(HttpStatus.OK_200), contentType("application/xml"), stringContent(todoEg));
    }

    @Test
    public void todoTest() {
    	RestAssured
    	       .given()
    	       .param("id", "3")
               .when()
               .get("/services/todos/check")
    	       .then()
    	       .body("todoElement.summary", equalTo("ArticleX"));
    	
    	// verify that the GET request has happened just once
    	verifyHttp(server)
    	   .once(method(Method.GET),
    	         uri("/services/todos/check"),
    	         parameter("id", "3")
    	    );
    }
    
    @After
    public void stop() {
        server.stop();
    }

}
