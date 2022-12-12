package int3.team2.website.pantry_loogr.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import int3.team2.website.pantry_loogr.repository.ShoppingListRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * EndUser contains all data relating to a specific user.
 * When created the first constructor is used with only the
 * necessary parameters.
 */
public class EndUser {

    private Logger logger;
    private int id;

    private String password;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String city;
    private String stateRegion;
    private String zip;
    private String country;
    private int currentRecipe;
    private ShoppingList shoppingList;
    private List<PantryZone> pantryZones;

    /**
     * Constructor that initially creates users on user registration
     *
     * @param username username of the user
     * @param email email of the user
     * @param password password of the user
     */
    public EndUser(String username, String email, String password) {
        this.logger = LoggerFactory.getLogger(this.getClass());
        this.username = username;
        this.email = email;
        this.password = password;
    }

    /**
     *
     * Constructor that is used when getting users from the database.
     * All values except username, email and password may be null.
     *
     * @param id id from the database
     * @param password password of the user
     * @param username username of the user
     * @param firstName firstname of the user
     * @param lastName lastname of the user
     * @param email email of the user
     * @param city city of residence
     * @param stateRegion stateRegion of residence
     * @param zip zip code of residence
     * @param country country of residence
     */
    public EndUser(int id, String password, String username, String firstName, String lastName, String email, String city, String stateRegion, String zip, String country, int currentRecipe) {
        this.logger = LoggerFactory.getLogger(this.getClass());
        this.id = id;
        this.password = password;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.city = city;
        this.stateRegion = stateRegion;
        this.zip = zip;
        this.country = country;
        this.currentRecipe = currentRecipe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {return password;}

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getCity() {
        return city;
    }

    public String getStateRegion() {
        return stateRegion;
    }

    public String getZip() {
        return zip;
    }

    public String getCountry() {
        return country;
    }

    public int getCurrentRecipe() {
        return currentRecipe;
    }

    public void setCurrentRecipe(Recipe currentRecipe) {
        this.currentRecipe = currentRecipe.getId();
        logger.debug(String.valueOf(pantryZones.size()));
        List<PantryZoneProduct> pantryZoneProducts = new ArrayList<>();
        pantryZones.forEach(pantryZone -> {
            logger.debug(String.valueOf(pantryZone.getProducts().size()));
            pantryZoneProducts.addAll(pantryZone.getProducts());
        });
        Map<Ingredient, Integer> missingIngredients = currentRecipe.getMissingIngredients(pantryZoneProducts);
        logger.debug("missingIngredients");
        for (Ingredient i: missingIngredients.keySet()) {
            logger.debug(i.getName() + " " +  missingIngredients.get(i));
        }

        shoppingList.setIngredients(missingIngredients);
    }

    public void setPantryZones(List<PantryZone> pantryZones) {
        this.pantryZones = pantryZones;
    }
    public void setShoppingList(ShoppingList shoppingList) {
        this.shoppingList = shoppingList;
    }
    public List<PantryZone> getPantryZones() {
        return this.pantryZones;
    }
    @Override
    public String toString() {
        return "end_user{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", city='" + city + '\'' +
                ", stateRegion='" + stateRegion + '\'' +
                ", zip='" + zip + '\'' +
                ", country='" + country + '\'' +
                '}';
    }

    public ShoppingList getShoppingList() {
        return shoppingList;
    }

    public  Map<Ingredient, Integer> getShoppingListItems() {
        return shoppingList.getIngredients();
    }
}
