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
    private final int numberOfEdges = 60;
    List<Edge> edgeList = new ArrayList<>(numberOfEdges);

    @Autowired
    public EdgesService(EdgeDAO edgeDAO) {
        this.edgeDAO = edgeDAO;
    }

    public EdgesService() {
    }
//    public List<Edge> getEdges() {
//        return readEdges();
//    }

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

    @PostConstruct
    public void readEdges() {
        try {
            String EXCEL_FILENAME = "/PlanetData.xlsx";
            URL resource = getClass().getResource(EXCEL_FILENAME);
            File file1 = new File(resource.toURI());
//            String fileName = new File("./").getCanonicalPath() + "\\src\\main\\resources\\PlanetData.xlsx";
//            FileInputStream file = new FileInputStream(new File("C:\\PlanetData.xlsx"));
            FileInputStream file = new FileInputStream(file1);
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheet("Routes");
            int rowStart = Math.min(1, 1); // 0 based not 1 based rows
            int rowEnd = Math.max(12, sheet.getLastRowNum());
            for (int rowNum = rowStart; rowNum < rowEnd + 1; rowNum++) {
                Row r = sheet.getRow(rowNum);
                Iterator<Cell> cellIterator = r.cellIterator();
                while (cellIterator.hasNext()) {
                    Double routeId = r.getCell(0).getNumericCellValue();
                    String planetSource = r.getCell(1).getStringCellValue();
                    String planetDestination = r.getCell(2).getStringCellValue();
                    Double planetDistance = r.getCell(3).getNumericCellValue();
                    int routeId2 = routeId.intValue();
                    Edge edge = new Edge(routeId2, planetSource, planetDestination, planetDistance);
                    edgeList.add(edge);
                    persistEdge(edge);
                    break;
                }
            }
            file.close();

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
//        return edgeList;
    }
}
