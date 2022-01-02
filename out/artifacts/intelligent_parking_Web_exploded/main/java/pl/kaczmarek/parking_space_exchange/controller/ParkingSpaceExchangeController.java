package pl.kaczmarek.parking_space_exchange.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.ParseException;

import pl.kaczmarek.parking_space_exchange.dto.HireRequest;
import pl.kaczmarek.parking_space_exchange.dto.LandLordRequest;
import pl.kaczmarek.parking_space_exchange.service.HireService;
import pl.kaczmarek.parking_space_exchange.service.LandLordService;
import pl.kaczmarek.parking_space_exchange.service.UserService;

@Controller
public class ParkingSpaceExchangeController {

    @Autowired
    LandLordService landLordService;

    @Autowired
    HireService hireService;

    @Autowired
    UserService userService;

    @GetMapping("/showParkingExchangeForm")
    public String showParkingExchangeForm(){
        return "parking_space_exchange_management";
    }

    @GetMapping("/showAddLandLordForm")
    public String showAddLandLordForm(Model model){
        model.addAttribute("landLord",new LandLordRequest());
        return "add_land_lord";
    }

    @GetMapping("/showAddHireForm")
    public String showAddHireForm(Model model){
        model.addAttribute("hire",new HireRequest());
        return "add_hire";
    }

    @GetMapping("/getAllUsers")
    public String getAllUsers(Model model) throws ParseException {
        model.addAttribute("users",userService.getUserList());
        return "parking_space_exchange_users";
    }

    @PostMapping("/add-hire")
    public String addHire(HireRequest hireRequest) throws ParseException {
        hireService.addHire(hireRequest);
        return "parking_space_exchange_management";
    }

    @PostMapping("/add-land-lord")
    public String addLandLord(LandLordRequest landLordRequest) throws ParseException {
        landLordService.addLandLord(landLordRequest);
        return "parking_space_exchange_management";
    }

}
