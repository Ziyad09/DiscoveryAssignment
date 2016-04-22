package za.co.discovery.models;

/**
 * Created by Ziyad.Jappie on 2016/04/06.
 */
public class EdgeBuilder {
    private int routeId;
    private Vertex source;
    private Vertex destination;
    private double distance;

    private EdgeBuilder() {
    }

    public static EdgeBuilder anEdge() {
        return new EdgeBuilder();
    }

    public EdgeBuilder withRouteId(int routeId) {
        this.routeId = routeId;
        return this;
    }

    public EdgeBuilder withSource(Vertex source) {
        this.source = source;
        return this;
    }

    public EdgeBuilder withDestination(Vertex destination) {
        this.destination = destination;
        return this;
    }

    public EdgeBuilder withDistance(double distance) {
        this.distance = distance;
        return this;
    }

    public EdgeBuilder but() {
        return anEdge().withRouteId(routeId).withSource(source).withDestination(destination).withDistance(distance);
    }

    public Edge build() {
        Edge edge = new Edge(routeId, source, destination, distance);
        return edge;
    }
}
