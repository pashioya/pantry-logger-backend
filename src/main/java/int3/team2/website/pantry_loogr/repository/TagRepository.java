package int3.team2.website.pantry_loogr.repository;

import int3.team2.website.pantry_loogr.domain.Tag;

import java.util.List;

public interface TagRepository {
    List<Tag> findAll();
    Tag get(int id);
    List<Tag> findByName(String name);
    List<Tag> getByRecipeId(int recipeId);
    List<Tag> getByIngredientId(int ingredientId);
    List<Tag> getLikesByUserId(int userId);
    List<Tag> getDislikesByUserId(int userId);
    List<Tag> addToRelationTable(int recipeId, List<Tag> tagList);
    Tag createTag(Tag tag);
    Tag createUserPreference(int userId, Tag tag, boolean like);
}
