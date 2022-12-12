package int3.team2.website.pantry_loogr.service;

import int3.team2.website.pantry_loogr.domain.EndUser;
import int3.team2.website.pantry_loogr.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class UserServiceImpl implements UserService {

    private Logger logger;
    private UserRepository userRepository;
    private PantryZoneService pantryZoneService;
    private IngredientService ingredientService;
    private ShoppingListService shoppingListService;

    public UserServiceImpl(UserRepository userRepository, PantryZoneService pantryZoneService,IngredientService ingredientService, ShoppingListService shoppingListService) {
        logger = LoggerFactory.getLogger(this.getClass());
        this.userRepository = userRepository;
        this.pantryZoneService = pantryZoneService;
        this.ingredientService = ingredientService;
        this.shoppingListService = shoppingListService;
    }

    @Override
    public List<EndUser> getAll() {
        return userRepository.findAll();
    }

    @Override
    public EndUser get(int userID) {
        return userRepository.get(userID);
    }

    @Override
    public EndUser add(EndUser endUser) {
        if (
                !this.usernameExists(endUser.getUsername())
                && !this.emailExists(endUser.getEmail())
        ) {
            return userRepository.add(endUser);
        } else {
            return null;
        }
    }


    public EndUser authenticate(String username, String password) {
        if(this.usernameExists(username)) {
            EndUser user = this.getByUsername(username);
            if (user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
    @Override
    public EndUser getByUsername(String username) {

        EndUser user =  userRepository.findByUsername(username);
        if(user != null) {
            user.setPantryZones(pantryZoneService.getAllForUser(user.getId()));
            user.setShoppingList(shoppingListService.getByUser(user.getId()));
        }
        return user;
    }


    @Override
    public EndUser getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean usernameExists(String username) {
        return this.getByUsername(username) != null;
    }

    @Override
    public boolean emailExists(String email) {
        return this.getByEmail(email) != null;
    }

    @Override
    public void updateUser(EndUser endUser) {

        userRepository.updateUser(endUser);
        ingredientService.clearShoppingListIngredients(endUser.getShoppingList().getId());
        ingredientService.addShoppingListIngredients(endUser.getShoppingList().getId(), endUser.getShoppingListItems());
    }
}
