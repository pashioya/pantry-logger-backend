package int3.team2.website.pantry_loogr.presentation;

import int3.team2.website.pantry_loogr.domain.EndUser;
import int3.team2.website.pantry_loogr.domain.PantryZone;
import int3.team2.website.pantry_loogr.presentation.helper.DataItem;
import int3.team2.website.pantry_loogr.presentation.helper.HtmlItems;
import int3.team2.website.pantry_loogr.service.PantryZoneService;
import int3.team2.website.pantry_loogr.service.TagService;
import int3.team2.website.pantry_loogr.service.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.slf4j.Logger;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * allows the user to see all the data and sensor boxes related to his account, he can modify that data (preferences, username, password...) and can logout of his account
 */
@Controller
@RequestMapping("/profile")
public class UserController {

    private Logger logger;
    private UserService userService;
    private TagService tagService;

    private PantryZoneService pantryZoneService;

    public UserController(UserService userService, TagService tagService, PantryZoneService pantryZoneService) {
        this.logger = LoggerFactory.getLogger(this.getClass());
        this.userService = userService;
        this.tagService = tagService;
        this.pantryZoneService = pantryZoneService;
    }


    @GetMapping
    public String profile(HttpSession httpSession, Model model) {
        EndUser user = userService.authenticate((String) httpSession.getAttribute("username"), (String) httpSession.getAttribute("password"));
        if(user == null) {
            return "redirect:/login";
        }
        model.addAttribute("title", "Profile");
        model.addAttribute("headerList", new ArrayList<>(Arrays.asList(
                new DataItem(HtmlItems.BACK_BUTTON,"/items/pantry-zones"),
                new DataItem(HtmlItems.HEADER_TITLE, "Profile"),
                new DataItem(HtmlItems.LOGO)
        )));
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("tagMap", tagService.getTagsByUserRelationship(user.getId()));
        model.addAttribute("pantryZones", pantryZoneService.getAllForUser(user.getId()));
        model.addAttribute("emptyPantryZones", pantryZoneService.getAllForUser(user.getId()).stream()
                .filter(pantryZone -> Objects.equals(pantryZone.getSensorBoxCode(), "")).collect(Collectors.toList()));
        model.addAttribute("user", user);
        return "profile";
    }

    @RequestMapping(
            value = "/logout",
            method = RequestMethod.GET
    )
    public String logout(HttpSession httpSession) {
        EndUser user = userService.authenticate((String) httpSession.getAttribute("username"), (String) httpSession.getAttribute("password"));
        if(user == null) {
            return "redirect:/login";
        }

        httpSession.invalidate();
        return "redirect:/login";
    }

