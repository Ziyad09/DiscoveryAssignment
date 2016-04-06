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
import za.co.discovery.DataSourceConfig;
import za.co.discovery.PersistenceConfig;
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
//@ActiveProfiles("test")
public class VertexDAOTest {

    @Autowired
    private SessionFactory sessionFactory;

    //@Autowired
    private VertexDAO vertexDAO;

    @Before
    public void init() {
        vertexDAO = new VertexDAO(sessionFactory);
    }

    @Test
    public void testSave() throws Exception {
        Vertex expectedVertex = aVertex()
                .withNode("A")
                .build();
        Session session = sessionFactory.getCurrentSession();
        vertexDAO.save(expectedVertex);
        Criteria criteria = session.createCriteria(Vertex.class);

        Vertex actualEdge = (Vertex) criteria.uniqueResult();
        assertThat(actualEdge, is(sameBeanAs(expectedVertex)
                .ignoring("vertexId")));
    }

    @Test
    public void testRetrieveVertex() throws Exception {
        Vertex vertex = aVertex()
                .withNode("A")
                .withPlanetName("Earth")
                .build();
        Vertex returnedVertex = vertexDAO.save(vertex);
        System.out.print(returnedVertex.getPlanetName(returnedVertex.getNode()) + "\n\n\n" + returnedVertex.getVertexId() + "\n\n\n");
        Vertex actualVertex = vertexDAO.retrieveVertex(((Long) returnedVertex.getVertexId()).intValue());
        System.out.print(actualVertex.getPlanetName(returnedVertex.getNode()) + "\n\n\n" + actualVertex.getVertexId() + "\n\n\n");

        MatcherAssert.assertThat(actualVertex, is(sameBeanAs(vertex)
                .ignoring("vertexId")));

    }
}