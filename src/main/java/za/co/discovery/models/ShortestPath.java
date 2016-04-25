package za.co.discovery.models;


//import za.co.discovery.services.*;
//import za.co.discovery.services.Vertex;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ShortestPath {
    public void dijkstra(String startName, Map<String, Vertex> graph) {
        if (!graph.containsKey(startName)) {
            System.err.printf("Graph doesn't contain start vertex \"%s\"\n", startName);
            return;
        }
        Vertex source = graph.get(startName);
        NavigableSet<Vertex> setQueues = new TreeSet<>();

        // set-up vertices
        for (Vertex v : graph.values()) {
            if (v == source) v.setPrevious(source);
            else v.setPrevious(null);

            if (v == source) v.setMinDistance(0);
            else v.setMinDistance(Double.POSITIVE_INFINITY);
            setQueues.add(v);
        }

        dijkstra(setQueues);
    }

    private void dijkstra(final NavigableSet<Vertex> queue) {
        Vertex shortestDistanceVertex, vertexUnderTest;
        while (!queue.isEmpty()) {

            shortestDistanceVertex = queue.pollFirst(); // vertex with shortest distance
            if (shortestDistanceVertex.getMinDistance() == Integer.MAX_VALUE)
                break; // we can ignore shortestDistanceVertex (and any other remaining vertices) since they are unreachable

            //look at distances to each neighbour
            for (Map.Entry<Vertex, Double> neighbourValues : shortestDistanceVertex.getNeighbours().entrySet()) {
                vertexUnderTest = neighbourValues.getKey(); //the neighbour in this iteration

                final double alternateDist = shortestDistanceVertex.getMinDistance() + neighbourValues.getValue();
                if (alternateDist < vertexUnderTest.getMinDistance()) { // shorter path to neighbour found
                    queue.remove(vertexUnderTest);
                    vertexUnderTest.setMinDistance(alternateDist);
                    vertexUnderTest.setPrevious(shortestDistanceVertex);
                    queue.add(vertexUnderTest);
                }
            }
        }
    }


    public List<String> printPath(Map<String, Vertex> graph, String endName) {
        if (!graph.containsKey(endName)) {
            System.err.printf("Graph doesn't contain end vertex \"%s\"\n", endName);
        }

        List<String> pathNames = new LinkedList<>();
        if (graph.get(endName) == null) {
            pathNames.add("Cannot Complete request");
            return pathNames;
        } else {
            pathNames = graph.get(endName).printPath();
            return pathNames;
        }
    }
}
