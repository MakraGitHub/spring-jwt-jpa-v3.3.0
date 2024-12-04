package co.prime.jpa.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("anonymous")
public class UnSecuredController {

    @GetMapping("/test")
    public String test(){
        return "For anonymous";
    }

}
