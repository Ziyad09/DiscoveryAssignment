package za.co.discovery.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class ResourceExcelFile {

    @Bean
    public InputStream resourceFile() throws IOException {
        String EXCEL_FILENAME = "/PlanetData.xlsx";
        ClassPathResource classPathResource = new ClassPathResource(EXCEL_FILENAME);
        return classPathResource.getInputStream();
    }
}
