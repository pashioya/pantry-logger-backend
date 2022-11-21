package int3.team2.website.pantry_loogr.repository;

import int3.team2.website.pantry_loogr.domain.Tag;

import java.util.List;

public interface TagRepository {
    List<Tag> findAll();
    Tag get(int id);
    List<Tag> findByName(String name);

}
