package za.co.discovery.models;

import javax.persistence.*;
import java.util.*;

import static java.util.Collections.singletonList;

@Entity(name = "Vertex")
@Table
public class Vertex implements Comparable<Vertex> {
    // TODO create tests of vertex and edge and see if the behaviour is correct
    @Id
    @Column(nullable = false)
    private String node;
    @Column
    private String planetName;
    @Transient
    public double minDistance = 1000000;//Double.POSITIVE_INFINITY;
    @Transient
    public Vertex previous = null;
    @Transient
    Map<Vertex, Double> neighbours = new HashMap<>();

    protected Vertex() {
    }

    public List<String> printPath() {
        List<String> thisNode = new LinkedList<>(singletonList(this.node));
        if (this == this.previous) {
            return thisNode;
        } else {
            List<String> pathToHere = this.previous.printPath();
            pathToHere.add(this.node);
            return pathToHere;
        }
    }

    public int compareTo(Vertex other) {
        return Double.compare(minDistance, other.minDistance);
    }

    public Vertex(String node) {
        this.node = node;
    }

    public Vertex(String node, String planetName) {
        this.node = node;
        this.planetName = planetName;
    }

    public String getNode() {
        return node;
    }

    public String getPlanetName() {
        return planetName;
    }
}
