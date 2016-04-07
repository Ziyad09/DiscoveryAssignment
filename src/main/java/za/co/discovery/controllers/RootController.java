package za.co.discovery.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import za.co.discovery.models.Graph;
import za.co.discovery.services.EdgesService;
import za.co.discovery.services.VerticesService;

public class RootController {

    private EdgesService edge;
    private VerticesService vertex;
    private Graph graph = null;

    @Autowired
    public RootController(EdgesService edge, VerticesService vertex) {
        this.edge = edge;
        this.vertex = vertex;
    }

    @RequestMapping("/")
    public String home() {
        return "index";
    }

}
