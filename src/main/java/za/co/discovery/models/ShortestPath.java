package za.co.discovery.models;


//import za.co.discovery.services.*;
//import za.co.discovery.services.Vertex;

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

    private void dijkstra(final NavigableSet<Vertex> q) {
        Vertex u, v;
        while (!q.isEmpty()) {

            u = q.pollFirst(); // vertex with shortest distance (first iteration will return source)
            if (u.minDistance == Integer.MAX_VALUE)
                break; // we can ignore u (and any other remaining vertices) since they are unreachable

            //look at distances to each neighbour
            for (Map.Entry<Vertex, Double> a : u.neighbours.entrySet()) {
                v = a.getKey(); //the neighbour in this iteration

                final double alternateDist = u.minDistance + a.getValue();
                if (alternateDist < v.minDistance) { // shorter path to neighbour found
                    q.remove(v);
                    v.minDistance = alternateDist;
                    v.previous = u;
                    q.add(v);
                }
            }
        }
    }

    public void printPath(Map<String, Vertex> graph, String endName) {
        if (!graph.containsKey(endName)) {
            System.err.printf("Graph doesn't contain end vertex \"%s\"\n", endName);
            return;
        }

        graph.get(endName).printPath();
        System.out.println();
    }





    /*
    public static void computePaths(Vertex source) {
        source.minDistance = 0.;
        PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
        vertexQueue.add(source);
        while (!vertexQueue.isEmpty()) {
            Vertex sourceVertex = vertexQueue.poll();
            for (Edge neighbourEdges : sourceVertex.adjacency)
            {
                Vertex v = neighbourEdges.getTarget();
                double weight = neighbourEdges.getDistance();
                double distanceThroughNeighbourEdges = sourceVertex.minDistance + weight;

                if (distanceThroughNeighbourEdges < v.minDistance) {
                    vertexQueue.remove(v);
                    v.minDistance = distanceThroughNeighbourEdges ;
                    v.previous = sourceVertex;
                    vertexQueue.add(v);
                }
            }

        }
    }
    public static List<Vertex> getShortestPathTo(Vertex target)
    {
        List<Vertex> path = new ArrayList<Vertex>();
        for (Vertex vertex = target; vertex != null; vertex = vertex.previous)
            path.add(vertex);

        Collections.reverse(path);
        return path;
    }
*/
}
