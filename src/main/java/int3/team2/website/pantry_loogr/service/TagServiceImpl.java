package int3.team2.website.pantry_loogr.service;

import int3.team2.website.pantry_loogr.domain.Tag;
import int3.team2.website.pantry_loogr.domain.UserTagRealationship;
import int3.team2.website.pantry_loogr.repository.TagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class TagServiceImpl implements TagService {
    private Logger logger;
    private TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.logger = LoggerFactory.getLogger(this.getClass());
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
    public List<Tag> addToRecipeRelationTable(int recipeId, List<Tag> recipeTags) {
        if (recipeTags != null) {
            return tagRepository.addToRecipeRelationTable(recipeId, recipeTags);
        }
        return null;
    }

    @Override
    public Map<Tag, UserTagRealationship> getTagsByUserRelationship(int userId) {
        List<Tag> allTags = tagRepository.findAll();
        Map<Tag, Boolean> userTags = tagRepository.getAllByUser(userId);
        Map<Tag, UserTagRealationship> exitMap = new HashMap<>();
        allTags.forEach(x -> {
            if (userTags.containsKey(x)) {
                if (userTags.get(x)) {
                    exitMap.put(x, UserTagRealationship.LIKES);
                } else {
                    exitMap.put(x, UserTagRealationship.DISLIKES);
                }
            } else {
                exitMap.put(x, UserTagRealationship.UNDEFINED);
            }
        });
        return exitMap;
    }

    @Override
    public void updateUserTagRelationship(int userId, List<Integer> tagIds, boolean like) {
        List<Tag> originalTags;
        if (like) {
            originalTags = getLikesByUserId(userId);
        } else {
            originalTags = getDislikesByUserId(userId);
        }

        //tags to add
        tagIds.stream()
                .filter(x -> !originalTags.stream().map(Tag::getId).toList().contains(x))
                .forEach(x -> tagRepository.addUserPreference(userId, x, like));
        //tags to delete
        originalTags.stream()
                .filter(x -> !tagIds.contains(x.getId()))
                .forEach(x -> tagRepository.removeUserPreference(userId, x.getId()));
    }
}
