package pl.kaczmarek;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
        return "/templates/page/index.html";
    }

    @GetMapping("/welcome")
    public String showWelcomePage(){
        return "/templates/page/index.html";
    }

    @GetMapping("/contact")
    public String showContactPage(){
        return "/templates/page/contact.html";
    }

    @GetMapping("/about-us")
    public String showAboutUsPage(){
        return "/templates/page/about-us.html";
    }

    @GetMapping("/parking")
    public String showAwsPage(){
        return "/templates/application/parking.html";
    }

    @GetMapping("/display-parking")
    public String displayParking(Model model){
        model.addAttribute("parkings",parkingService.getAll());
        return "/templates/application/parking.html";
    }


    @GetMapping("/display-parking-details/{name}")
    public String displayParking(Model model, @PathVariable("name") String name){
        ParkingEntity parkingEntity = parkingRepository.getByName(name);
        List<ParkingPlaceEntity> list = parkingPlaceRepository.getAllByParkingId(parkingEntity.getId());
        model.addAttribute("parkingPlaces",list);
        return "/templates/application/parking_details.html";
    }
}
