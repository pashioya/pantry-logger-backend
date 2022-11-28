package int3.team2.website.pantry_loogr.domain;

import java.util.Map;

public class Recipe {
    private int id;
    private String name;
    private Difficulty difficulty;

    private String description;
    private String instructions;
    private Time time;
    private Map<Ingredient, String> ingredients;


    public Recipe() {
    }

    public Recipe(String name, Difficulty difficulty, String description, String instructions, Time time) {
        this.name = name;
        this.difficulty = difficulty;
        this.description = description;
        this.instructions = instructions;
        this.time = time;
    }

    public Recipe(int ID, String name, Difficulty difficulty, String description, String instructions, Time time) {
        this.id = ID;
        this.name = name;
        this.difficulty = difficulty;
        this.description = description;
        this.instructions = instructions;
        this.time = time;
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

    public Map<Ingredient, String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Map<Ingredient, String> ingredients) {
        this.ingredients = ingredients;
    }


    @Override
    public String toString() {
        return "Model{" +
                "recipe_id=" + id +
                ", recipe_name='" + name + '\'' +
                ", recipe_difficulty=" + difficulty +
                ", recipe_description='" + description + '\'' +
                ", recipe_instructions='" + instructions + '\'' +
                ", recipe_time=" + time +
                '}';
    }
}
