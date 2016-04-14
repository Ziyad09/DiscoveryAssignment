package za.co.discovery.controllers;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import za.co.discovery.models.*;
import za.co.discovery.services.EdgesService;
import za.co.discovery.services.TrafficService;
import za.co.discovery.services.VerticesService;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Controller
public class RootController {

    private static EdgesService edge;
    private static VerticesService vertex;
    private static TrafficService traffic;
    private Graph graph = null;

    @Autowired
    public RootController(EdgesService edge, VerticesService vertex,
                          TrafficService traffic) {
        this.edge = edge;
        this.vertex = vertex;
        this.traffic = traffic;
    }

    @RequestMapping("/")
    public String home(Model model) {
        List<Vertex> vertices = vertex.getVertexList();
        model.addAttribute("vertexList", vertices);
        List<Traffic> trafficList = traffic.getTrafficList();
        List<Edge> edgeList = edge.getEdgeList();
        for (int i = 0; i < trafficList.size(); i++) {
            int id = trafficList.get(i).getRouteId();
            String source = trafficList.get(i).getSource();
            String destination = trafficList.get(i).getDestination();
            double distance = edgeList.get(i).getDistance() + trafficList.get(i).getDistance();
            Traffic lastTraffic = new Traffic(id, source, destination, distance);
            traffic.updateTraffic(lastTraffic);
        }
        return "index";
    }

//    public static void main(String args[]){
////        System.out.print(traffic.getTrafficList().get(0));
//        System.out.print(edge.getEdgeList().get(0));
//    }

    @RequestMapping("/update")
    public String update(Model model) {
        List<Vertex> vertices = vertex.getVertexList();
        model.addAttribute("vertexList", vertices);
        return "update";
    }

    @RequestMapping("/delete")
    public String delete(Model model) {
        List<Vertex> vertices = vertex.getVertexList();
        model.addAttribute("vertexList", vertices);
        return "delete";
    }

    @RequestMapping("/addNode")
    public String addNode(Model model) {
        List<Vertex> vertices = vertex.getVertexList();
        model.addAttribute("vertexList", vertices);
        return "addNode";
    }

    @RequestMapping(value = "/updateVertex/{vertexUpdate}",
            method = RequestMethod.GET)
    @ResponseBody
    public void updateVertex(@PathVariable String vertexUpdate) {
        String[] updated = vertexUpdate.split(",");
        String nodeName = updated[0];
        String newPlanetName = updated[1];
        Vertex newVertex = new Vertex(nodeName, newPlanetName);
        vertex.updateVertex(newVertex);
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
        while (newVertex != null) {
            vertexId = RandomStringUtils.randomAlphanumeric(3).toUpperCase();
            newVertex = new Vertex(vertexId, newPlanetName);
        }
        newVertex = new Vertex(vertexId, newPlanetName);
        vertex.persistVertex(newVertex);
        int[] randomId = ThreadLocalRandom.current().ints(50, 100).distinct().limit(5).toArray();
        int id = randomId[3];
        Edge newEdge = new Edge(id, nodeName, newPlanetName, distance);
        edge.persistEdge(newEdge);
    }

    @RequestMapping(value = "/deleteVertex/{destinationToDelete}",
            method = RequestMethod.GET)
    @ResponseBody
    public void deleteVertex(@PathVariable String destinationToDelete) {
        vertex.deleteVertex(destinationToDelete);
    }

    // Traffic delay
    @RequestMapping(
            value = "/selectDelayedPath/{delayPath}",
            method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> selectDelayedPath(@PathVariable String delayPath, Model model) {

        Graph graph = new Graph();
        List<Traffic> trafficList = traffic.getTrafficList();
        Map<String, Vertex> mapTraffic = graph.GraphTraffic(trafficList);
        ShortestPath disTraffic = new ShortestPath();
        disTraffic.dijkstra("A", mapTraffic);
        LinkedList<String> actual = disTraffic.printPath(mapTraffic, delayPath);

        String pathTravelled = new String("");
        for (int i = 0; i < actual.size(); i++) {
            Vertex returnedV = vertex.getVertexByNode(actual.get(i));
            pathTravelled += returnedV.getPlanetName() + " ";
        }
        if (actual.size() > 0) {
            actual.clear();
        }
        return new ResponseEntity<>(pathTravelled, HttpStatus.OK);
    }

    // No traffic delay
    @RequestMapping(
            value = "/selectPath/{path}",
            method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> selectPath(@PathVariable String path, Model model) {

        List<Edge> edges = edge.getEdgeList();
        Graph graph = new Graph();
        Map<String, Vertex> map = graph.GraphEdge(edges);
        ShortestPath dis = new ShortestPath();
        dis.dijkstra("A", map);
        LinkedList<String> actual = dis.printPath(map, path);

        String pathTravelled = new String("");
        for (int i = 0; i < actual.size(); i++) {
            Vertex returnedV = vertex.getVertexByNode(actual.get(i));
            if (returnedV == null) {
                pathTravelled = "Unable to determine path, Planet was destroyed";
            } else {
                pathTravelled += returnedV.getPlanetName() + " ";
            }
        }
        if (actual.size() > 0) {
            actual.clear();
        }
        return new ResponseEntity<>(pathTravelled, HttpStatus.OK);
    }
}
