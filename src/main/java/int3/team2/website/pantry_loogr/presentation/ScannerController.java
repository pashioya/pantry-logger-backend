package int3.team2.website.pantry_loogr.presentation;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/scanner")
public class ScannerController {

    private Logger logger;

    public ScannerController() {
        logger = LoggerFactory.getLogger(this.getClass());
    }

    @GetMapping
    public String scanner(Model model) {
        model.addAttribute("title", "Pantry Logr: Scanner");
        return "scanner";
    }

    @RequestMapping(
            method= RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    public String addItem(@RequestBody MultiValueMap<String, String> registerData) {
        logger.debug(registerData.toString());
        return "redirect:/scanner";
    }
}
