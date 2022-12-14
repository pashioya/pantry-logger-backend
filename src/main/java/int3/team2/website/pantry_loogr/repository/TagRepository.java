package int3.team2.website.pantry_loogr.repository;

import int3.team2.website.pantry_loogr.domain.Tag;

import java.util.List;
import java.util.Map;

public interface TagRepository {
    List<Tag> findAll();
    Tag get(int id);
    List<Tag> findByName(String name);
    List<Tag> getByRecipeId(int recipeId);
    List<Tag> getByIngredientId(int ingredientId);
    Map<Tag, Boolean> getAllByUser(int userId);
    void addUserPreference(int userId, int tagId, boolean like);
    void removeUserPreference(int userId, int tagId);
    List<Tag> getLikesByUserId(int userId);
    List<Tag> getDislikesByUserId(int userId);
    List<Tag> addToRecipeRelationTable(int recipeId, List<Tag> tagList);
}
