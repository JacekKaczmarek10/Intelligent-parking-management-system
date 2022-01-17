package pl.kaczmarek;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import pl.kaczmarek.parking.model.ParkingEntity;
import pl.kaczmarek.parking.repository.ParkingRepository;
import pl.kaczmarek.parking.service.ParkingService;
import pl.kaczmarek.parking_place.model.ParkingPlaceEntity;
import pl.kaczmarek.parking_place.repository.ParkingPlaceRepository;

@Controller
public class MainController {


    @Autowired
    ParkingService parkingService;

    @Autowired
    ParkingRepository parkingRepository;

    @Autowired
    ParkingPlaceRepository parkingPlaceRepository;

    @GetMapping()
    public String showPage(){
        return "index";
    }

    @GetMapping("/welcome")
    public String showWelcomePage(){
        return "index";
    }

    @GetMapping("/contact")
    public String showContactPage(){
        return "contact";
    }

    @GetMapping("/about-us")
    public String showAboutUsPage(){
        return "about-us";
    }

    @GetMapping("/application")
    public String showApplicationPage(){
        return "application";
    }

    @GetMapping("/parking")
    public String showAwsPage(){
        return "parking";
    }

    @GetMapping("/parking-owner")
    public String showParkingOwnerPage(){
        return "parking-owner";
    }

    @GetMapping("/parking-driver")
    public String showParkingDriverPage(){
        return "parking-driver";
    }

    @GetMapping("/show-add-parking")
    public String showAddParking(){
        return "show-add-parking";
    }

    @GetMapping("/show-add-parking-photo")
    public String showAddParkingPhoto(){
        return "show-add-parking-photo";
    }


    @GetMapping("/display-parking")
    public String displayParking(Model model){
        model.addAttribute("parkings",parkingService.getAll());
        return "parking";
    }

    @GetMapping("/parking-list")
    public String displayParkingList(Model model){
        model.addAttribute("parkings",parkingService.getAll());
        return "parking_list";
    }

    @GetMapping("/getParkingDetails/{name}")
    public String getAllGroups(Model model, @PathVariable("name") String name){
        ParkingEntity parking = parkingRepository.getByName(name);
        if(parking==null){
            return "No parking with that name";
        }
        model.addAttribute("parkingData",parking);
        model.addAttribute("parkingPlaces",parkingPlaceRepository.getAllByParkingId(parking.getId()));
        model.addAttribute("name",
            "http://ec2-18-224-21-114.us-east-2.compute.amazonaws.com:8000/photo/" +parking.getName());
        model.addAttribute("numberOfFreePlaces",parkingPlaceRepository.getAllFreeByParkingId(parking.getId()).size());
        return "parking_data";
    }


    @GetMapping("/display-parking-details/{name}")
    public String displayParking(Model model, @PathVariable("name") String name){
        ParkingEntity parkingEntity = parkingRepository.getByName(name);
        List<ParkingPlaceEntity> list = parkingPlaceRepository.getAllByParkingId(parkingEntity.getId());
        model.addAttribute("parkingPlaces",list);
        return "parking_details";
    }
}
