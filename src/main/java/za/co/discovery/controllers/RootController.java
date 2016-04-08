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

    public static List<Edge> getPathTraveled() {
        EdgesService edge = new EdgesService();
        VerticesService vertex = new VerticesService();
        final List<Edge> edges = edge.getEdges();
        final List<Vertex> vertices = vertex.getVertexList();
        return edges;
    }
//
//        for (int i = 0; i < vertices.size(); i++) {
//            for (int j = 0; i < edges.size(); i++) {
//                if (vertices.get(i).getNode() == edges.get(j).getSource()) {
//                    Vertex target = new Vertex(edges.get(j).getDestination());
//                    double distance = edges.get(j).getDistance();
//                    vertices.get(i).adjacency = new Edge[]{new Edge(target,distance)};
//                    edges.get(j).getDestination();
//                }
//            }
//        }
//        ShortestPath.computePaths(source); // run Dijkstra
////            System.out.println("Distance to " + Z + ": " + Z.minDistance);
//        List<Vertex> path = ShortestPath.getShortestPathTo(destination);
////            System.out.println("Path: " + path);
//        return path;
//    }

    //    public static void main(String args[]){
//        String start = "A";
//        String end = "X";
//        List<Edge> edges = getPathTraveled();
//        Graph graph = new Graph();
//        Map<String, Vertex> map = graph.Graph(edges);
//        ShortestPath dis = new ShortestPath();
//        dis.dijkstra(start,map);
//        dis.printPath(map,end);
//    }
    @RequestMapping("/")
    public String home() {
        return "index";
    }

}
