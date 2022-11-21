package int3.team2.website.pantry_loogr.domain;

public class Ingredient {
    private int id;
    private String name;

    public Ingredient(String name) {
        this.name = name;
    }

    public Ingredient(int id, String name) {
        this.name = name;
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
}
