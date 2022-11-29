package int3.team2.website.pantry_loogr.domain;


/**
 * A Tag is a very brood item that represents a property of either an
 * ingredient a recipe of even a user. It can show a allergy or a property.
 */
public class Tag {
    private int id;
    private String name;

    public Tag(String name) {
        this.name = name;
    }

    public Tag(int id, String name) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
