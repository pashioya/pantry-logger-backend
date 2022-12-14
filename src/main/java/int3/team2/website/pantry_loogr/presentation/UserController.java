package int3.team2.website.pantry_loogr.presentation;

import int3.team2.website.pantry_loogr.domain.EndUser;
import int3.team2.website.pantry_loogr.presentation.helper.DataItem;
import int3.team2.website.pantry_loogr.presentation.helper.HtmlItems;
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

@Controller
@RequestMapping("/profile")
public class UserController {

    private Logger logger;
    private UserService userService;
    private TagService tagService;

    public UserController(UserService userService, TagService tagService) {
        this.logger = LoggerFactory.getLogger(this.getClass());
        this.userService = userService;
        this.tagService = tagService;
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
        return "profile";
    }

    @RequestMapping(
            value = "/logout",
            method = RequestMethod.GET
    )
    public String logout(HttpSession httpSession) {
        EndUser user = userService.authenticate((String) httpSession.getAttribute("username"), (String) httpSession.getAttribute("password"));
        httpSession.invalidate();
        return "redirect:/login";
    }

    @RequestMapping(
            value="/profile/tagedit",
            method= RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    public String loginUser(HttpSession httpSession, @RequestBody MultiValueMap<String, String> tagData) {
        EndUser user = userService.authenticate((String) httpSession.getAttribute("username"), (String) httpSession.getAttribute("password"));
        if(user == null) {
            return "redirect:/login";
        }
        logger.debug(tagData.toString());
        return "redirect:/profile";
    }

}
