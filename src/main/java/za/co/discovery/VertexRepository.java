package za.co.discovery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import za.co.discovery.models.Edge;
import za.co.discovery.models.Graph;
import za.co.discovery.models.ShortestPath;
import za.co.discovery.models.Vertex;
import za.co.discovery.services.EdgesService;
import za.co.discovery.services.VerticesService;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component
public class VertexRepository {
    private static List<Vertex> vertexList = new ArrayList<Vertex>();
    private VerticesService verticesService;
    private EdgesService edgesService;
    private List<Edge> edges;
    @Autowired
    @Qualifier("transactionManager")
    protected PlatformTransactionManager platformTransactionManager;

    @Autowired
    public VertexRepository(EdgesService edgesService, VerticesService verticesService) {
        this.edgesService = edgesService;
        this.verticesService = verticesService;
    }

    @PostConstruct
    public void initData() {

        TransactionTemplate tmpl = new TransactionTemplate(platformTransactionManager);
        tmpl.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
//                EdgesService edge = new EdgesService();
//                VerticesService vertex = new VerticesService();
                edges = edgesService.getEdges();
                verticesService.readVertices();
                vertexList = verticesService.vertexList;
//                Vertex earth = new Vertex("A", "Earth");
//                vertexList.add(earth);
//                Vertex moon = new Vertex("B", "Moon");
//                vertexList.add(moon);
//                Vertex venus = new Vertex("C", "Venus");
//                vertexList.add(venus);
            }
        });
    }

    public String findVertex(String name) {
        Graph graph = new Graph();
        Map<String, Vertex> map = graph.Graph(edges);
        ShortestPath dis = new ShortestPath();
        dis.dijkstra("A", map);

        LinkedList<String> actual = dis.printPath(map, name);
        String pathTravelled = new String("");
        for (int i = 0; i < actual.size(); i++) {
            Vertex returnedV = verticesService.getVertexByNode(actual.get(i));
            pathTravelled += returnedV.getPlanetName() + " ";
        }
        if (actual.size() > 0) {
            actual.clear();
        }

        return pathTravelled;


//        Assert.notNull(name);
//        Vertex result = null;
//        for (Vertex vertex : vertexList) {
//            if (name.equals(vertex.getPlanetName())) {
//                result = vertex;
//            }
//        }
//
//        return result;
    }

}