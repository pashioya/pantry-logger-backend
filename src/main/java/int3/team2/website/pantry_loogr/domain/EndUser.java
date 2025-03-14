package int3.team2.website.pantry_loogr.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private List<Tag> likes;
    private List<Tag> dislikes;

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
     * Constructor that is used when getting users from the database.
     * All values except username, email and password may be null.
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
     * @param currentRecipe the recipe that the User is currently cooking
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
        List<PantryZoneProduct> pantryZoneProducts = new ArrayList<>();
        pantryZones.forEach(pantryZone -> {
            pantryZoneProducts.addAll(pantryZone.getProducts());
        });
        Map<Ingredient, Integer> missingIngredients = currentRecipe.getMissingIngredients(pantryZoneProducts);

        shoppingList.setIngredients(missingIngredients);
    }

    public void setPantryZones(List<PantryZone> pantryZones) {
        this.pantryZones = pantryZones;
    }
    public void setShoppingList(ShoppingList shoppingList) {
        this.shoppingList = shoppingList;
    }

    /**
     * @return a list of all the places the User stores food at
     */
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

    /**
     * @return a list of Tags that the User likes
     * (these can be indicated by the User or can be deducted by the AI)
     */
    public List<Tag> getLikes() {
        return likes;
    }

    public void setLikes(List<Tag> likes) {
        this.likes = likes;
    }

    /**
     * @return a list of Tags that the User likes
     * (these can be indicated by the User or can be deducted by the AI)
     */
    public List<Tag> getDislikes() {
        return dislikes;
    }

    public void setDislikes(List<Tag> dislikes) {
        this.dislikes = dislikes;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStateRegion(String stateRegion) {
        this.stateRegion = stateRegion;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return the items that belong to the user's shopping list
     */
    public ShoppingList getShoppingList() {
        return shoppingList;
    }

    public  Map<Ingredient, Integer> getShoppingListItems() {
        return shoppingList.getIngredients();
    }

    public boolean ownsPantry(int pantryId) {
        for(PantryZone pantryZone: pantryZones) {
            if(pantryZone.getId() == pantryId) return true;
        }
        return false;
    }
}
