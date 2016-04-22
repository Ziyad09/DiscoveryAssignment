package za.co.discovery.services;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.discovery.dataAccess.VertexDAO;
import za.co.discovery.models.Vertex;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

@Service
public class VerticesService {

    private VertexDAO vertexDAO;

    @Autowired
    public VerticesService(VertexDAO vertexDAO) {
        this.vertexDAO = vertexDAO;
    }

    public Vertex getVertexByNode(String node) {
        return vertexDAO.retrieveVertex(node);
    }

    public Vertex getVertexByPlanetName(String planetName) {
        return vertexDAO.retrieveVertexByName(planetName);
    }

    public Vertex updateVertex(Vertex vertex) {
        return vertexDAO.update(vertex);
    }

    public int deleteVertex(String node) {
        return vertexDAO.delete(node);
    }

    public void persistVertex(Vertex planet) {
        vertexDAO.save(planet);
    }

    public List<Vertex> getVertexList() {
        return vertexDAO.retrieveVertexList();
    }


}
