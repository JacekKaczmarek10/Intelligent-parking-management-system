package pl.kaczmarek.parking.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Validation;
import javax.validation.constraints.NotNull;

import lombok.NonNull;
import pl.kaczmarek.parking.dto.ParkingRequest;
import pl.kaczmarek.parking.model.ParkingEntity;
import pl.kaczmarek.parking.model.ParkingStatus;
import pl.kaczmarek.parking.repository.ParkingRepository;
import pl.kaczmarek.parking.service.ParkingService;
import pl.kaczmarek.utils.ValidationService;
import pl.kaczmarek.utils.dozer.BooleanObject;
import pl.kaczmarek.utils.dozer.FloatObject;
import pl.kaczmarek.utils.dozer.IntegerObject;
import pl.kaczmarek.utils.dozer.StringObject;

@Controller
public class ParkingController {

    @Autowired
    ParkingService parkingService;

    @Autowired
    ParkingRepository parkingRepository;

    @Autowired
    ValidationService validationService;

    @Value("${filesPath}")
    private String filesPath;

    @GetMapping("/showParkingInstructionForm")
    public String showParkingInstructionForm(){
        return "/templates/parking_instruction.html";
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
            parkingEntity.setParkingStatus(ParkingStatus.UPLOADED);
            parkingRepository.save(parkingEntity);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return ResponseEntity.status(200).build();
    }

    @RequestMapping(value = "/add-parking", method = RequestMethod.POST)
    public ResponseEntity<Object> addParking(@Param("name")String name,
                                             @Param("city")String city,
                                             @Param("street")String street,
                                             @Param("postalCode")String postalCode,
                                             @Param("number") Integer number,
                                             @Param("lat") Float lat,
                                             @Param("lng")Float lng,
                                             @Param("numberOfPlaces")Integer numberOfPlaces,
                                             @Param("isGuarded")Boolean isGuarded){
        // validation
        List<String> errorList = new ArrayList<>();
        List<StringObject> stringObjectList = new ArrayList<>();
        List<IntegerObject> integerObjectList = new ArrayList<>();
        List<FloatObject> floatObjectList = new ArrayList<>();
        List<BooleanObject> boolObjectList = new ArrayList<>();

        stringObjectList.add(new StringObject("name",name));
        stringObjectList.add(new StringObject("city",city));
        stringObjectList.add(new StringObject("street",street));
        stringObjectList.add(new StringObject("postalCode",postalCode));

        integerObjectList.add(new IntegerObject("number",number));
        integerObjectList.add(new IntegerObject("numberOfPlaces",numberOfPlaces));

        floatObjectList.add(new FloatObject("lat",lat));
        floatObjectList.add(new FloatObject("lng",lng));

        boolObjectList.add(new BooleanObject("isGuarded",isGuarded));


        validationService.checkStringParameters(stringObjectList,errorList);
        validationService.checkIntegerParameters(integerObjectList,errorList);
        validationService.checkFloatParameters(floatObjectList,errorList);
        validationService.checkBooleanParameters(boolObjectList,errorList);
        if(errorList.size()>0){
            return ResponseEntity.status(400).body(errorList);
        }

        parkingService.addParking(new ParkingRequest(numberOfPlaces, city,name,street,postalCode,number,
            lat,lng,isGuarded));
        return ResponseEntity.status(200).build();
    }


}
