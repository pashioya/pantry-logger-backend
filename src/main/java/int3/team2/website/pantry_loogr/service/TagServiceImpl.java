package int3.team2.website.pantry_loogr.service;

import int3.team2.website.pantry_loogr.domain.Tag;
import int3.team2.website.pantry_loogr.repository.TagRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TagServiceImpl implements TagService {
    private TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public List<Tag> getAll() {
        return tagRepository.findAll();
    }

    @Override
    public Tag get(int tagID) {
        return tagRepository.get(tagID);
    }

    @Override
    public List<Tag> add(Tag tag) {
        return null;
    }

    @Override
    public List<Tag> getByName(String name) {
        return tagRepository.findByName(name);
    }

    @Override
    public List<Tag> getByRecipeId(int recipeId) {
        return tagRepository.getByRecipeId(recipeId);
    }

    @Override
    public List<Tag> getLikesByUserId(int userId) {
        return tagRepository.getLikesByUserId(userId);
    }

    @Override
    public List<Tag> getDislikesByUserId(int userId) {
        return tagRepository.getDislikesByUserId(userId);
    }

    @Override
    public List<Tag> addToRelationTable(int recipeId, List<Tag> recipeTags) {
        return tagRepository.addToRelationTable(recipeId, recipeTags);
    }
}
