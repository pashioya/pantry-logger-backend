package int3.team2.website.pantry_loogr.service;

import int3.team2.website.pantry_loogr.domain.Tag;
import int3.team2.website.pantry_loogr.domain.UserTagRealationship;

import java.util.List;
import java.util.Map;

public interface TagService {
    List<Tag> getAll();
    Tag get(int tagID);
    List<Tag> add(Tag tag);
    List<Tag> getByName(String name);
    List<Tag> getByRecipeId(int recipeId);
    List<Tag> getLikesByUserId(int userId);
    List<Tag> getDislikesByUserId(int userId);
    List<Tag> addToRecipeRelationTable(int recipeId, List<Tag> tagList);

    Map<Tag, UserTagRealationship> getTagsByUserRelationship(int userId);

    void updateUserTagRelationship(int userId, List<Integer> tagIds, boolean like);
}
