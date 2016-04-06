package za.co.discovery.dataAccess;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;
import za.co.discovery.DAOConfig;
import za.co.discovery.DataSourceConfig;
import za.co.discovery.PersistenceConfig;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {PersistenceConfig.class, DataSourceConfig.class, DAOConfig.class},
        loader = AnnotationConfigContextLoader.class)
@ActiveProfiles("test")
public class EdgeDAOTest {

//    @Autowired
//    private SessionFactory sessionFactory;
//    @Autowired
//    private EdgeDAO edgeDAO;

    @Test
    public void testSave() throws Exception {
//        Edge expectedEdge = anEdge()
//                .withSource("A")
//                .withDestination("B")
//                .withRouteId(1)
//                .withDistance(1.0)
//                .build();
//        Session session = sessionFactory.getCurrentSession();
//        edgeDAO.save(expectedEdge);
//        Criteria criteria = session.createCriteria(Edge.class);
//
//        Edge actualEdge = (Edge) criteria.uniqueResult();
//        assertThat(actualEdge, is(sameBeanAs(expectedEdge)
//                .ignoring("edgeId")));
    }

    @Test
    public void testRetrieveEdge() throws Exception {

    }
}