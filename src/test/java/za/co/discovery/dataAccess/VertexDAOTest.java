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
import za.co.discovery.configuration.DataSourceConfig;
import za.co.discovery.configuration.PersistenceConfig;
import za.co.discovery.models.Vertex;

import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static za.co.discovery.models.VertexBuilder.aVertex;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {PersistenceConfig.class, DataSourceConfig.class, VertexDAO.class},
        loader = AnnotationConfigContextLoader.class)
public class VertexDAOTest {

    @Autowired
    private SessionFactory sessionFactory;
    private VertexDAO vertexDAO;
    private Vertex firstVertex;

    @Before
    public void init() {
        vertexDAO = new VertexDAO(sessionFactory);
    }

    @Test
    public void testUpdateVertex() throws Exception {
        getVertex();
        vertexDAO.save(firstVertex);
        Vertex expectedVertex = aVertex()
                .withPlanetName("Pluto")
                .withNode("A")
                .build();
        Vertex actualVertexUpdate = vertexDAO.update(expectedVertex);
        assertThat(actualVertexUpdate, is(sameBeanAs(expectedVertex)));
    }

    @Test
    public void testSave() throws Exception {
        getVertex();
        Session session = sessionFactory.getCurrentSession();
        vertexDAO.save(firstVertex);
        Criteria criteria = session.createCriteria(Vertex.class);
        Vertex actualEdge = (Vertex) criteria.uniqueResult();
        assertThat(actualEdge, is(sameBeanAs(firstVertex)));
    }

    @Test
    public void testDeleteVertex() throws Exception {

        // Set up Fixture
        getVertex();

        // Exercise SUT
        vertexDAO.save(firstVertex);
        int result = vertexDAO.delete(firstVertex.getNode());

        // Verify Behaviour
        assertThat(result, is(1));
    }


    @Test
    public void testRetrieveVertex() throws Exception {
        getVertex();
        Vertex returnedVertex = vertexDAO.save(firstVertex);
        Vertex actualVertex = vertexDAO.retrieveVertex(returnedVertex.getNode());
        assertThat(actualVertex, is(sameBeanAs(firstVertex)));
    }

    public void getVertex() {
        firstVertex = aVertex()
                .withNode("A")
                .withPlanetName("Earth")
                .build();
    }
}