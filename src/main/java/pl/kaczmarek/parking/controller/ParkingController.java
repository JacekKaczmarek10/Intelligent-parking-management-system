package pl.kaczmarek.parking.controller;


import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
import pl.kaczmarek.utils.EmailService;
import pl.kaczmarek.utils.TypeConversionUtils;
import pl.kaczmarek.utils.ValidationObject;
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

    @Autowired
    EmailService emailService;

    @Value("${uploadedImagesPath}")
    private String uploadedImagesPath;

    @Value("${editedImagesPath}")
    private String editedImagesPath;

    @GetMapping("/showParkingInstructionForm")
    public String showParkingInstructionForm(){
        return "/templates/parking_instruction.html";
    }

    @RequestMapping(value = "/add-parking-image",
        method = RequestMethod.POST,
        headers = "Content-Type=multipart/form-data")
    public String addParkingImage(@RequestParam("file") MultipartFile file,
                                                  @RequestParam("name")String name){
        ParkingEntity parkingEntity = parkingRepository.getByName(name);
        if(parkingEntity==null){
            return ResponseEntity.status(400).build().toString();
        }
        String fileName = name + TypeConversionUtils.getFileExtension(file);
        Path path = Paths.get(uploadedImagesPath + fileName);
        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            parkingEntity.setParkingStatus(ParkingStatus.UPLOADED);
            parkingEntity.setUploadPath(fileName);
            parkingRepository.save(parkingEntity);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return "show-add-parking-photo";
    }

    @RequestMapping(value = "/add-edited-parking-image",
        method = RequestMethod.POST,
        headers = "Content-Type=multipart/form-data")
    public String addEditedParkingImage(@RequestParam("file") MultipartFile file,
                                                  @RequestParam("name")String name){
        ParkingEntity parkingEntity = parkingRepository.getByName(name);
        if(parkingEntity==null){
            return ResponseEntity.status(400).build().toString();
        }
        String fileName = file.getOriginalFilename();
        Path path = Paths.get(editedImagesPath + fileName);
        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            parkingEntity.setImagePath(fileName);
            parkingEntity.setParkingStatus(ParkingStatus.SCANNED);
            parkingRepository.save(parkingEntity);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return "show-add-parking-photo";
    }

    @GetMapping(value = "/photo/{parkingName}")
    public ResponseEntity<Object> getImage (@PathVariable String parkingName) throws IOException {
        ParkingEntity parking = parkingRepository.getByName(parkingName);
        InputStream in = new FileInputStream(uploadedImagesPath +parking.getUploadPath());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(MediaType.IMAGE_JPEG_VALUE));
        return new ResponseEntity<Object>(IOUtils.toByteArray(in),headers, HttpStatus.OK);
    }


    @RequestMapping(value = "/add-parking", method = RequestMethod.POST)
    public ResponseEntity<Object> addParking(@Param("email")String email,
                                             @Param("name")String name,
                                             @Param("city")String city,
                                             @Param("street")String street,
                                             @Param("postalCode")String postalCode,
                                             @Param("number") Integer number,
                                             @Param("phoneNumber") String phoneNumber,
                                             @Param("isGuarded")Boolean isGuarded,
                                             @Param("numberOfPlaces")Integer numberOfPlaces
                                             ){
        // validation
        ValidationObject validationObject = new ValidationObject();
        List<StringObject> stringObjectList = new ArrayList<>();
        List<IntegerObject> integerObjectList = new ArrayList<>();
        List<FloatObject> floatObjectList = new ArrayList<>();
        List<BooleanObject> boolObjectList = new ArrayList<>();

        stringObjectList.add(new StringObject("name",name));
        stringObjectList.add(new StringObject("email",email));

        stringObjectList.add(new StringObject("city",city));
        stringObjectList.add(new StringObject("street",street));
        stringObjectList.add(new StringObject("postalCode",postalCode));
        integerObjectList.add(new IntegerObject("number",number));

        stringObjectList.add(new StringObject("phoneNumber",phoneNumber));
        boolObjectList.add(new BooleanObject("isGuarded",isGuarded));
        integerObjectList.add(new IntegerObject("numberOfPlaces",numberOfPlaces));


        validationService.checkStringParameters(stringObjectList,validationObject);
        validationService.checkIntegerParameters(integerObjectList,validationObject.getErrorList());
        validationService.checkFloatParameters(floatObjectList,validationObject.getErrorList());
        validationService.checkBooleanParameters(boolObjectList,validationObject.getErrorList());
        if(validationObject.getErrorList().size()>0){
            return ResponseEntity.status(400).body(validationObject);
        }

        parkingService.addParking(new ParkingRequest(name,email,city,street,postalCode,number,phoneNumber,isGuarded,numberOfPlaces));
        emailService.sendRegisterEmail(email,name);
        System.out.println("PARKING WAS ADDED");
        return ResponseEntity.status(200).build();
    }


}
