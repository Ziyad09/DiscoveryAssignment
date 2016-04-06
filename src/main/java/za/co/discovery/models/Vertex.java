package za.co.discovery.models;

import javax.persistence.*;

@Entity(name = "Vertex")
@Table
public class Vertex {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int vertexId;
    @Column
    private String node;
    @Column
    private String planetName;

    protected Vertex() {

    }

    public Vertex(String node) {
        this.node = node;
    }

    public String getNode() {
        return node;
    }

    public String getPlanetName(String node) {
        // Select planetName from 1st DB where planetName = 'planetName';
        return planetName;
    }

    public void setPlanetName(String planetName) {
        this.planetName = planetName;
    }

    public long getVertexId() {
        return vertexId;
    }
}
