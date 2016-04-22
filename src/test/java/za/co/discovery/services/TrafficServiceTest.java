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
import za.co.discovery.dataAccess.TrafficDAO;
import za.co.discovery.dataAccess.VertexDAO;
import za.co.discovery.models.Traffic;
import za.co.discovery.models.Vertex;

import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static za.co.discovery.models.TrafficBuilder.aTraffic;
import static za.co.discovery.models.VertexBuilder.aVertex;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {PersistenceConfig.class, DataSourceConfig.class, TrafficDAO.class, VertexDAO.class},
        loader = AnnotationConfigContextLoader.class)
public class TrafficServiceTest {

    @Autowired
    private TrafficDAO trafficDAO;
    private TrafficService trafficService;
    @Autowired
    private VertexDAO vertexDAO;

    private VerticesService verticesService;


    @Before
    public void init() {
        trafficService = new TrafficService(trafficDAO);
        verticesService = new VerticesService(vertexDAO);
    }

    @Test
    public void testGetTrafficById() throws Exception {

        Vertex vertexSource = aVertex().withNode("A").withPlanetName("Earth").build();
        Vertex vertexDestination = aVertex().withNode("B").withPlanetName("Moon").build();

        verticesService.persistVertex(vertexSource);
        verticesService.persistVertex(vertexDestination);


        Traffic expectedTraffic = aTraffic()
                .withRouteId(1)
                .withSource(vertexSource)
                .withDestination(vertexDestination)
                .withDistance(0.4)
                .build();

        trafficService.persistTraffic(1, vertexSource, vertexDestination, 0.4);
        Traffic actualTraffic = trafficService.getTrafficById(expectedTraffic.getRouteId());
        assertThat(actualTraffic, is(sameBeanAs(expectedTraffic)));

    }

    @Test
    public void testTrafficGetsUpdated() throws Exception {
        Vertex vertexSource = aVertex().withNode("A").withPlanetName("Earth").build();
        Vertex vertexDestination = aVertex().withNode("B").withPlanetName("Moon").build();
        Vertex vertexDestinationUpdate = aVertex().withNode("C").withPlanetName("Jupiter").build();

        verticesService.persistVertex(vertexSource);
        verticesService.persistVertex(vertexDestination);
        verticesService.persistVertex(vertexDestinationUpdate);


        trafficService.persistTraffic(1, vertexSource, vertexDestination, 0.4);

        Traffic updatedTraffic = aTraffic()
                .withRouteId(1)
                .withSource(vertexSource)
                .withDestination(vertexDestinationUpdate)
                .withDistance(0.4)
                .build();

        trafficService.updateTraffic(updatedTraffic);
        Traffic actualTraffic = trafficService.getTrafficById(updatedTraffic.getRouteId());
        assertThat(actualTraffic, is(sameBeanAs(updatedTraffic)));
    }

    @Test
    public void testTrafficGetsDeleted() throws Exception {
        Vertex vertexSource = aVertex().withNode("A").withPlanetName("Earth").build();
        Vertex vertexDestination = aVertex().withNode("B").withPlanetName("Moon").build();

        verticesService.persistVertex(vertexSource);
        verticesService.persistVertex(vertexDestination);

        trafficService.persistTraffic(1, vertexSource, vertexDestination, 0.4);
        int affectedRows = trafficService.deleteTraffic(1);
        assertThat(affectedRows, is(1));
    }
}