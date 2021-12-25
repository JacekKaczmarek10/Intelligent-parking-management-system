package pl.kaczmarek;

import org.dozer.DozerBeanMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.ArrayList;

@SpringBootApplication
@EnableScheduling
@EnableCaching
public class IntelligentParkingApplication {

    @Bean
    public DozerBeanMapper dozerBeanMapper() throws Exception {
        DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();

        ArrayList<String> mappingFile = new ArrayList<String>();
        mappingFile.add("dozerMapping.xml");
        dozerBeanMapper.setMappingFiles(mappingFile);

        return dozerBeanMapper;
    }

    public static void main(String[] args) {
        SpringApplication.run(IntelligentParkingApplication.class, args);
    }


}
