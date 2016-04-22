package za.co.discovery.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
    public Map<String, Vertex> GraphEdge(List<Edge> edges) {
        final Map<String, Vertex> graph;
        graph = new HashMap<>(edges.size());
        for (Edge e : edges) {
            if (!graph.containsKey(e.getSource().getNode()))
                graph.put(e.getSource().getNode(), new Vertex(e.getSource().getNode()));
            if (!graph.containsKey(e.getDestination().getNode()))
                graph.put(e.getDestination().getNode(), new Vertex(e.getDestination().getNode()));
        }

        //another pass to set neighbouring vertices
        for (Edge e : edges) {
            graph.get(e.getSource().getNode()).neighbours.put(graph.get(e.getDestination().getNode()), e.getDistance());
            //graph.get(e.v2).neighbours.put(graph.get(e.v1), e.dist); // also do this for an undirected graph
        }
        return graph;
    }

    public Map<String, Vertex> GraphTraffic(List<Traffic> traffic) {
        final Map<String, Vertex> graph;
        graph = new HashMap<>(traffic.size());
        for (Traffic e : traffic) {
            if (!graph.containsKey(e.getSource().getNode()))
                graph.put(e.getSource().getNode(), new Vertex(e.getSource().getNode()));
            if (!graph.containsKey(e.getDestination().getNode()))
                graph.put(e.getDestination().getNode(), new Vertex(e.getDestination().getNode()));
        }

        //another pass to set neighbouring vertices
        for (Traffic e : traffic) {
            graph.get(e.getSource().getNode()).neighbours.put(graph.get(e.getDestination().getNode()), e.getDistance());
            //graph.get(e.v2).neighbours.put(graph.get(e.v1), e.dist); // also do this for an undirected graph
        }
        return graph;
    }

}
