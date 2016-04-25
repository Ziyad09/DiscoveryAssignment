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
import za.co.discovery.services.FileReaderService;
import za.co.discovery.services.TrafficService;
import za.co.discovery.services.VerticesService;

import java.util.List;
import java.util.Map;

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
        return "index";
    }


    @RequestMapping("/update")
    public String update(Model model) {
        List<Vertex> vertices = vertexService.getVertexList();
        model.addAttribute("vertexList", vertices);
        List<Edge> edgesL = edgesService.getEdgeList();
        model.addAttribute("edgeList", edgesL);
        List<Traffic> trafficL = traffic.getTrafficList();
        model.addAttribute("trafficList", trafficL);
        return "update";
    }

    @RequestMapping("/delete")
    public String delete(Model model) {
        List<Edge> edgesL = edgesService.getEdgeList();
        model.addAttribute("edgeList", edgesL);
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

    @RequestMapping(value = "/updateRoute/{routeUpdate}",
            method = RequestMethod.GET)
    @ResponseBody
    public void updateRoute(@PathVariable String routeUpdate) {
        String[] updated = routeUpdate.split(",");
        int routeId = Integer.parseInt(updated[0]);
        String source = updated[1];
        String destination = updated[2];
        double distance = Double.parseDouble(updated[3]);

        Vertex sourceVertex = vertexService.getVertexByPlanetName(source);
        Vertex destinationVertex = vertexService.getVertexByPlanetName(destination);

        Edge newEdge = new Edge(routeId, sourceVertex, destinationVertex, distance);
        edgesService.updateEdge(newEdge);
    }

    @RequestMapping(value = "/updateTraffic/{trafficUpdate}",
            method = RequestMethod.GET)
    @ResponseBody
    public void updateTraffic(@PathVariable String trafficUpdate) {
        String[] updated = trafficUpdate.split(",");
        int routeId = Integer.parseInt(updated[0]);
        Traffic returnedTraffic = traffic.getTrafficById(routeId);
        double distance = Double.parseDouble(updated[1]);

        Traffic newTraffic = new Traffic(routeId, returnedTraffic.getSource(), returnedTraffic.getDestination(), distance);
        traffic.updateTraffic(newTraffic);
    }

    @RequestMapping(value = "/addEdge/{edgeAdded}",
            method = RequestMethod.GET)
    @ResponseBody
    public void addEdge(@PathVariable String edgeAdded) {
        String[] updated = edgeAdded.split(",");
        String source = updated[0];
        String destination = updated[1];
        double distance = Double.parseDouble(updated[2]);

        Vertex sourceVertex = vertexService.getVertexByNode(source);
        Vertex destinationVertex = vertexService.getVertexByNode(destination);

        Edge newEdge = new Edge(sourceVertex, destinationVertex, distance);
        edgesService.persistEdge(newEdge);
        traffic.persistTrafficWithoutId(sourceVertex, destinationVertex, 0D);
    }

    @RequestMapping(value = "/addVertex/{vertexAdded}",
            method = RequestMethod.GET)
    @ResponseBody
    public void addVertex(@PathVariable String vertexAdded) {
        String vertexId = RandomStringUtils.randomAlphanumeric(3).toUpperCase();
        // Add the vertex
        Vertex newVertex = vertexService.getVertexByNode(vertexId);
        while (newVertex != null) {
            vertexId = RandomStringUtils.randomAlphanumeric(3).toUpperCase();
            newVertex = new Vertex(vertexId, vertexAdded);
        }
        newVertex = new Vertex(vertexId, vertexAdded);
        vertexService.persistVertex(newVertex);
    }

    @RequestMapping(value = "/deleteRoute/{routeToDelete}",
            method = RequestMethod.GET)
    @ResponseBody
    public void deleteRoute(@PathVariable String routeToDelete) {
        int routeId = Integer.parseInt(routeToDelete);
        edgesService.deleteEdge(routeId);
        traffic.deleteTraffic(routeId);
    }

    // Traffic delay
    @RequestMapping(
            value = "/selectDelayedPath/{delayPath}",
            method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> selectDelayedPath(@PathVariable String delayPath) {

        Graph graph = new Graph();
        List<Traffic> trafficList = traffic.getTrafficList();
        Map<String, Vertex> mapTraffic = graph.buildGraphWithTraffic(trafficList);
        ShortestPath disTraffic = new ShortestPath();
        disTraffic.dijkstra("A", mapTraffic);
        List<String> actual = disTraffic.printPath(mapTraffic, delayPath);
        String pathTravelled = "";
        for (String anActual : actual) {
            Vertex returnedV = vertexService.getVertexByNode(anActual);
            if (returnedV == null) {
                pathTravelled = "Unable to determine path";
            } else {
                pathTravelled += returnedV.getPlanetName() + " ";
            }
        }
        return new ResponseEntity<>(pathTravelled, HttpStatus.OK);
    }

    // No traffic delay
    @RequestMapping(
            value = "/selectPath/{path}",
            method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> selectPath(@PathVariable String path) {

        Graph graph = new Graph();
        List<Edge> edges = edgesService.getEdgeList();
        Map<String, Vertex> map = graph.buildGraphWithEdges(edges);
        ShortestPath dis = new ShortestPath();
        dis.dijkstra("A", map);
        List<String> actual = dis.printPath(map, path);
        String pathTravelled = "";
        for (String anActual : actual) {
            Vertex returnedV = vertexService.getVertexByNode(anActual);
            if (returnedV == null) {
                pathTravelled = "Unable to determine path, no route";
            } else {
                pathTravelled += returnedV.getPlanetName() + " ";
            }
        }
        return new ResponseEntity<>(pathTravelled, HttpStatus.OK);
    }
}
