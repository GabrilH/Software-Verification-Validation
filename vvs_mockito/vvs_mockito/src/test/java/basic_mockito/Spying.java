package basic_mockito;

import static org.mockito.Mockito.*;
import java.util.LinkedList;

public class Spying {

  public static void main(String[] args) {
    // "Real object"
    LinkedList<String> real = new LinkedList<>();
    // Spy
    LinkedList<String> spy =  (LinkedList<String>) spy(real);   

    // Note: (a bit weird ...) 
    // It will call real methods from LinkedList but over a copy of the original real object.
    spy.add("A"); // calls real method 
    spy.add("B"); // calls real method

    System.out.println(spy.size());
    System.out.println(spy.get(0) + " " + spy.get(1));

    // We can then verify interactions
    verify(spy).add("A");
    verify(spy).add("B");
    verify(spy).size();
    verify(spy).get(0); 
    verify(spy).get(1);
    verify(spy).get(2); // WILL FAIL
    
  }
}