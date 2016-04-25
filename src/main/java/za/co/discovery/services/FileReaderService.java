package za.co.discovery.services;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.discovery.models.Edge;
import za.co.discovery.models.Traffic;
import za.co.discovery.models.Vertex;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

@Service
public class FileReaderService {
    private VerticesService verticesService;
    private EdgesService edgesService;
    private TrafficService trafficService;

    @Autowired
    public FileReaderService(VerticesService verticesService, EdgesService edgesService, TrafficService trafficService) {
        this.verticesService = verticesService;
        this.edgesService = edgesService;
        this.trafficService = trafficService;
    }

    @PostConstruct
    public void readVertices() {
        try {
            String EXCEL_FILENAME = "/PlanetData.xlsx";
            URL resource = getClass().getResource(EXCEL_FILENAME);
            File file1 = new File(resource.toURI());
            FileInputStream file = new FileInputStream(file1);
            XSSFWorkbook workbook = new XSSFWorkbook(file);

// TODO Mixing implementation and config!! Try Resource Bean, will be in config, in discovery app
            XSSFSheet sheetVertex = workbook.getSheet("Planet Names");
            int rowStartVertex = Math.min(1, 1); // 0 based not 1 based rows
            int rowEndVertex = Math.max(12, sheetVertex.getLastRowNum());
            for (int rowNum = rowStartVertex; rowNum < rowEndVertex + 1; rowNum++) {
                Row row = sheetVertex.getRow(rowNum);
                String planetNode = row.getCell(0).getStringCellValue();
                String planetName = row.getCell(1).getStringCellValue();
                Vertex vertex = new Vertex(planetNode, planetName);
                verticesService.persistVertex(vertex);
            }

            XSSFSheet sheetEdge = workbook.getSheet("Routes");
            int rowStartEdge = Math.min(1, 1); // 0 based not 1 based rows
            int rowEndEdge = Math.max(12, sheetEdge.getLastRowNum());
            for (int rowNum = rowStartEdge; rowNum < rowEndEdge + 1; rowNum++) {
                Row r = sheetEdge.getRow(rowNum);
                Double routeId = r.getCell(0).getNumericCellValue();
                String planetSource = r.getCell(1).getStringCellValue();
                String planetDestination = r.getCell(2).getStringCellValue();
                Double planetDistance = r.getCell(3).getNumericCellValue();
                int routeId2 = routeId.intValue();

                Vertex source = verticesService.getVertexByNode(planetSource);
                Vertex destination = verticesService.getVertexByNode(planetDestination);

                if (source != null && destination != null) {
                    Edge edge = new Edge(routeId2, source, destination, planetDistance);
                    edgesService.persistEdge(edge);
                }
            }

            XSSFSheet sheetTraffic = workbook.getSheet("Traffic");
            int rowStartTraffic = Math.min(1, 1); // 0 based not 1 based rows
            int rowEndTraffic = Math.max(12, sheetTraffic.getLastRowNum());
            for (int rowNum = rowStartTraffic; rowNum < rowEndTraffic + 1; rowNum++) {
                Row r = sheetTraffic.getRow(rowNum);
                Double routeId = r.getCell(0).getNumericCellValue();
                String planetSource = r.getCell(1).getStringCellValue();
                String planetDestination = r.getCell(2).getStringCellValue();
                Double planetDistance = r.getCell(3).getNumericCellValue();
                int routeId2 = routeId.intValue();

                Vertex source = verticesService.getVertexByNode(planetSource);
                Vertex destination = verticesService.getVertexByNode(planetDestination);

                if (source != null && destination != null) {
                    trafficService.persistTraffic(routeId2, source, destination, planetDistance);
                }

            }


            file.close();

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }


    }

}
