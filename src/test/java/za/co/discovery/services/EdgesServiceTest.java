package za.co.discovery.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;
import za.co.discovery.DataSourceConfig;
import za.co.discovery.PersistenceConfig;
import za.co.discovery.dataAccess.EdgeDAO;
import za.co.discovery.models.Edge;

import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static za.co.discovery.models.EdgeBuilder.anEdge;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {PersistenceConfig.class, DataSourceConfig.class, EdgeDAO.class},
        loader = AnnotationConfigContextLoader.class)
public class EdgesServiceTest {

    @Autowired
//    private SessionFactory sessionFactory;

    //@Autowired
    private EdgeDAO edgeDAO;
    private Edge firstEdge;
    private EdgesService edgesService;

    @Before
    public void init() {
        edgesService = new EdgesService(edgeDAO);
    }

    @Test
    public void testGetEdgeById() throws Exception {
        firstEdge = anEdge()
                .withRouteId(1)
                .withSource("A")
                .withDestination("B")
                .withDistance(0.4)
                .build();
        edgesService.persistEdge(1, "A", "B", 0.4);
        Edge actualEdge = edgesService.getEdgeById(firstEdge.getRouteId());
        assertThat(actualEdge, is(sameBeanAs(firstEdge)));
    }

    @Test
    public void testUpdateEdge() throws Exception {
        edgesService.persistEdge(1, "A", "B", 0.4);
        Edge updatedEdge = anEdge()
                .withRouteId(1)
                .withSource("A")
                .withDestination("C")
                .withDistance(0.4)
                .build();
        edgesService.updateEdge(updatedEdge);
        Edge actualEdge = edgesService.getEdgeById(updatedEdge.getRouteId());
        assertThat(actualEdge, is(sameBeanAs(updatedEdge)));
    }

    @Test
    public void testDeleteEdge() throws Exception {
        edgesService.persistEdge(1, "A", "B", 0.4);
        int affectedRows = edgesService.deleteEdge(1);
        assertThat(affectedRows, is(1));
    }
}