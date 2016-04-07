package za.co.discovery.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "Vertex")
@Table
public class Vertex {
    @Id
    @Column(nullable = false)
    private String node;
    @Column
    private String planetName;

    protected Vertex() {

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
