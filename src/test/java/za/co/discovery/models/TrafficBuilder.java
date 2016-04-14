package za.co.discovery.models;

public class TrafficBuilder {
    private int routeId;
    private String source;
    private String destination;
    private double distance;

    private TrafficBuilder() {
    }

    public static TrafficBuilder aTraffic() {
        return new TrafficBuilder();
    }

    public TrafficBuilder withRouteId(int routeId) {
        this.routeId = routeId;
        return this;
    }

    public TrafficBuilder withSource(String source) {
        this.source = source;
        return this;
    }

    public TrafficBuilder withDestination(String destination) {
        this.destination = destination;
        return this;
    }

    public TrafficBuilder withDistance(double distance) {
        this.distance = distance;
        return this;
    }

    public TrafficBuilder but() {
        return aTraffic().withRouteId(routeId).withSource(source).withDestination(destination).withDistance(distance);
    }

    public Traffic build() {
        Traffic traffic = new Traffic(routeId, source, destination, distance);
        return traffic;
    }
}
