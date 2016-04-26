package za.co.discovery.soapSetUp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import za.co.discovery.models.ShortestPath;
import za.co.discovery.models.Vertex;
import za.co.discovery.services.*;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class VertexRepository {
    private static List<Vertex> vertexList = new ArrayList<>();
    private VerticesService verticesService;
    private EdgesService edgesService;
    private FileReaderService fileReaderService;
    private TrafficService trafficService;

    //    @Autowired
    private InputStream file;

    @Autowired
    @Qualifier("transactionManager")
    protected PlatformTransactionManager platformTransactionManager;


    @Autowired
    public VertexRepository(EdgesService edgesService,
                            VerticesService verticesService, FileReaderService fileReaderService
            , InputStream file, TrafficService trafficService) {
        this.edgesService = edgesService;
        this.verticesService = verticesService;
        this.fileReaderService = fileReaderService;
        this.trafficService = trafficService;
        this.file = file;
    }

    @PostConstruct
    public void initData() throws URISyntaxException {

        TransactionTemplate tmpl = new TransactionTemplate(platformTransactionManager);
        tmpl.execute(new TransactionCallbackWithoutResult() {

            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                fileReaderService.readVertices();
                vertexList = verticesService.getVertexList();
            }
        });
    }


    public StringBuilder findVertex(String name) {
        GraphService graphService = new GraphService();
        Map<String, Vertex> map = graphService.buildGraphWithEdges(edgesService.getEdgeList());
        ShortestPath dis = new ShortestPath();
        dis.dijkstra("A", map);

        List<String> actual = dis.printPath(map, name);
        map.clear();
        StringBuilder pathTravelledSoap = new StringBuilder();
        for (String anActual : actual) {
            Vertex returnedV = verticesService.getVertexByNode(anActual);
            pathTravelledSoap.append(returnedV.getPlanetName());
            pathTravelledSoap.append(" ");
        }

        return pathTravelledSoap;
    }

}
