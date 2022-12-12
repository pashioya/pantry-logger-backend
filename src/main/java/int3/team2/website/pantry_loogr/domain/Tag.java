package int3.team2.website.pantry_loogr.domain;


import java.util.Objects;

/**
 * A Tag is a very broad item that represents a property of either an
 * ingredient a recipe of even a user. It can show a allergy or a property.
 */
public class Tag {
    private int id;
    private String name;
    private TagFlag flag;

    public Tag(String name, TagFlag flag) {
        this.name = name;
        this.flag = flag;
    }

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
