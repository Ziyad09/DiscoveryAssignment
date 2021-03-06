package za.co.discovery.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "Traffic")
@Table
public class Traffic {
    @Id
    @Column(nullable = false)
    private int routeId;
    @Column
    private String source;
    @Column
    private String destination;
    @Column
    private double distance;

    protected Traffic() {
    }

    public Traffic(int routeId, String source, String destination, double distance) {
        this.routeId = routeId;
        this.source = source;
        this.destination = destination;
        this.distance = distance;
    }

    public int getRouteId() {
        return routeId;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public double getDistance() {
        return distance;
    }
}
