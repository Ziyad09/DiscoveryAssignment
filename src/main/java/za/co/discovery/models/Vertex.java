package za.co.discovery.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "Vertex")
@Table
public class Vertex {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
//    private int vertexId;
//    @Column
    private String node;
    @Column
    private String planetName;

    protected Vertex() {

    }

    //    public Vertex(String node) {
//        this.node = node;
//    }
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
//    public long getVertexId() {
//        return vertexId;
//    }
//    public void setVertexId(int vertexId){
//        this.vertexId = vertexId;
//    }
}
