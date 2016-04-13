package za.co.discovery.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import za.co.discovery.models.Edge;
import za.co.discovery.models.Graph;
import za.co.discovery.models.ShortestPath;
import za.co.discovery.models.Vertex;
import za.co.discovery.services.EdgesService;
import za.co.discovery.services.VerticesService;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
public class RootController {

    private static EdgesService edge;
    private static VerticesService vertex;
    private Graph graph = null;

    @Autowired
    public RootController(EdgesService edge, VerticesService vertex) {
        this.edge = edge;
        this.vertex = vertex;
    }

    public List<Edge> getPathTraveled() {
        EdgesService edge = new EdgesService();
//        VerticesService vertex = new VerticesService();
        final List<Edge> edges = edge.getEdges();
//        final List<Vertex> vertices = vertex.getVertexList();
        return edges;
    }


    @RequestMapping("/")
    public String home() {
        return "index";
    }

    @RequestMapping(
            value = "/selectPath/{path}",
            method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> selectPath(@PathVariable String path) {
        List<Edge> edges = getPathTraveled();
        Graph graph = new Graph();
        Map<String, Vertex> map = graph.Graph(edges);
        ShortestPath dis = new ShortestPath();
        dis.dijkstra("A", map);
        LinkedList<String> actual = dis.printPath(map, path);
        String pathTravelled = new String("");
        for (int i = 0; i < actual.size(); i++) {
            Vertex returnedV = vertex.getVertexByNode(actual.get(i));
            pathTravelled += returnedV.getPlanetName() + " ";
        }
        if (actual.size() > 0) {
            actual.clear();
        }
//        System.out.print("\n\n" + pathTravelled + "\n\n");
        return new ResponseEntity<>(pathTravelled, HttpStatus.OK);
    }
}
