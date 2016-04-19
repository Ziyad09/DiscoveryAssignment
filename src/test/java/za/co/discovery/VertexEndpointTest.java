package za.co.discovery;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;
import za.co.discovery.assignment.GetPathRequest;
import za.co.discovery.assignment.GetPathResponse;
import za.co.discovery.dataAccess.EdgeDAO;
import za.co.discovery.dataAccess.VertexDAO;
import za.co.discovery.services.EdgesService;
import za.co.discovery.services.VerticesService;

import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {PersistenceConfig.class, VertexRepository.class, VertexEndpoint.class, DataSourceConfig.class, DAOConfig.class, VerticesService.class, EdgesService.class, EdgeDAO.class, VertexDAO.class},
        loader = AnnotationConfigContextLoader.class)
public class VertexEndpointTest {

    @Autowired
    VertexEndpoint vertexEndpoint;

    @Mock
    VertexRepository vertexRepository;

    @Test
    public void testGetPathResponse() throws Exception {
        GetPathRequest pathRequest = new GetPathRequest();
        pathRequest.setName("D");
        GetPathResponse expectedResponse = new GetPathResponse();
        expectedResponse.setPath("Earth Venus ");
        GetPathResponse actualResponse = vertexEndpoint.getPathResponse(pathRequest);
        assertThat(actualResponse, is(sameBeanAs(expectedResponse)));
    }
}