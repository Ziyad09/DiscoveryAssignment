package za.co.discovery.models;

import javax.persistence.*;

@Entity(name = "VertexInformation")
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

    public Vertex(String node) {
        this.node = node;
    }

    public String getNode() {
        return node;
    }

    public String getPlanetName(String planetName) {
        // Select planetName from 1st DB where planetName = 'planetName';
        return planetName;
    }

}
