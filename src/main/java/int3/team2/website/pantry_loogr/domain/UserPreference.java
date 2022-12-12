package int3.team2.website.pantry_loogr.domain;

public class UserPreference {
    private Tag tag;
    private boolean like;

    public UserPreference(Tag tag, boolean like) {
        this.tag = tag;
        this.like = like;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    @Override
    public String toString() {
        return "UserPreference{" +
                "tag=" + tag +
                ", like=" + like +
                '}';
    }
}
