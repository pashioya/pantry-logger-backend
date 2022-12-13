package int3.team2.website.pantry_loogr.service;

import int3.team2.website.pantry_loogr.domain.Tag;

import java.util.List;

public interface TagService {
    List<Tag> getAll();
    Tag get(int tagID);
    List<Tag> add(Tag tag);
    List<Tag> getByName(String name);
    List<Tag> getByRecipeId(int recipeId);
    List<Tag> getLikesByUserId(int userId);
    List<Tag> getDislikesByUserId(int userId);
    List<Tag> addToRelationTable(int recipeId, List<Tag> tagList);
    Tag createUserPreference(int userId, Tag tag, boolean like);
}
