package za.co.discovery.models;

/**
 * Created by Ziyad.Jappie on 2016/04/06.
 */
public class VertexBuilder {
    private String planetName;
    private String node;

    private VertexBuilder() {
    }

    public static VertexBuilder aVertex() {
        return new VertexBuilder();
    }

    public VertexBuilder withPlanetName(String planetName) {
        this.planetName = planetName;
        return this;
    }

    public VertexBuilder withNode(String node) {
        this.node = node;
        return this;
    }

    public VertexBuilder but() {
        return aVertex().withPlanetName(planetName).withNode(node);
    }

    public Vertex build() {
        Vertex vertex = new Vertex(node);
        vertex.setPlanetName(planetName);
        return vertex;
    }
}
