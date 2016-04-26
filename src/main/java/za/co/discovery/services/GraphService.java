package za.co.discovery.services;

import org.springframework.stereotype.Service;
import za.co.discovery.models.Edge;
import za.co.discovery.models.Traffic;
import za.co.discovery.models.Vertex;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GraphService {
    public Map<String, Vertex> buildGraphWithEdges(List<Edge> edges) {
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
            Vertex vertexNeighboursDestination = graph.get(e.getDestination().getNode());
            double distanceCalculated = e.getDistance();
//            graph.get(e.getSource().getNode()).neighbours.put(vertexNeighbours, distanceCalculated);
            String sourceNode = e.getSource().getNode();
            graph.get(sourceNode).setNeighbours(vertexNeighboursDestination, distanceCalculated);
        }
        return graph;
    }

    public Map<String, Vertex> buildGraphWithTraffic(List<Traffic> traffic) {
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
            String sourceNode = e.getSource().getNode();
            double distance = e.getDistance();
//            graph.get(sourceNode).neighbours.put(graph.get(destinationNode), distance);
            Vertex destinationNeighbours = graph.get(e.getDestination().getNode());
            graph.get(sourceNode).setNeighbours(destinationNeighbours, distance);
        }
        return graph;
    }

}
