package sut.metro;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

/**
 * Software Verification and Validation
 * 
 * Master of Science in Computer Engineering,
 * University of Lisbon,
 * Faculty of Sciences,
 * Department of Informatics
 * 
 * @author Eduardo Marques
 * @version $Id: MetroDB.java 313 2016-03-21 12:46:58Z vv $
 */
public enum MetroDB {
	/**
	 * Singleton instance.
	 */
	INSTANCE;

	/**
	 * Set of all stations connections.
	 */
	private final Set<String> allStations;

	/**
	 * Direct connections.
	 */
	private final HashMap<String, LinkedList<String>> connections = new HashMap<>();

	/**
	 * Constructs a Metro DB.
	 */
	private MetroDB() {
		TreeSet<String> set = new TreeSet<>();
		Arrays.asList(MetroLine.values()).forEach(line -> set.addAll(line.getStations()));
		allStations = Collections.unmodifiableSet(set);
		
		Arrays.asList(MetroLine.values()).forEach(line -> {
			List<String> l = line.getStations();
			String stationA = l.get(0);
			for (int i = 1; i < l.size(); i++) {
				String stationB = l.get(i);
				addConnection(stationA, stationB);
				addConnection(stationB, stationA);
				stationA = stationB;
			}
		});
	}

	/**
	 * Add a connection between stations.
	 * 
	 * @param src
	 *            Source station.
	 * @param dst
	 *            Destination station.
	 */
	private void addConnection(String src, String dst) {
		LinkedList<String> list = connections.get(src);
		if (list == null) {
			list = new LinkedList<>();
			connections.put(src, list);
		}
		list.add(dst);
	}

	/**
	 * Get all stations.
	 * 
	 * @return A set of all stations.
	 */
	public Set<String> getAllStations() {
		return allStations;
	}

	/**
	 * Check if station is valid.
	 * 
	 * @param station
	 *            Station name.
	 * @return True if station name is valid.
	 */
	public boolean isValidStation(String station) {
		return connections.containsKey(station);
	}

	/**
	 * Check if a station is terminal.
	 * 
	 * @param station
	 *            Station name.
	 * @return Boolean value indicating if station is terminal.
	 */
	public static boolean isTerminalStation(String station) {
		for (MetroLine line : MetroLine.values()) {
			if (station.equals(line.getFirstStation()) || station.equals(line.getLastStation())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Find a path between two stations. The path is guaranteed to be the of the
	 * shortest paths or one of the shortest paths.
	 * 
	 * @param src
	 *            Source station.
	 * @param dst
	 *            Destination station.
	 * @return A list of stations starting at <code>src</code> and endingin at
	 *         <code>dst</code>, a list formed by <code>src</code> if
	 *         <code>src.equals(dst)</code> or an empty list if the stations are
	 *         invalid or the path cannot be found.
	 */
	public List<String> findPath(String src, String dst) {
		if (!isValidStation(src) || !isValidStation(dst)) {
			return Collections.emptyList();
		}

		Queue<LinkedList<String>> queue = new ArrayDeque<>();

		LinkedList<String> startPath = new LinkedList<>();
		startPath.add(src);
		queue.offer(startPath);

		while (!queue.isEmpty()) {
			LinkedList<String> path = queue.remove();
			String current = path.getLast();
			if (current.equals(dst)) {
				queue.clear();
				return path;
			}

			for (String next : connections.get(current)) {
				if (!path.contains(next)) {
					LinkedList<String> newL = new LinkedList<String>(path);
					newL.addLast(next);
					queue.offer(newL);
				}
			}
		}
		// Path not found: this should never happen as the network is connected!
		return Collections.emptyList();
	}
}
