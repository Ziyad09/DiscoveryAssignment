package za.co.discovery.dataAccess;

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
public class EdgeDAOTest {
    @Autowired
    private SessionFactory sessionFactory;
    private EdgeDAO edgeDAO;
    private Edge firstEdge;

    @Before
    public void init() {
        edgeDAO = new EdgeDAO(sessionFactory);
    }

    @Test
    public void testSave() throws Exception {

        // Set up Fixture
        getEdge();
        Session session = sessionFactory.getCurrentSession();

        // Exercise SUT
        edgeDAO.save(firstEdge);
        Criteria criteria = session.createCriteria(Edge.class);
        Edge actualEdge = (Edge) criteria.uniqueResult();

        // Verify Behaviour
        assertThat(actualEdge, is(sameBeanAs(firstEdge)));
    }

    @Test
    public void testUpdateEdge() throws Exception {

        // Set up Fixture
        getEdge();

        // Exercise SUT
        edgeDAO.save(firstEdge);
        Edge expectedEdge = anEdge()
                .withRouteId(1)
                .withSource("C")
                .withDestination("D")
                .withDistance(5.0)
                .build();
        edgeDAO.update(expectedEdge);
        Edge actualEdge = edgeDAO.retrieveEdge(1);

        // Verify Behaviour
        assertThat(actualEdge, is(sameBeanAs(expectedEdge)));
    }

    @Test
    public void testDeleteEdge() throws Exception {

        // Set up Fixture
        getEdge();

        // Exercise SUT
        edgeDAO.save(firstEdge);
        int result = edgeDAO.delete(firstEdge.getRouteId());

        // Verify Behaviour
        assertThat(result, is(1));
    }

    @Test
    public void testRetrieveEdge() throws Exception {

        // Set up Fixture
        getEdge();

        // Exercise SUT
        Edge returnedEdge = edgeDAO.save(firstEdge);
        Edge actualEdge = edgeDAO.retrieveEdge(returnedEdge.getRouteId());

        // Verify Behaviour
        assertThat(actualEdge, is(sameBeanAs(firstEdge)
                .ignoring("routeId")));
    }

    public void getEdge() {
        firstEdge = anEdge()
                .withRouteId(1)
                .withSource("A")
                .withDestination("B")
                .withDistance(1.0)
                .build();
    }
}