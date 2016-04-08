package za.co.discovery.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "Edge")
@Table
public class Edge {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
//    private int edgeId;
//    @Column
    private int routeId;
    @Column
    private String source;
    @Column
    private String destination;
    @Column
    private double distance;

    //    private Vertex target;
    protected Edge() {

    }

    public Edge(int routeId, String source, String destination, double distance) {
        this.routeId = routeId;
        this.source = source;
        this.destination = destination;
        this.distance = distance;
    }

    public Edge(String source, String destination, double distance) {
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
//    public Vertex getTarget() {
//        return target;
//    }

    public double getDistance() {
        return distance;
    }


}
