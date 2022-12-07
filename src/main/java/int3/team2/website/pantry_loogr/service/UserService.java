package int3.team2.website.pantry_loogr.service;

import int3.team2.website.pantry_loogr.domain.EndUser;
import java.util.List;

public interface UserService {
    List<EndUser> getAll();
    EndUser get(int userID);
    EndUser add(EndUser endUser);
    void updateUser(EndUser endUser);
    EndUser getByUsername(String username);
    EndUser getByEmail(String email);

    boolean usernameExists(String username);
    boolean emailExists(String email);
    EndUser authenticate(String username, String password);
}
