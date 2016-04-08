package za.co.discovery.models;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity(name = "Vertex")
@Table
public class Vertex implements Comparable<Vertex> {
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

    public void printPath() {
        if (this == this.previous) {
//            System.out.printf("%s", this.node);
            System.out.print(this.node + " ");
        } else if (this.previous == null) {
            System.out.printf("%s(unreached)", this.node);
        } else {
            this.previous.printPath();
//            System.out.printf(" -> %s(%f)", this.node, this.minDistance);
            System.out.print(this.node + " ");
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
        // Select planetName from 1st DB where planetName = 'planetName';
        return planetName;
    }
}
