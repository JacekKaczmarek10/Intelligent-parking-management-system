package pl.kaczmarek.parking.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import pl.kaczmarek.parking.dto.ParkingRequest;
import pl.kaczmarek.parking.service.ParkingService;

@Controller
public class ParkingController {

    @Autowired
    ParkingService parkingService;

    @Value("${filesPath}")
    private String filesPath;

    public String getFileExtension(MultipartFile file){
        String extension = "";

        int i = file.getOriginalFilename().lastIndexOf('.');
        if (i > 0) {
            extension = file.getOriginalFilename().substring(i+1);
        }
        return ("." + extension);
    }


    @RequestMapping(value = "/add-parking-image", method = RequestMethod.POST,
        headers = "Content-Type=multipart/form-data")
    public ResponseEntity<Object> addParkingImage(@RequestParam("file") MultipartFile file){
        System.out.println(file.getOriginalFilename());

        String fileName = file.getOriginalFilename();
        Path path = Paths.get(filesPath + fileName);
        String extention = getFileExtension(file);

        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            ex.printStackTrace();
        }


        return ResponseEntity.status(200).build();
    }


    @RequestMapping(value = "/add-parking", method = RequestMethod.POST)
    public ResponseEntity<Object> addParking(@Param("numberOfPlaces")int numberOfPlaces,
                                             @Param("city")String city,
                                             @Param("name")String name,
                                             @Param("street")String street,
                                             @Param("postalCode")String postalCode){
        parkingService.addParking(new ParkingRequest(numberOfPlaces, city,name,street,postalCode));
        return ResponseEntity.status(200).build();
    }


}
