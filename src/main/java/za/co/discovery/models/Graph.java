package za.co.discovery.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
    public Map<String, Vertex> Graph(List<Edge> edges) {
        final Map<String, Vertex> graph;
        graph = new HashMap<>(edges.size());
        for (Edge e : edges) {
            if (!graph.containsKey(e.getSource())) graph.put(e.getSource(), new Vertex(e.getSource()));
            if (!graph.containsKey(e.getDestination())) graph.put(e.getDestination(), new Vertex(e.getDestination()));
        }

        //another pass to set neighbouring vertices
        for (Edge e : edges) {
            graph.get(e.getSource()).neighbours.put(graph.get(e.getDestination()), e.getDistance());
            //graph.get(e.v2).neighbours.put(graph.get(e.v1), e.dist); // also do this for an undirected graph
        }
        return graph;
    }

//    private List<Vertex> vertices;
//    private List<Edge> edges;
//
//    public Graph(List<Vertex> verticies, List<Edge> edges) {
//        this.vertices = verticies;
//        this.edges = edges;
//    }
//
//    public List<Vertex> getVertices() {
//        return vertices;
//    }
//
//    public List<Edge> getEdges() {
//        return edges;
//    }
}
