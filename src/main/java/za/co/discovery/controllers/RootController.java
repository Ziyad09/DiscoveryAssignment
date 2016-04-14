package za.co.discovery.controllers;

import org.apache.commons.lang3.RandomStringUtils;
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
import java.util.concurrent.ThreadLocalRandom;

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

    @RequestMapping("/update")
    public String update() {
        return "update";
    }

    @RequestMapping("/delete")
    public String delete() {
        return "delete";
    }

    @RequestMapping("/addNode")
    public String addNode() {
        return "addNode";
    }

    @RequestMapping(value = "/updateVertex/{vertexUpdate}",
            method = RequestMethod.GET)
    @ResponseBody
    public void updateVertex(@PathVariable String vertexUpdate) {
        String[] updated = vertexUpdate.split(",");
        String nodeName = updated[0];
        String newPlanetName = updated[1];
//        Vertex oldVertex = vertex.getVertexByNode(nodeName);
        Vertex newVertex = new Vertex(nodeName, newPlanetName);
        vertex.updateVertex(newVertex);
//        return "index";
    }

    @RequestMapping(value = "/addVertex/{vertexAdded}",
            method = RequestMethod.GET)
    @ResponseBody
    public void addVertex(@PathVariable String vertexAdded) {
        String[] updated = vertexAdded.split(",");
        String nodeName = updated[0];
        String newPlanetName = updated[1];
        double distance = Double.parseDouble(updated[2]);
        String vertexId = RandomStringUtils.randomAlphanumeric(3).toUpperCase();
        Vertex newVertex = vertex.getVertexByNode(vertexId);
        if (newVertex == null) {
            newVertex = new Vertex(vertexId, newPlanetName);
        } else {
            vertexId = RandomStringUtils.randomAlphanumeric(3).toUpperCase();
        }

        newVertex = new Vertex(vertexId, newPlanetName);
        vertex.persistVertex(newVertex);
        int[] randomId = ThreadLocalRandom.current().ints(50, 100).distinct().limit(5).toArray();
        int id = randomId[3];
        edge.persistEdge(id, nodeName, newPlanetName, distance);
//        System.out.print("\n\n"+edge.getEdgeById(id).getDestination()+"\n\n"+edge.getEdgeById(id).getSource());
    }

    @RequestMapping(value = "/deleteVertex/{destinationToDelete}",
            method = RequestMethod.GET)
    @ResponseBody
    public void deleteVertex(@PathVariable String destinationToDelete) {
        vertex.deleteVertex(destinationToDelete);
        System.out.print(vertex.getVertexByNode(destinationToDelete));
//        return "index";
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
