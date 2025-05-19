package todo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/todos")
public class AddTodoService {
	
    // Return the list of todos to the user in the browser
    @GET
    @Produces(MediaType.TEXT_XML)
    public List<TodoElement> getTodosBrowser() {
        List<TodoElement> todos = new ArrayList<>();
        todos.addAll(TodoDAO.INSTANCE.getModel().values());
        return todos;
    }

    // retuns the number of todoElements
    // Use http://localhost:8080/vvs_rest/services/todos/count
    // to get the total number of records
    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String getCount() {
        int count = TodoDAO.INSTANCE.getModel().size();
        return String.valueOf(count);
    }

    // processing form createTodo.html
    @POST
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void newTodo(@FormParam("id")          String id,
                        @FormParam("summary")     String summary,
                        @FormParam("description") String description,
                        @Context HttpServletResponse servletResponse) 
                throws IOException {

    	TodoElement todo = new TodoElement(id, summary);
        if (description != null)
            todo.setDescription(description);
        TodoDAO.INSTANCE.getModel().put(id, todo);

        servletResponse.sendRedirect("../createTodo.html");
	}
    
    // get a specific todoElement
    @Path("check")
    @GET
    @Produces(MediaType.TEXT_XML)
    public TodoElement getTodo(@QueryParam("id") String id) {
    	TodoElement todo = TodoDAO.INSTANCE.getModel().get(id);
        if(todo==null)
            todo = new TodoElement("0", "Not Found!");
        return todo;
    }
}
