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
import za.co.discovery.dataAccess.VertexDAO;
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
public class VerticesServiceTest {
    @Autowired
    private VertexDAO vertexDAO;
    private Vertex firstVertex;
    private VerticesService verticesService;

    @Before
    public void init() {
        verticesService = new VerticesService(vertexDAO);
    }

    @Test
    public void testGetVertexByNodeShouldReturnVertex() throws Exception {
        getVertex();
        verticesService.persistVertex(firstVertex);
        Vertex actualVertex = verticesService.getVertexByNode(firstVertex.getNode());
        assertThat(actualVertex, is(sameBeanAs(firstVertex)));
    }

    @Test
    public void testUpdateVertexShouldUpdateDBWithSameNode() throws Exception {
        firstVertex = aVertex()
                .withNode("A")
                .withPlanetName("Earth")
                .build();
        verticesService.persistVertex(firstVertex);
        Vertex expectedVertex = aVertex()
                .withPlanetName("Pluto")
                .withNode("A")
                .build();
        verticesService.updateVertex(expectedVertex);
        Vertex actualVertex = verticesService.getVertexByNode(expectedVertex.getNode());
        assertThat(actualVertex, is(sameBeanAs(expectedVertex)));
    }

    @Test
    public void testDeleteVertex() throws Exception {
        getVertex();
        verticesService.persistVertex(firstVertex);
        int affectedRows = verticesService.deleteVertex(firstVertex.getNode());
        assertThat(affectedRows, is(1));
    }

    public void getVertex() {
        firstVertex = aVertex()
                .withNode("A")
                .withPlanetName("Earth")
                .build();
    }
}