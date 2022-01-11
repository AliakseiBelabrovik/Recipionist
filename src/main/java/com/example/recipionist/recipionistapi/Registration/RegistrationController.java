package com.example.recipionist.recipionistapi.Registration;


import com.example.recipionist.recipionistapi.Controllers.TemplateController;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "registration")
@AllArgsConstructor
public class RegistrationController {

    private final TemplateController templateController;
    private final RegistrationService registrationService;

    //we take information (see RegistrationRequest) to register a person
    @RequestMapping(method = RequestMethod.POST)
    public String register(@RequestBody RegistrationRequest request){
        //return registrationService.register(request);
        registrationService.register(request);
        return "redirect:/main";
    }

    //we send token as parameter token?=xxxxxx
    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        System.out.println("token" + token);
        return registrationService.confirmToken(token);
    }



}
