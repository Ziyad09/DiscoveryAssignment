package za.co.discovery.services;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.discovery.dataAccess.EdgeDAO;
import za.co.discovery.models.Edge;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class EdgesService {
    private EdgeDAO edgeDAO;

    @Autowired
    public EdgesService(EdgeDAO edgeDAO) {
        this.edgeDAO = edgeDAO;
    }

    public List<Edge> getEdgeList() {
        return edgeDAO.retrieveEdgeList();
    }

    public Edge getEdgeById(int routeId) {
        return edgeDAO.retrieveEdge(routeId);
    }

    public void updateEdge(Edge edge) {
        edgeDAO.update(edge);
    }

    public int deleteEdge(int routeId) {
        return edgeDAO.delete(routeId);
    }
    public void persistEdge(Edge edge) {
        edgeDAO.save(edge);
    }
}
