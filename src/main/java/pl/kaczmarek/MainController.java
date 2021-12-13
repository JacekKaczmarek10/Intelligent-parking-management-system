package pl.kaczmarek;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {


    @GetMapping()
    public String showPage(){
        return "index";
    }

    @GetMapping("/parking")
    public String showAwsPage(){
        return "parking";
    }
}
