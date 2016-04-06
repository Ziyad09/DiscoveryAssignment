package za.co.discovery.dataAccess;

import com.shazam.shazamcrest.MatcherAssert;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;
import za.co.discovery.DAOConfig;
import za.co.discovery.DataSourceConfig;
import za.co.discovery.PersistenceConfig;
import za.co.discovery.models.Edge;

import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static za.co.discovery.models.EdgeBuilder.anEdge;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {PersistenceConfig.class, DataSourceConfig.class, DAOConfig.class, EdgeDAO.class},
        loader = AnnotationConfigContextLoader.class)
//@ActiveProfiles("test")
public class EdgeDAOTest {
    @Autowired
    private SessionFactory sessionFactory;

    //@Autowired
    private EdgeDAO edgeDAO;

    @Before
    public void init() {
        edgeDAO = new EdgeDAO(sessionFactory);
    }

    @Test
    public void testSave() throws Exception {
        Edge expectedEdge = anEdge()
                .withSource("A")
                .withDestination("B")
                .withRouteId(1)
                .withDistance(1.0)
                .build();
        Session session = sessionFactory.getCurrentSession();
        edgeDAO.save(expectedEdge);
        Criteria criteria = session.createCriteria(Edge.class);

        Edge actualEdge = (Edge) criteria.uniqueResult();
        assertThat(actualEdge, is(sameBeanAs(expectedEdge)
                .ignoring("edgeId")));
    }

    @Test
    public void testRetrieveEdge() throws Exception {
        Edge edge = anEdge()
                .withRouteId(1)
                .withSource("A")
                .withDestination("B")
                .withDistance(1.0)
                .build();
        Edge returnedEdge = edgeDAO.save(edge);
//        System.out.print(returnedEdge.getDestination()+"\n\n\n" + returnedEdge.getDistance()+"\n\n\n");
        Edge actualEdge = edgeDAO.retrieveEdge(returnedEdge.getRouteId());
//        System.out.print(actualEdge.getDestination()+"\n\n\n" + actualEdge.getDistance()+"\n\n\n");

        MatcherAssert.assertThat(actualEdge, is(sameBeanAs(edge)
                .ignoring("routeId")));
    }
}