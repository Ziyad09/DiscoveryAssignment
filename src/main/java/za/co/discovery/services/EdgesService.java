package za.co.discovery.services;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.discovery.dataAccess.EdgeDAO;
import za.co.discovery.models.Edge;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class EdgesService {
    private EdgeDAO edgeDAO;
    private final int numberOfEdges = 45;
    List<Edge> edgeList = new ArrayList<>(numberOfEdges);

    @Autowired
    public EdgesService(EdgeDAO edgeDAO) {
        this.edgeDAO = edgeDAO;
    }

    public EdgesService() {
    }
    public List<Edge> getEdges() {
        return readEdges();
    }

    public Edge getEdgeById(int routeId) {
        return edgeDAO.retrieveEdge(routeId);
    }

    public void updateEdge(Edge edge) {
        edgeDAO.update(edge);
    }

    public void deleteEdge(int routeId) {
        edgeDAO.delete(routeId);
    }

    public void persistEdge(int routeId, String sourcePlanet, String destinationPlanet, Double distance) {
        Edge edge = new Edge(routeId, sourcePlanet, destinationPlanet, distance);
        edgeDAO.save(edge);
    }

    private List<Edge> readEdges() {
        try {

            FileInputStream file = new FileInputStream(new File("C:\\PlanetData.xlsx"));
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
                    int routeId2 = Integer.valueOf(routeId.intValue());
                    Edge edge = new Edge(routeId2, planetSource, planetDestination, planetDistance);
                    edgeList.add(edge);
//                    persistEdge(routeId2, planetSource, planetDestination, planetDistance);
                    edgeDAO.save(edge);
//                    System.out.print(routeId + " " + planetSource + "\n\n");
                    break;
                }
//                System.out.println("");
            }
            file.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return edgeList;
    }
}
