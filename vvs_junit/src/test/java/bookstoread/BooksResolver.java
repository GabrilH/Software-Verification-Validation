package bookstoread;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.lang.reflect.Parameter;
import java.util.*;

/**
 * JUnit 5 introduced the concept of ParameterResolver, which provides an API
 * to resolve parameters at runtime. You can either use a built-in parameter resolver
 * like TestInfoParameterResolver or provide your own resolver by implementing
 * ParameterResolver interface. ParameterResolver is part of the JUnit 5 extension
 * mechanism, which we will cover in a chapter 8. To make your test aware of your own
 * custom resolver, you should annotate your test class with the ExtendWith annotation
 */
public class BooksResolver implements ParameterResolver {

	/**
	 * supportsParameter method validates if the implementation 
	 * can provide the resolution for the asked parameter. 
	 * The BooksParameterResolver needs to validate that it supports
	 * objects of type Map<String, Book>.
	 */
	@Override
	public boolean supportsParameter(final ParameterContext parameterContext, 
			                         final ExtensionContext extensionContext) 
					                     throws	ParameterResolutionException {
		
		Parameter parameter = parameterContext.getParameter();
		return Objects.equals(parameter.getParameterizedType().getTypeName(), 
				              "java.util.Map<java.lang.String, bookstoread.Book>");
	}

	/**
	 * resolveParameter method returns the value for the asked parameter. 
	 * The BooksParameterResolver returns a Map containing books.
	 */
	@Override
	public Object resolveParameter(final ParameterContext parameterContext,
			                       final ExtensionContext extensionContext) 
			                    		throws ParameterResolutionException {
		
		Map<String, Book> books = new HashMap<>();
		
		books.put("Effective Java", new Book("Effective Java"));
		books.put("Code Complete", new Book("Code Complete"));
		books.put("The Mythical Man-Month", new Book("The Mythical Man-Month"));
		books.put("Clean Code", new Book("Clean Code"));
		books.put("Refactoring", new Book("Refactoring"));
		
		return books;
	}
}
