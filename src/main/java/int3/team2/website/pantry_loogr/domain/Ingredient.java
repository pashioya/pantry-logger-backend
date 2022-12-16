package int3.team2.website.pantry_loogr.domain;

import java.util.Objects;

/**
 * Ingredient contains the information relative to a specific ingredient.
 */
public class Ingredient {
    protected int id;
    protected String name;
    protected String imagePath;

    /**
     * Constructor to create a new Ingredient before it is inserted into the database.
     *
     * @param name name of the ingredient
     */
    public Ingredient(String name) {
        this.name = name;
    }

    /**
     * Constructor used when getting an ingredient from the database with id included.
     *
     * @param id ties the ingredient to the DB
     * @param name name of the ingredient
     */
    public Ingredient(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Constructor used when getting an ingredient from the database with id included
     * (used when there is an image tied to the ingredient).
     *
     * @param id ties the ingredient to the DB
     * @param name name of the ingredient
     * @param imagePath used to tie the ingredient to the image
     */
    public Ingredient(int id, String name, String imagePath) {
        this.id = id;
        this.name = name;
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

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
