package za.co.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class DiscoveryApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiscoveryApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public InputStream resourceFile() throws URISyntaxException, IOException {
        String EXCEL_FILENAME = "/PlanetData.xlsx";
        ClassPathResource classPathResource = new ClassPathResource(EXCEL_FILENAME);
//        URL resource = getClass().getResource(EXCEL_FILENAME);
        classPathResource.getInputStream();
        return classPathResource.getInputStream();
    }
}
