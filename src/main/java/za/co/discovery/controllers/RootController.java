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

    private EdgesService edgesService;
    private VerticesService vertexService;
    private TrafficService traffic;

    @Autowired
    public RootController(EdgesService edgesService, VerticesService vertexService,
                          TrafficService traffic) {
        this.edgesService = edgesService;
        this.vertexService = vertexService;
        this.traffic = traffic;
    }

    @RequestMapping("/")
    public String home(Model model) {
        List<Vertex> vertices = vertexService.getVertexList();
        model.addAttribute("vertexList", vertices);
        List<Traffic> trafficList = traffic.getTrafficList();
        List<Edge> edgeList = edgesService.getEdgeList();
        for (int i = 0; i < trafficList.size(); i++) {
            int id = trafficList.get(i).getRouteId();
            String source = trafficList.get(i).getSource();
            String destination = trafficList.get(i).getDestination();
            double distance = edgeList.get(i).getDistance() + trafficList.get(i).getDistance();
            Traffic lastTraffic = new Traffic(id, source, destination, distance);
            traffic.updateTraffic(lastTraffic);
        }
//        System.out.print("\n\n"+vertices.get(vertices.size()-1).getNode()+"\n\n");
        return "index";
    }

    @RequestMapping("/update")
    public String update(Model model) {
        List<Vertex> vertices = vertexService.getVertexList();
        model.addAttribute("vertexList", vertices);
        return "update";
    }

    @RequestMapping("/delete")
    public String delete(Model model) {
        List<Vertex> vertices = vertexService.getVertexList();
        model.addAttribute("vertexList", vertices);
        return "delete";
    }

    @RequestMapping("/addNode")
    public String addNode(Model model) {
        List<Vertex> vertices = vertexService.getVertexList();
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
        vertexService.updateVertex(newVertex);
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

        // Add the edge
        int[] randomId = ThreadLocalRandom.current().ints(50, 100).distinct().limit(5).toArray();
        int id = randomId[3];
        Edge newEdge = new Edge(id, nodeName, newPlanetName, distance);
        edgesService.persistEdge(newEdge);

        // Add the vertex
        Vertex newVertex = vertexService.getVertexByNode(vertexId);
        while (newVertex != null) {
            vertexId = RandomStringUtils.randomAlphanumeric(3).toUpperCase();
            newVertex = new Vertex(vertexId, newPlanetName);
        }
        newVertex = new Vertex(vertexId, newPlanetName);
        vertexService.persistVertex(newVertex);
    }

    @RequestMapping(value = "/deleteVertex/{destinationToDelete}",
            method = RequestMethod.GET)
    @ResponseBody
    public void deleteVertex(@PathVariable String destinationToDelete) {
        vertexService.deleteVertex(destinationToDelete);
    }

    // Traffic delay
    @RequestMapping(
            value = "/selectDelayedPath/{delayPath}",
            method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> selectDelayedPath(@PathVariable String delayPath) {

        Graph graph = new Graph();
        List<Traffic> trafficList = traffic.getTrafficList();
        Map<String, Vertex> mapTraffic = graph.GraphTraffic(trafficList);
        ShortestPath disTraffic = new ShortestPath();
        disTraffic.dijkstra("A", mapTraffic);
        LinkedList<String> actual = disTraffic.printPath(mapTraffic, delayPath);

        String pathTravelled = "";
        for (String anActual : actual) {
            Vertex returnedV = vertexService.getVertexByNode(anActual);
            if (returnedV == null) {
                pathTravelled = "Unable to determine path, Planet vanished";
            } else {
                pathTravelled += returnedV.getPlanetName() + " ";
            }
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
    public ResponseEntity<String> selectPath(@PathVariable String path) {

        List<Edge> edges = edgesService.getEdgeList();
        Graph graph = new Graph();
        Map<String, Vertex> map = graph.GraphEdge(edges);
        System.out.print("\n\n\n\n" + map.get(path) + "\n\n\n");
        ShortestPath dis = new ShortestPath();
        dis.dijkstra("A", map);
        LinkedList<String> actual = dis.printPath(map, path);
        String pathTravelled = "";
        for (String anActual : actual) {
            Vertex returnedV = vertexService.getVertexByNode(anActual);
//            if (returnedV == null) {
//                pathTravelled = "Unable to determine path, Planet was destroyed";
//            } else {
                pathTravelled += returnedV.getPlanetName() + " ";
//            }
        }
        if (actual.size() > 0) {
            actual.clear();
        }
        return new ResponseEntity<>(pathTravelled, HttpStatus.OK);
    }
}
