package za.co.discovery.models;

/**
 * Created by Ziyad.Jappie on 2016/04/08.
 */
public class VertexBuilder {
    private String node;
    private String planetName;

    private VertexBuilder() {
    }

    public static VertexBuilder aVertex() {
        return new VertexBuilder();
    }

    public VertexBuilder withNode(String node) {
        this.node = node;
        return this;
    }

    public VertexBuilder withPlanetName(String planetName) {
        this.planetName = planetName;
        return this;
    }

    public VertexBuilder but() {
        return aVertex().withNode(node).withPlanetName(planetName);
    }

    public Vertex build() {
        Vertex vertex = new Vertex(node, planetName);
        return vertex;
    }
}
