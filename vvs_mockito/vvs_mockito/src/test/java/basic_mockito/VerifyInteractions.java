package basic_mockito;

import static org.mockito.Mockito.*;
import java.util.LinkedList;
import java.util.List;

import org.mockito.Mockito;

public class VerifyInteractions {

  public static void main(String[] args) {
    @SuppressWarnings("unchecked")
    List<String> mockedList = (List<String>) Mockito.mock(LinkedList.class);
    
    // Interact with the mock object
    mockedList.set(0,"XPTO");
    mockedList.set(1,"XPTO");
    mockedList.clear();
    
    // Verify interactions
    verify(mockedList).set(0, "XPTO");
    verify(mockedList).set(1, "XPTO");
    verify(mockedList).clear();
    verify(mockedList).size(); // WILL FAIL
  }
}