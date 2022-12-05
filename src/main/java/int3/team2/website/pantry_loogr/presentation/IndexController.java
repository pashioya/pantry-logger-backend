package int3.team2.website.pantry_loogr.presentation;

import int3.team2.website.pantry_loogr.domain.EndUser;
import int3.team2.website.pantry_loogr.presentation.helper.DataItem;
import int3.team2.website.pantry_loogr.presentation.helper.HtmlItems;
import int3.team2.website.pantry_loogr.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;

@Controller
@RequestMapping("/")
public class IndexController {

    private Logger logger;
    private UserService userService;

    public IndexController(UserService userService) {
        this.logger = LoggerFactory.getLogger(this.getClass());
        this.userService = userService;
    }

    @GetMapping
    public String index(HttpSession httpSession, Model model) {
        EndUser user = userService.authenticate((String) httpSession.getAttribute("username"), (String) httpSession.getAttribute("password"));
        if(user != null) {
            return "redirect:/items/pantry-zones";
        }
        model.addAttribute("title", "Welcome");

        return "index";
    }

    @GetMapping("/login")
    public String login(HttpSession httpSession, Model model) {
        EndUser user = userService.authenticate((String) httpSession.getAttribute("username"), (String) httpSession.getAttribute("password"));
        if(user != null) {
            return "redirect:/items/pantry-zones";
        }
        model.addAttribute("title", "Log-In");
        model.addAttribute("headerList", new ArrayList<>(Arrays.asList(
                new DataItem(HtmlItems.BACK_BUTTON,"/"),
                new DataItem(HtmlItems.HEADER_TITLE, "Log-In"),
                new DataItem(HtmlItems.LOGO)
        )));
        return "login";
    }

    @RequestMapping(
            value="/login",
            method= RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    public String loginUser(HttpSession httpSession, @RequestBody MultiValueMap<String, String> loginData) {
        String username = loginData.get("username").get(0);
        String password = loginData.get("password").get(0);
        if (username.length() == 0 || password.length() == 0 || !userService.usernameExists(username)) {
            return "redirect:/login";
        }
        EndUser user = userService.authenticate(username, password);
        if (user != null) {
            httpSession.setAttribute("username", username);
            httpSession.setAttribute("password", password);
            return "redirect:/items/pantry-zones";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("title", "Create Account");
        model.addAttribute("headerList", new ArrayList<>(Arrays.asList(
                new DataItem(HtmlItems.BACK_BUTTON,"/"),
                new DataItem(HtmlItems.HEADER_TITLE, "Create Account"),
                new DataItem(HtmlItems.LOGO)
        )));
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
                return "redirect:/items/pantry-zones";
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
