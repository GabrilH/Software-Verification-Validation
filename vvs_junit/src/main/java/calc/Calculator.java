package calc;

// O método evaluate recebe uma string do tipo 1+2+3 e devolve 6
public class Calculator {
	  public int evaluate(String expression) {
	    int sum = 0;
	    for (String summand: expression.split("[+()]+"))
	      sum += Integer.valueOf(summand);
	    return sum;
	  }
	}
