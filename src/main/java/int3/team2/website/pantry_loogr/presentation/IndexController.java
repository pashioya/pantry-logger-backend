package int3.team2.website.pantry_loogr.presentation;

import int3.team2.website.pantry_loogr.domain.EndUser;
import int3.team2.website.pantry_loogr.service.UserService;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/")
public class IndexController {

    private Logger logger;
    private UserService userService;

    public IndexController(UserService userService) {
        logger = LoggerFactory.getLogger(this.getClass());

        this.userService = userService;
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
        String username = loginData.get("username").get(0);
        String password = loginData.get("password").get(0);
        if (username.length() == 0 || password.length() == 0 || !userService.usernameExists(username)) {
            return "redirect:/login";
        }
        EndUser user = userService.getByUsername(username);
        if (user.getPassword().equals(loginData.get("password").get(0))) {
            return "redirect:/items/areas";
        } else {
            return "redirect:/login";
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
    public String registerUser(@RequestBody MultiValueMap<String, String> data) {
        logger.debug(data.toString());

        EndUser user;
        //TODO check for username and email uniqueness
        if (data.get("password").get(0).equals(data.get("confirm-password").get(0))) {
            user = userService.add(new EndUser(
                    data.get("username").get(0),
                    data.get("email").get(0),
                    data.get("password").get(0)
            ));
            if (user == null) {
                logger.info("User already exists with this name please use a different name!");
            } else {
                logger.debug(user.toString());
                return "redirect:/items/areas";
            }
        }
        return "redirect:/register";
    }

    @RequestMapping(
            value = "/checkUsername",
            method = RequestMethod.GET,
            produces = "application/json"
    )
    public @ResponseBody boolean checkUsername(@RequestParam("username") String username) {
        return userService.usernameExists(username);
    }

    @RequestMapping(
            value = "/checkEmail",
            method = RequestMethod.GET,
            produces = "application/json"
    )
    public @ResponseBody boolean checkEmail(@RequestParam("email") String email) {
        return userService.emailExists(email);
    }
}
