package int3.team2.website.pantry_loogr.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Recipe contains the information relative to a specific recipe.
 */
public class Recipe {
    private int id;
    private String name;
    private Difficulty difficulty;

    private String description;
    private String instructions;
    private Time time;
    private Map<Ingredient, Integer> ingredients;

    private List<Tag> tags;

    private String imagePath;

    /**
     * Instantiates an object without any data so that the user can add it in pieces.
     */
    public Recipe() {
    }

    /**
     * Constructor to create a new Recipe before it is inserted into the database.
     *
     * @param name name of the recipe
     * @param difficulty cooking difficulty of the recipe
     * @param description quick description of the recipe
     * @param instructions list of instructions to make the recipe
     * @param time enum time needed to cook the recipe
     * @param imagePath the path to the image the recipe is tied to
     */
    public Recipe(String name, Difficulty difficulty, String description, String instructions, Time time, String imagePath) {
        this.name = name;
        this.difficulty = difficulty;
        this.description = description;
        this.instructions = instructions;
        this.time = time;
        this.imagePath = imagePath;
    }

    /**
     *
     * Constructor used when getting a recipe from the database with id included.
     *
     * @param ID ties the recipe to the db
     * @param name name of the recipe
     * @param difficulty cooking difficulty of the recipe
     * @param description quick description of the recipe
     * @param instructions list of instructions to make the recipe
     * @param time enum time needed to cook the recipe
     * @param imagePath the path to the image the recipe is tied to
     */
    public Recipe(int ID, String name, Difficulty difficulty, String description, String instructions, Time time, String imagePath) {
        this.id = ID;
        this.name = name;
        this.difficulty = difficulty;
        this.description = description;
        this.instructions = instructions;
        this.time = time;
        this.imagePath = imagePath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Map<Ingredient, Integer> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Map<Ingredient, Integer> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String getImagePath() {return this.imagePath;}


    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", difficulty=" + difficulty +
                ", description='" + description + '\'' +
                ", instructions='" + instructions + '\'' +
                ", time=" + time +
                ", ingredients=" + ingredients +
                ", tags=" + tags +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }

    public Map<Ingredient, Integer> getMissingIngredients(List<PantryZoneProduct> pantryZoneProducts) {
        Map<Ingredient, Integer> ingredientsNeeded = new HashMap<>();

        this.getIngredients().forEach((k,v) -> {
            PantryZoneProduct pantryZoneproduct = pantryZoneProducts.stream().filter(product -> product.getId() == k.getId()).findAny().orElse(null);
            if( pantryZoneproduct == null) {
                ingredientsNeeded.put(k, v);
            } else {
                int amountNeeded = v - pantryZoneproduct.getTotalRemaining();
                if(amountNeeded > 0) {
                    ingredientsNeeded.put(k, amountNeeded);
                }

            }
        });
        return ingredientsNeeded;
    }
}
