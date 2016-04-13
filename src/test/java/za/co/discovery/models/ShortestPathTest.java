package za.co.discovery.models;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static za.co.discovery.models.EdgeBuilder.anEdge;

public class ShortestPathTest {

    @Test
    public void testDijkstra() throws Exception {
        String start = "A";
        String end = "C";
        List<Edge> edges = new ArrayList<>(3);
        Edge edge1 = anEdge()
                .withRouteId(1)
                .withSource("A")
                .withDestination("B")
                .withDistance(6)
                .build();
        Edge edge2 = anEdge()
                .withRouteId(2)
                .withSource("B")
                .withDestination("C")
                .withDistance(6)
                .build();
        Edge edge3 = anEdge()
                .withRouteId(3)
                .withSource("A")
                .withDestination("C")
                .withDistance(1)
                .build();
        edges.add(edge1);
        edges.add(edge2);
        edges.add(edge3);

        Graph graph = new Graph();
        Map<String, Vertex> map = graph.Graph(edges);
        ShortestPath dis = new ShortestPath();
        dis.dijkstra(start, map);
        LinkedList<String> actual = dis.printPath(map, end);
        System.out.print(actual.toString());
//        System.out.print(actual.get(0));
        assertThat(actual.toString(), is("[A, C]"));
    }
}