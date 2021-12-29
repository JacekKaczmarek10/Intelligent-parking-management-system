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
import pl.kaczmarek.parking.model.ParkingEntity;
import pl.kaczmarek.parking.repository.ParkingRepository;
import pl.kaczmarek.parking.service.ParkingService;

@Controller
public class ParkingController {

    @Autowired
    ParkingService parkingService;

    @Autowired
    ParkingRepository parkingRepository;

    @Value("${filesPath}")
    private String filesPath;

    @GetMapping("/showParkingInstructionForm")
    public String showParkingInstructionForm(){
        return "parking_instruction";
    }

    @RequestMapping(value = "/add-parking-image",
        method = RequestMethod.POST,
        headers = "Content-Type=multipart/form-data")
    public ResponseEntity<Object> addParkingImage(@RequestParam("file") MultipartFile file,
                                                  @RequestParam("name")String name){
        ParkingEntity parkingEntity = parkingRepository.getByName(name);
        if(parkingEntity==null){
            return ResponseEntity.status(400).build();
        }
        String fileName = file.getOriginalFilename();
        Path path = Paths.get(filesPath + fileName);
        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            parkingEntity.setImagePath(fileName);
            parkingRepository.save(parkingEntity);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return ResponseEntity.status(200).build();
    }

    @RequestMapping(value = "/add-parking", method = RequestMethod.POST)
    public ResponseEntity<Object> addParking(@Param("city")String city,
                                             @Param("name")String name,
                                             @Param("street")String street,
                                             @Param("postalCode")String postalCode){
        parkingService.addParking(new ParkingRequest(0, city,name,street,postalCode));
        return ResponseEntity.status(200).build();
    }


}
