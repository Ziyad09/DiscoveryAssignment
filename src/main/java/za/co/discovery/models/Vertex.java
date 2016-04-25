package za.co.discovery.models;

import javax.persistence.*;
import java.util.*;

import static java.util.Collections.singletonList;

@Entity(name = "Vertex")
@Table
public class Vertex implements Comparable<Vertex> {
    @Id
    @Column(nullable = false)
    private String node;
    @Column
    private String planetName;
    @Transient
    private double minDistance = 1000000;//Double.POSITIVE_INFINITY;
    @Transient
    private Vertex previous = null;
    @Transient
    private Map<Vertex, Double> neighbours = new HashMap<>();

    protected Vertex() {
    }

    public void setNeighbours(Vertex neighbour, double distance) {
        neighbours.put(neighbour, distance);
    }

    public double getMinDistance() {
        return minDistance;
    }

    public Vertex getPrevious() {
        return previous;
    }

    public void setPrevious(Vertex previous) {
        this.previous = previous;
    }

    public void setMinDistance(double minDistance) {
        this.minDistance = minDistance;
    }

    public Map<Vertex, Double> getNeighbours() {
        return neighbours;
    }

    public List<String> printPath() {
        List<String> thisNode = new LinkedList<>(singletonList(this.node));
        if (this == this.previous) {
            return thisNode;
        } else if (this.previous == null) {
//            System.out.printf("%s(unreached)", this.node);
            thisNode.add("");
            return thisNode;
        } else {
            List<String> pathToHere = this.previous.printPath();
            pathToHere.add(this.node);
            return pathToHere;
        }
//        return thisNode;
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
