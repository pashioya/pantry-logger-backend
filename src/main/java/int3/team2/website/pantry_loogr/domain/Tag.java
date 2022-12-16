package int3.team2.website.pantry_loogr.domain;


import java.util.Objects;

/**
 * A Tag is used to represent different properties of a Recipe or Ingredient
 * It can be used to notice the user for certain allergen in a recipe.
 * It can also be used to indicate the origin of a recipe or if it fits into a diet (vegetarian, vegan, etc...)
 */
public class Tag {
    private int id;
    private String name;
    private TagFlag flag;

    /**
     * Constructor to create a new Tag before it is inserted into the database.
     *
     * @param name the name of the tag
     * @param flag the category of tag that it belongs to (general, allergen, cuisine)
     */
    public Tag(String name, TagFlag flag) {
        this.name = name;
        this.flag = flag;
    }

    /**
     * Constructor used when getting a tag from the database with id included.
     *
     * @param id ties the tag to the db
     * @param name the name of the tag
     * @param flag the category of tag that it belongs to (general, allergen, cuisine)     */
    public Tag(int id, String name, TagFlag flag) {
        this.id = id;
        this.name = name;
        this.flag = flag;
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

    public TagFlag getFlag() {
        return flag;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return name.equals(tag.name) && flag == tag.flag;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, flag);
    }
}
