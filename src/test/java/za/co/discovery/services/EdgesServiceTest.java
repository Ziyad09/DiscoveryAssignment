//package za.co.discovery.services;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.support.AnnotationConfigContextLoader;
//import org.springframework.transaction.annotation.Transactional;
//import za.co.discovery.configuration.DataSourceConfig;
//import za.co.discovery.configuration.PersistenceConfig;
//import za.co.discovery.dataAccess.EdgeDAO;
//import za.co.discovery.models.Edge;
//
//import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
//import static org.hamcrest.core.Is.is;
//import static org.junit.Assert.assertThat;
//import static za.co.discovery.models.EdgeBuilder.anEdge;
//
//@Transactional
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(
//        classes = {PersistenceConfig.class, DataSourceConfig.class, EdgeDAO.class},
//        loader = AnnotationConfigContextLoader.class)
//public class EdgesServiceTest {
//
//    @Autowired
//    private EdgeDAO edgeDAO;
//    private EdgesService edgesService;
//
//    @Before
//    public void init() {
//        edgesService = new EdgesService(edgeDAO);
//    }
//
//    @Test
//    public void testGetEdgeById() throws Exception {
//        Edge firstEdge = anEdge()
//                .withRouteId(1)
//                .withSource("A")
//                .withDestination("B")
//                .withDistance(0.4)
//                .build();
//        Edge newEdge = new Edge(1, "A", "B", 0.4);
//        edgesService.persistEdge(newEdge);
//        Edge actualEdge = edgesService.getEdgeById(firstEdge.getRouteId());
//        assertThat(actualEdge, is(sameBeanAs(firstEdge)));
//    }
//
//    @Test
//    public void testUpdateEdge() throws Exception {
//        Edge newEdge = new Edge(1, "A", "B", 0.4);
//        edgesService.persistEdge(newEdge);
//        Edge updatedEdge = anEdge()
//                .withRouteId(1)
//                .withSource("A")
//                .withDestination("C")
//                .withDistance(0.4)
//                .build();
//        edgesService.updateEdge(updatedEdge);
//        Edge actualEdge = edgesService.getEdgeById(updatedEdge.getRouteId());
//        assertThat(actualEdge, is(sameBeanAs(updatedEdge)));
//    }
//
//    @Test
//    public void testDeleteEdge() throws Exception {
//        Edge newEdge = new Edge(1, "A", "B", 0.4);
//        edgesService.persistEdge(newEdge);
//        int affectedRows = edgesService.deleteEdge(1);
//        assertThat(affectedRows, is(1));
//    }
//}