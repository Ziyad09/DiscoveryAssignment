package za.co.discovery.services;

import org.apache.poi.ss.usermodel.Cell;
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
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

@Service
public class VerticesService {

    private final int numberOfVertices = 50;
    public List<Vertex> vertexList = new Vector<>(numberOfVertices);
    private VertexDAO vertexDAO;


    @Autowired
    public VerticesService(VertexDAO vertexDAO) {
        this.vertexDAO = vertexDAO;
    }

    public Vertex getVertexByNode(String node) {
        return vertexDAO.retrieveVertex(node);
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

    @PostConstruct
    public void readVertices() {
        try {
            String EXCEL_FILENAME = "/PlanetData.xlsx";
            URL resource = getClass().getResource(EXCEL_FILENAME);
            File file1 = new File(resource.toURI());
            FileInputStream file = new FileInputStream(file1);
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheet("Planet Names");
            int rowStart = Math.min(1, 1); // 0 based not 1 based rows
            int rowEnd = Math.max(12, sheet.getLastRowNum());
            for (int rowNum = rowStart; rowNum < rowEnd + 1; rowNum++) {
                Row row = sheet.getRow(rowNum);
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    String planetNode = row.getCell(0).getStringCellValue();
                    String planetName = row.getCell(1).getStringCellValue();
                    Vertex vertex = new Vertex(planetNode, planetName);
                    vertexList.add(vertex);
                    persistVertex(vertex);
                    break;
                }
            }
            file.close();

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

}
