package za.co.discovery.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;
import za.co.discovery.configuration.DataSourceConfig;
import za.co.discovery.configuration.PersistenceConfig;
import za.co.discovery.dataAccess.EdgeDAO;
import za.co.discovery.dataAccess.VertexDAO;
import za.co.discovery.models.Edge;
import za.co.discovery.models.Vertex;

import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static za.co.discovery.models.EdgeBuilder.anEdge;
import static za.co.discovery.models.VertexBuilder.aVertex;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {PersistenceConfig.class, DataSourceConfig.class, EdgeDAO.class, VertexDAO.class},
        loader = AnnotationConfigContextLoader.class)
public class EdgesServiceTest {

    @Autowired
    private EdgeDAO edgeDAO;
    private EdgesService edgesService;
    @Autowired
    private VertexDAO vertexDAO;
    private VerticesService verticesService;


    @Before
    public void init() {
        edgesService = new EdgesService(edgeDAO);
        verticesService = new VerticesService(vertexDAO);
    }

    @Test
    public void testGetEdgeById() throws Exception {

        Vertex vertexSource = aVertex().withNode("A").withPlanetName("Earth").build();
        Vertex vertexDestination = aVertex().withNode("B").withPlanetName("Moon").build();

        verticesService.persistVertex(vertexSource);
        verticesService.persistVertex(vertexDestination);

        Edge firstEdge = anEdge()
                .withRouteId(1)
                .withSource(vertexSource)
                .withDestination(vertexDestination)
                .withDistance(0.4)
                .build();
        Edge newEdge = new Edge(1, vertexSource, vertexDestination, 0.4);
        edgesService.persistEdge(newEdge);
        Edge actualEdge = edgesService.getEdgeById(firstEdge.getRouteId());
        assertThat(actualEdge, is(sameBeanAs(firstEdge)));
    }

    @Test
    public void testUpdateEdge() throws Exception {
        Vertex vertexSource = aVertex().withNode("A").withPlanetName("Earth").build();
        Vertex vertexDestination = aVertex().withNode("B").withPlanetName("Moon").build();
        Vertex vertexDestinationUpdate = aVertex().withNode("C").withPlanetName("Jupiter").build();

        verticesService.persistVertex(vertexSource);
        verticesService.persistVertex(vertexDestination);
        verticesService.persistVertex(vertexDestinationUpdate);
        Edge newEdge = new Edge(1, vertexSource, vertexDestination, 0.4);
        edgesService.persistEdge(newEdge);
        Edge updatedEdge = anEdge()
                .withRouteId(1)
                .withSource(vertexSource)
                .withDestination(vertexDestinationUpdate)
                .withDistance(0.4)
                .build();
        edgesService.updateEdge(updatedEdge);
        Edge actualEdge = edgesService.getEdgeById(updatedEdge.getRouteId());
        assertThat(actualEdge, is(sameBeanAs(updatedEdge)));
    }

    @Test
    public void testDeleteEdge() throws Exception {
        Vertex vertexSource = aVertex().withNode("A").withPlanetName("Earth").build();
        Vertex vertexDestination = aVertex().withNode("B").withPlanetName("Moon").build();

        verticesService.persistVertex(vertexSource);
        verticesService.persistVertex(vertexDestination);
        Edge newEdge = new Edge(1, vertexSource, vertexDestination, 0.4);
        edgesService.persistEdge(newEdge);
        int affectedRows = edgesService.deleteEdge(1);
        assertThat(affectedRows, is(1));
    }
}