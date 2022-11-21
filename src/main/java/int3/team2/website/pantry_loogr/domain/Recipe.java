package int3.team2.website.pantry_loogr.domain;

public class Recipe {
    private long recipe_id;
    private String recipe_name;
    private Difficulty recipe_difficulty;

    private String recipe_description;
    private String recipe_instructions;
    private Time recipe_time;


    public Recipe() {
    }

    public Recipe(String recipe_name, Difficulty recipe_difficulty, String recipe_description, String recipe_instructions, Time recipe_time) {
        this.recipe_name = recipe_name;
        this.recipe_difficulty = recipe_difficulty;
        this.recipe_description = recipe_description;
        this.recipe_instructions = recipe_instructions;
        this.recipe_time = recipe_time;
    }

    public Recipe(int ID, String recipe_name, Difficulty recipe_difficulty, String recipe_description, String recipe_instructions, Time recipe_time) {
        this.recipe_id = ID;
        this.recipe_name = recipe_name;
        this.recipe_difficulty = recipe_difficulty;
        this.recipe_description = recipe_description;
        this.recipe_instructions = recipe_instructions;
        this.recipe_time = recipe_time;
    }

    public long getRecipe_id() {
        return recipe_id;
    }

    public String getRecipe_name() {
        return recipe_name;
    }

    public void setRecipe_name(String recipe_name) {
        this.recipe_name = recipe_name;
    }

    public Difficulty getRecipe_difficulty() {
        return recipe_difficulty;
    }

    public void setRecipe_difficulty(Difficulty recipe_difficulty) {
        this.recipe_difficulty = recipe_difficulty;
    }

    public String getRecipe_description() {
        return recipe_description;
    }

    public void setRecipe_description(String recipe_description) {
        this.recipe_description = recipe_description;
    }

    public String getRecipe_instructions() {
        return recipe_instructions;
    }

    public void setRecipe_instructions(String recipe_instructions) {
        this.recipe_instructions = recipe_instructions;
    }

    public Time getRecipe_time() {
        return recipe_time;
    }

    public void setRecipe_time(Time recipe_time) {
        this.recipe_time = recipe_time;
    }

    @Override
    public String toString() {
        return "Model{" +
                "recipe_id=" + recipe_id +
                ", recipe_name='" + recipe_name + '\'' +
                ", recipe_difficulty=" + recipe_difficulty +
                ", recipe_description='" + recipe_description + '\'' +
                ", recipe_instructions='" + recipe_instructions + '\'' +
                ", recipe_time=" + recipe_time +
                '}';
    }
}
