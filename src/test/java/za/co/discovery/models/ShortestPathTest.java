package za.co.discovery.models;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;
import za.co.discovery.configuration.DataSourceConfig;
import za.co.discovery.configuration.PersistenceConfig;
import za.co.discovery.dataAccess.EdgeDAO;
import za.co.discovery.dataAccess.VertexDAO;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static za.co.discovery.models.EdgeBuilder.anEdge;
import static za.co.discovery.models.VertexBuilder.aVertex;

//@Transactional
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(
//        classes = {PersistenceConfig.class, DataSourceConfig.class, EdgeDAO.class, VertexDAO.class},
//        loader = AnnotationConfigContextLoader.class)
public class ShortestPathTest {

    @Test
    public void testDijkstra() throws Exception {

        Vertex vertexSource = aVertex().withNode("A").withPlanetName("Earth").build();
        Vertex vertexInRoute = aVertex().withNode("B").withPlanetName("Moon").build();
        Vertex vertexDestination = aVertex().withNode("C").withPlanetName("Jupiter").build();

        String start = "A";
        String end = "C";
        List<Edge> edges = new ArrayList<>(3);
        Edge edge1 = anEdge()
                .withRouteId(1)
                .withSource(vertexSource)
                .withDestination(vertexInRoute)
                .withDistance(6)
                .build();
        Edge edge2 = anEdge()
                .withRouteId(2)
                .withSource(vertexInRoute)
                .withDestination(vertexDestination)
                .withDistance(6)
                .build();
        Edge edge3 = anEdge()
                .withRouteId(3)
                .withSource(vertexSource)
                .withDestination(vertexDestination)
                .withDistance(1)
                .build();
        edges.add(edge1);
        edges.add(edge2);
        edges.add(edge3);

        Graph graph = new Graph();
        Map<String, Vertex> map = graph.GraphEdge(edges);
        ShortestPath dis = new ShortestPath();
        dis.dijkstra(start, map);
        LinkedList<String> actual = dis.printPath(map, end);
        assertThat(actual.toString(), is("[A, C]"));
    }
}