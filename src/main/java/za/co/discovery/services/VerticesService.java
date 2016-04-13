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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

@Service
public class VerticesService {

    private final int numberOfVertices = 37;
    List<Vertex> vertexList = new Vector<>(numberOfVertices);
    private VertexDAO vertexDAO;


    @Autowired
    public VerticesService(VertexDAO vertexDAO) {
        this.vertexDAO = vertexDAO;
    }

    public Vertex getVertexByNode(String node) {
        return vertexDAO.retrieveVertex(node);
    }

    public void updateVertex(Vertex vertex) {
        vertexDAO.update(vertex);
    }

    public void deleteVertex(String node) {
        vertexDAO.delete(node);
    }

    public void persistVertex(Vertex planet) {
        vertexDAO.save(planet);
    }

    @PostConstruct
    public void readVertices() {
        try {
//            ClassLoader classLoader = this.getClass().getClassLoader();
//            File fileEx = new File(classLoader.getResource("PlanetData.xlsx").getFile());
//            FileInputStream file = new FileInputStream(fileEx);
            FileInputStream file = new FileInputStream(new File("C:\\PlanetData.xlsx"));
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
//                    System.out.print(planetNode + " " + planetName + "\n\n");
                    Vertex vertex = new Vertex(planetNode, planetName);
//                    vertex.setPlanetName(planetName);
                    vertexList.add(vertex);
                    persistVertex(vertex);
                    //vertexDAO.save(vertex);
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
//        return vertexList;
    }

}
