package int3.team2.website.pantry_loogr.service;

import int3.team2.website.pantry_loogr.domain.Tag;
import int3.team2.website.pantry_loogr.domain.UserTagRelationship;
import int3.team2.website.pantry_loogr.repository.TagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * when creating a recipe, it will add all the tags related to that recipe in the relational table
     */
    @Override
    public List<Tag> addToRecipeRelationTable(int recipeId, List<Tag> recipeTags) {
        if (recipeTags != null) {
            return tagRepository.addToRecipeRelationTable(recipeId, recipeTags);
        }
        return null;
    }

    /**
     * creates a map of all the tags and assigns to each a UserRelationship, it defines for each if the user likes or dislikes a tag or if we don't know
     */
    @Override
    public Map<Tag, UserTagRelationship> getTagsByUserRelationship(int userId) {
        List<Tag> allTags = tagRepository.findAll();
        Map<Tag, Boolean> userTags = tagRepository.getAllByUser(userId);
        Map<Tag, UserTagRelationship> exitMap = new HashMap<>();
        allTags.forEach(tag -> {
            if (userTags.containsKey(tag)) {
                if (userTags.get(tag)) {
                    exitMap.put(tag, UserTagRelationship.LIKES);
                } else {
                    exitMap.put(tag, UserTagRelationship.DISLIKES);
                }
            } else {
                exitMap.put(tag, UserTagRelationship.UNDEFINED);
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
