package za.co.discovery.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import za.co.discovery.models.Edge;
import za.co.discovery.models.Graph;
import za.co.discovery.models.Vertex;
import za.co.discovery.services.EdgesService;
import za.co.discovery.services.VerticesService;

import java.util.List;

public class RootController {

    private static EdgesService edge;
    private static VerticesService vertex;
    private Graph graph = null;

    @Autowired
    public RootController(EdgesService edge, VerticesService vertex) {
        this.edge = edge;
        this.vertex = vertex;
    }

    // TODO change this static when doing the webpages
    public List<Edge> getPathTraveled() {
        EdgesService edge = new EdgesService();
        VerticesService vertex = new VerticesService();
        final List<Edge> edges = edge.getEdges();
        final List<Vertex> vertices = vertex.getVertexList();
        return edges;
    }

//    public static void main(String args[]) {
//        String start = "A";
//        String end = "X";
//        List<Edge> edges = getPathTraveled();
//        Graph graph = new Graph();
//        Map<String, Vertex> map = graph.Graph(edges);
//        ShortestPath dis = new ShortestPath();
//        dis.dijkstra(start, map);
//        dis.printPath(map, end);
//    }

    @RequestMapping("/")
    public String home() {
        return "index";
    }

}
