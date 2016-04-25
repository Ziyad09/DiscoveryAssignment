package za.co.discovery.models;

import javax.persistence.*;

@Entity(name = "Traffic")
@Table
public class Traffic {
    @Id
    @Column(nullable = false)
    private int routeId;
    @OneToOne(fetch = FetchType.EAGER)
    private Vertex source;
    @OneToOne(fetch = FetchType.EAGER)
    private Vertex destination;
    @Column
    private double distance;

    protected Traffic() {
    }

    public Traffic(int routeId, Vertex source, Vertex destination, double distance) {
        this.routeId = routeId;
        this.source = source;
        this.destination = destination;
        this.distance = distance;
    }

    public Traffic(Vertex source, Vertex destination, double distance) {
        this.source = source;
        this.destination = destination;
        this.distance = distance;
    }

    public int getRouteId() {
        return routeId;
    }

    public Vertex getSource() {
        return source;
    }

    public Vertex getDestination() {
        return destination;
    }

    public double getDistance() {
        return distance;
    }
}
