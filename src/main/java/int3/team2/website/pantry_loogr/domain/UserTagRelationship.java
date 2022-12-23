package int3.team2.website.pantry_loogr.domain;

public enum UserTagRelationship {
    LIKES, UNDEFINED, DISLIKES;

    public int getVal() {
        return switch (this) {
            case LIKES -> 1;
            case UNDEFINED -> 0;
            case DISLIKES -> -1;
        };
    }
}
