package basic_mockito;

import static org.mockito.Mockito.*;
import java.util.List;
import org.mockito.Mockito;

public class MethodStubbing {

  public static void main(String[] args) {
    @SuppressWarnings("unchecked")
    List<String> mockedList = (List<String>) Mockito.mock(List.class);

    // Stub calls to and size() and getInt()
    when(mockedList.size()).thenReturn(3);
    when(mockedList.get(0)).thenReturn("String at pos 0");
    when(mockedList.get(1)).thenReturn("String at pos 1");
    when(mockedList.get(2)).thenReturn("String at pos 2");
    when(mockedList.get(3)).thenThrow(new IndexOutOfBoundsException());
    
    // Interact with the mock object and observe
    // the stubbed behavior being executed
    for (int i=0; i <= mockedList.size(); i++) {
      System.out.println(mockedList.get(i));
    }
  }
}
