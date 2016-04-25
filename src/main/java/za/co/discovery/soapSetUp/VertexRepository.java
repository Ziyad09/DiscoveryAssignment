package za.co.discovery.soapSetUp;

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
                edges = edgesService.getEdgeList();
                vertexList = verticesService.getVertexList();
            }
        });
    }


    public String findVertex(String name) {
        Graph graph = new Graph();
        // TODO don't keep state variables on a service (could be outdated)
        Map<String, Vertex> map = graph.GraphEdge(edges);
        ShortestPath dis = new ShortestPath();
        dis.dijkstra("A", map);

        List<String> actual = dis.printPath(map, name);
        map.clear();
        String pathTravelledSoap = "";
        // TODO use string builder
        for (String anActual : actual) {
            Vertex returnedV = verticesService.getVertexByNode(anActual);
            pathTravelledSoap += returnedV.getPlanetName() + " ";
        }
        if (actual.size() > 0) {
            actual.clear();
        }

        return pathTravelledSoap;
    }

}
