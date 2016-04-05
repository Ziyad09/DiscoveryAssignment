package za.co.discovery.controllers;

import org.springframework.web.bind.annotation.RequestMapping;

public class RootController {

    @RequestMapping("/")
    public String home() {
        return "index";
    }
}
