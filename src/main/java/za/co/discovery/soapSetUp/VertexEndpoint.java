package za.co.discovery.soapSetUp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import za.co.discovery.assignment.GetPathRequest;
import za.co.discovery.assignment.GetPathResponse;

@Endpoint
public class VertexEndpoint {
    private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";
    private VertexRepository vertexRepository;

    @Autowired
    public VertexEndpoint(VertexRepository vertexRepository) {
        this.vertexRepository = vertexRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getPathRequest")

    @ResponsePayload
    public GetPathResponse getPathResponse(@RequestPayload GetPathRequest request) {
        GetPathResponse response = new GetPathResponse();
        response.setPath(vertexRepository.findVertex(request.getName()).toString());

        return response;
    }
}
