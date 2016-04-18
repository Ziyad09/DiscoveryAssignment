package za.co.discovery.models;


//import za.co.discovery.services.*;
//import za.co.discovery.services.Vertex;

import java.util.LinkedList;
import java.util.Map;
import java.util.NavigableSet;
import java.util.TreeSet;

public class ShortestPath {
    public void dijkstra(String startName, Map<String, Vertex> graph) {
        if (!graph.containsKey(startName)) {
            System.err.printf("Graph doesn't contain start vertex \"%s\"\n", startName);
            return;
        }
        Vertex source = graph.get(startName);
        NavigableSet<Vertex> q = new TreeSet<>();

        // set-up vertices
        for (Vertex v : graph.values()) {
            v.previous = v == source ? source : null;
            v.minDistance = v == source ? 0 : Double.POSITIVE_INFINITY;
            q.add(v);
        }

        dijkstra(q);
    }

    private void dijkstra(final NavigableSet<Vertex> queue) {
        Vertex shortestDistanceVertex, vertexUnderTest;
        while (!queue.isEmpty()) {

            shortestDistanceVertex = queue.pollFirst(); // vertex with shortest distance (first iteration will return source)
            if (shortestDistanceVertex.minDistance == Integer.MAX_VALUE)
                break; // we can ignore u (and any other remaining vertices) since they are unreachable

            //look at distances to each neighbour
            for (Map.Entry<Vertex, Double> neighbourValues : shortestDistanceVertex.neighbours.entrySet()) {
                vertexUnderTest = neighbourValues.getKey(); //the neighbour in this iteration

                final double alternateDist = shortestDistanceVertex.minDistance + neighbourValues.getValue();
                if (alternateDist < vertexUnderTest.minDistance) { // shorter path to neighbour found
                    queue.remove(vertexUnderTest);
                    vertexUnderTest.minDistance = alternateDist;
                    vertexUnderTest.previous = shortestDistanceVertex;
                    queue.add(vertexUnderTest);
                }
            }
        }
    }

    public LinkedList<String> printPath(Map<String, Vertex> graph, String endName) {
        if (!graph.containsKey(endName)) {
            System.err.printf("Graph doesn't contain end vertex \"%s\"\n", endName);
        }

        LinkedList<String> pathNames = new LinkedList<>();
        if (graph.get(endName) == null) {
            pathNames.add("Cannot Complete request");
            return pathNames;
        } else {
            pathNames = graph.get(endName).printPath();
            return pathNames;
        }
    }
}
