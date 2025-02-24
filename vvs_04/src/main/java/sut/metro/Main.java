package sut.metro;

import java.util.List;

/**
 * Software Verification and Validation
 * 
 * Master of Science in Computer Engineering,
 * University of Lisbon,
 * Faculty of Sciences,
 * Department of Informatics
 * 
 * @author Eduardo Marques
 * @version $Id: Average.java 275 2016-02-28 22:59:09Z vv $
 */
public class Main {
	
	public static void main(String[] args) {
		if (args.length == 0) {
			for (String from : MetroDB.INSTANCE.getAllStations()) {
				printPaths(from);
			}
		} else if (args.length == 1) {
			printPaths(args[0]);
		} else if (args.length == 2) {
			printPath(args[0], args[1]);

		}
	}

	private static void printPaths(String from) {
		for (String to : MetroDB.INSTANCE.getAllStations()) {
			printPath(from, to);
		}
	}

	private static void printPath(String from, String to) {
		System.out.print(from + " -> " + to + " : ");
		List<String> path = MetroDB.INSTANCE.findPath(from, to);
		System.out.println(path);
	}
}
