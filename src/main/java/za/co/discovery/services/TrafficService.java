package za.co.discovery.services;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.discovery.dataAccess.TrafficDAO;
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

    public void persistTraffic(int routeId, Vertex sourcePlanet, Vertex destinationPlanet, Double distance) {
        Traffic traffic = new Traffic(routeId, sourcePlanet, destinationPlanet, distance);
        trafficDAO.save(traffic);
    }

    public void persistTrafficWithoutId(Vertex sourcePlanet, Vertex destinationPlanet, Double distance) {
        Traffic traffic = new Traffic(sourcePlanet, destinationPlanet, distance);
        trafficDAO.save(traffic);
    }
}
