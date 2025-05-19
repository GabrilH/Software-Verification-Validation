package todo;

import java.util.HashMap;
import java.util.Map;

/**
 * Data Access Object singleton class, serves as a data provider of TodoElement's
 * 
 * @author jpn
 *
 */
public enum TodoDAO {
    INSTANCE; 

    private Map<String, TodoElement> contentProvider = new HashMap<>();
    
    private TodoDAO() {
    	
    	// init some Todo's

    	TodoElement todoElement = new TodoElement("1", "Learn REST");
        todoElement.setDescription("Read http://www.vogella.com/tutorials/REST/article.html");
        contentProvider.put("1", todoElement);
        
        todoElement = new TodoElement("2", "Learn Java and XML");
        todoElement.setDescription("Read http://www.vogella.com/tutorials/JavaXML/article.html");
        contentProvider.put("2", todoElement);

    }
    
    public Map<String, TodoElement> getModel(){
        return contentProvider;
    }

}