package za.co.discovery.services;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.discovery.dataAccess.TrafficDAO;
import za.co.discovery.models.Traffic;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class TrafficService {
    private TrafficDAO trafficDAO;
    private final int numberOfEdges = 45;
    List<Traffic> trafficList = new ArrayList<>(numberOfEdges);

    @Autowired
    public TrafficService(TrafficDAO trafficDAO) {
        this.trafficDAO = trafficDAO;
    }

    public TrafficService() {
    }

    public Traffic getTrafficById(int routeId) {
        return trafficDAO.retrieveTraffic(routeId);
    }

    public void updateTraffic(Traffic traffic) {
        trafficDAO.update(traffic);
    }

    public int deleteTraffic(int routeId) {
        return trafficDAO.delete(routeId);
    }

    public List<Traffic> getTrafficList() {
        return trafficDAO.retrieveTrafficList();
    }

    public void persistTraffic(int routeId, String sourcePlanet, String destinationPlanet, Double distance) {
        Traffic traffic = new Traffic(routeId, sourcePlanet, destinationPlanet, distance);
        trafficDAO.save(traffic);
    }

    @PostConstruct
    public void readTraffic() {
        try {
            String fileName = new File("./").getCanonicalPath() + "\\src\\main\\resources\\PlanetData.xlsx";
//            FileInputStream file = new FileInputStream(new File("C:\\PlanetData.xlsx"));
            FileInputStream file = new FileInputStream(fileName);
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheet("Traffic");
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
                    Traffic traffic = new Traffic(routeId2, planetSource, planetDestination, planetDistance);
                    trafficList.add(traffic);
                    persistTraffic(routeId2, planetSource, planetDestination, planetDistance);
//                    edgeDAO.save(edge);
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
//        return trafficList;
    }
}
