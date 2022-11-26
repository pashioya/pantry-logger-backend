package int3.team2.website.pantry_loogr.presentation;

import int3.team2.website.pantry_loogr.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class IndexController {

    private Logger logger;

    public IndexController(UserService userService) {
        logger = LoggerFactory.getLogger(this.getClass());
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("title", "Welcome");
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("title", "Log-In");
        return "login";
    }

    @RequestMapping(
            value="/login",
            method= RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    public String loginUser(@RequestBody MultiValueMap<String, String> loginData) {
        logger.debug(loginData.toString());
        boolean loggedIn = true;
        if (!loggedIn) {
            return "redirect:/login";
        } else {
            return "redirect:/items/areas";
        }
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("title", "Create Account");
        return "register";
    }

    @RequestMapping(
            value="/register",
            method= RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    public String registerUser(@RequestBody MultiValueMap<String, String> registerData) {
        logger.debug(registerData.toString());
        boolean registered = true;
        if (!registered) {
            return "redirect:/register";
        } else {
            return "redirect:/items/areas";
        }
    }
}
