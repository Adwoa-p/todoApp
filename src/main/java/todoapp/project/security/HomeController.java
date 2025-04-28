package todoapp.project.security;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping
    public String home(){
        return "Hello";
    }

    @GetMapping("/security")
    public  String secured(){
        return "Secured";
    }

}