    @RequestMapping(
            value="/editLikes",
            method= RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    public String editLikes(HttpSession httpSession, @RequestBody MultiValueMap<String, String> tagData) {
        EndUser user = userService.authenticate((String) httpSession.getAttribute("username"), (String) httpSession.getAttribute("password"));
        if(user == null) {
            return "redirect:/login";
        }
        List<Integer> list = tagData.get("liked-tags").stream().map(Integer::parseInt).toList();
        tagService.updateUserTagRelationship(user.getId(), list, true);

        pantryZoneService.getAllForUser(user.getId())
                .stream()
                .filter(pantryZone -> pantryZone.getSensorBoxCode() != null);

        return "redirect:/profile";
    }

    @RequestMapping(
            value="/editDislikes",
            method= RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    public String editDislikes(HttpSession httpSession, @RequestBody MultiValueMap<String, String> tagData) {
        EndUser user = userService.authenticate((String) httpSession.getAttribute("username"), (String) httpSession.getAttribute("password"));
        if(user == null) {
            return "redirect:/login";
        }
        List<Integer> list = tagData.get("disliked-tags").stream().map(Integer::parseInt).toList();
        tagService.updateUserTagRelationship(user.getId(), list, false);
        return "redirect:/profile";
    }

    @RequestMapping(
            value="/editProfile",
            method= RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    public String editProfile(HttpSession httpSession, @RequestBody MultiValueMap<String, String> tagData) {
        EndUser user = userService.authenticate((String) httpSession.getAttribute("username"), (String) httpSession.getAttribute("password"));
        if(user == null) {
            return "redirect:/login";
        }

        String username = tagData.get("username").get(0);
        String firstName = tagData.get("firstName").get(0);
        String lastName = tagData.get("lastName").get(0);
        String city = tagData.get("city").get(0);
        String stateRegion = tagData.get("stateRegion").get(0);
        String country = tagData.get("country").get(0);

            user.setUsername(username);
            httpSession.setAttribute("username", username);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setCity(city);
            user.setStateRegion(stateRegion);
            user.setCountry(country);


        userService.updateUser(user);
        return "redirect:/profile";
    }

    @RequestMapping(
            value="/editPassword",
            method= RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    public String editPassword(HttpSession httpSession, @RequestBody MultiValueMap<String, String> tagData) {
        EndUser user = userService.authenticate((String) httpSession.getAttribute("username"), (String) httpSession.getAttribute("password"));
        if(user == null) {
            return "redirect:/login";
        }
        String password = tagData.get("password").get(0);
        String passwordConfirm = tagData.get("confirm-password").get(0);
        if(!password.equals(passwordConfirm)) {
            System.out.println("Passwords do not match");
            return "redirect:/profile";
        }
        user.setPassword(password);
        httpSession.setAttribute("password", password);
        userService.updateUser(user);
        return "redirect:/profile";
    }



    @RequestMapping(
            value="/addSensorBox",
            method= RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    public String addSensorBox(HttpSession httpSession, @RequestBody MultiValueMap<String, String> sensorBoxData) {
        EndUser user = userService.authenticate((String) httpSession.getAttribute("username"), (String) httpSession.getAttribute("password"));
        if(user == null) {
            return "redirect:/login";
        }
        String sensorBoxCode = sensorBoxData.get("sensor-box-code").get(0);
        PantryZone pantryzone = pantryZoneService.get(Integer.parseInt(sensorBoxData.get("pantry-zone").get(0)));

        pantryzone.setSensorBoxCode(sensorBoxCode);
        pantryZoneService.update(pantryzone);
        return "redirect:/profile";
    }


    @RequestMapping(
            value="/deleteSensorBox",
            method= RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    public String deleteSensorBox(HttpSession httpSession, @RequestBody MultiValueMap<String, String> sensorBoxData) {
        EndUser user = userService.authenticate((String) httpSession.getAttribute("username"), (String) httpSession.getAttribute("password"));
        if(user == null) {
            return "redirect:/login";
        }
        PantryZone pantryzone = pantryZoneService.get(Integer.parseInt(sensorBoxData.get("pantry-zone").get(0)));

        pantryzone.setSensorBoxCode(null);
        pantryZoneService.update(pantryzone);
        return "redirect:/profile";
    }


    @RequestMapping(
            value="/editSensorBox",
            method= RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    public String editSensorBox(HttpSession httpSession, @RequestBody MultiValueMap<String, String> sensorBoxData) {
        EndUser user = userService.authenticate((String) httpSession.getAttribute("username"), (String) httpSession.getAttribute("password"));
        if(user == null) {
            return "redirect:/login";
        }
        logger.debug(sensorBoxData.toString());
        String sensorID = sensorBoxData.get("sensor-id").get(0);
        PantryZone pantryzone = pantryZoneService.getBySensorBoxCode(sensorID);
        pantryzone.setMaxTemp(Integer.parseInt(sensorBoxData.get("temp-upper-limit").get(0)));
        pantryzone.setMinTemp(Integer.parseInt(sensorBoxData.get("temp-lower-limit").get(0)));
        pantryzone.setMaxHum(Integer.parseInt(sensorBoxData.get("hum-upper-limit").get(0)));
        pantryzone.setMinHum(Integer.parseInt(sensorBoxData.get("hum-lower-limit").get(0)));
        pantryzone.setMaxBright(Integer.parseInt(sensorBoxData.get("bright-upper-limit").get(0)));
        pantryzone.setMinBright(Integer.parseInt(sensorBoxData.get("bright-lower-limit").get(0)));
        pantryZoneService.update(pantryzone);
        return "redirect:/profile";
    }

}
