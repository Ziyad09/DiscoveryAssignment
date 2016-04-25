package za.co.discovery.soapSetUp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import za.co.discovery.models.*;
import za.co.discovery.services.EdgesService;
import za.co.discovery.services.FileReaderService;
import za.co.discovery.services.TrafficService;
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
    private FileReaderService fileReaderService;
    private TrafficService trafficService;

    @Autowired
    @Qualifier("transactionManager")
    protected PlatformTransactionManager platformTransactionManager;

    @Autowired
    public VertexRepository(EdgesService edgesService, VerticesService verticesService, FileReaderService fileReaderService
            , TrafficService trafficService) {
        this.edgesService = edgesService;
        this.verticesService = verticesService;
        this.fileReaderService = fileReaderService;
        this.trafficService = trafficService;
    }

    @PostConstruct
    public void initData() {

        TransactionTemplate tmpl = new TransactionTemplate(platformTransactionManager);
        tmpl.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                fileReaderService.readVertices();
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
        String pathTravelledSoap = new String("");
        for (String anActual : actual) {
            Vertex returnedV = verticesService.getVertexByNode(anActual);
            pathTravelledSoap += returnedV.getPlanetName() + " ";
        }

        return pathTravelledSoap;
    }

}
