package int3.team2.website.pantry_loogr.presentation;

import int3.team2.website.pantry_loogr.domain.EndUser;
import int3.team2.website.pantry_loogr.domain.ShoppingList;
import int3.team2.website.pantry_loogr.service.ShoppingListService;
import int3.team2.website.pantry_loogr.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/shopping-list")
public class ShoppingListController {

    private Logger logger;
    private UserService userService;
    private ShoppingListService shoppingListService;

    public ShoppingListController(UserService userService, ShoppingListService shoppingListService) {
        this.logger = LoggerFactory.getLogger(this.getClass());
        this.userService = userService;
        this.shoppingListService = shoppingListService;
    }

    @GetMapping
    public String ShoppingList(HttpSession httpSession, Model model) {
        EndUser user = userService.authenticate((String) httpSession.getAttribute("username"), (String) httpSession.getAttribute("password"));
        if(user == null) {
            return "redirect:/login";
        }
        model.addAttribute("title", "Shopping List");

        ShoppingList shoppingList = shoppingListService.getByUser(user.getId());

        logger.debug(String.valueOf(shoppingList.getIngredients().size()));

        model.addAttribute("shoppingListIngredients", shoppingList.getIngredients());

        return "shoppingList";
    }
}
