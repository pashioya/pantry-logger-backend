package int3.team2.website.pantry_loogr.repository;

import int3.team2.website.pantry_loogr.domain.EndUser;
import int3.team2.website.pantry_loogr.domain.Recipe;
import int3.team2.website.pantry_loogr.domain.Tag;
import int3.team2.website.pantry_loogr.domain.UserPreference;

import java.util.List;

public interface UserRepository {
    List<EndUser> findAll();
    EndUser get(int id);

    EndUser add(EndUser user);

    EndUser findByUsername(String username);

    EndUser findByEmail(String email);
    void updateUser(EndUser endUser);

    List<UserPreference> getUserPreferences(int userId);
}
