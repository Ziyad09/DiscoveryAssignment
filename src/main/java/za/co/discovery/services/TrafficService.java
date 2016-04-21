package za.co.discovery.services;

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
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

@Service
public class TrafficService {
    private TrafficDAO trafficDAO;

    @Autowired
    public TrafficService(TrafficDAO trafficDAO) {
        this.trafficDAO = trafficDAO;
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
            String EXCEL_FILENAME = "/PlanetData.xlsx";
            URL resource = getClass().getResource(EXCEL_FILENAME);
            File file1 = new File(resource.toURI());
            FileInputStream file = new FileInputStream(file1);
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheet("Traffic");
            int rowStart = Math.min(1, 1); // 0 based not 1 based rows
            int rowEnd = Math.max(12, sheet.getLastRowNum());
            for (int rowNum = rowStart; rowNum < rowEnd + 1; rowNum++) {
                Row r = sheet.getRow(rowNum);
                    Double routeId = r.getCell(0).getNumericCellValue();
                    String planetSource = r.getCell(1).getStringCellValue();
                    String planetDestination = r.getCell(2).getStringCellValue();
                    Double planetDistance = r.getCell(3).getNumericCellValue();
                    int routeId2 = routeId.intValue();
                    persistTraffic(routeId2, planetSource, planetDestination, planetDistance);
            }
            file.close();

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
