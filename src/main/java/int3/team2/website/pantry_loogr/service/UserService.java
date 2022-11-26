package int3.team2.website.pantry_loogr.service;

import int3.team2.website.pantry_loogr.domain.EndUser;
import java.util.List;

public interface UserService {
    List<EndUser> getAll();
    EndUser get(int userID);
    List<EndUser> add(EndUser endUser);
    List<EndUser> getByUsername(String username);
    List<EndUser> getByFirstName(String firstName);
    List<EndUser> getByLastName(String lastName);
    List<EndUser> getByEmail(String email);
    List<EndUser> getByCity(String city);
    List<EndUser> getByStateRegion(String stateRegion);
    List<EndUser> getByZip(String zip);
    List<EndUser> getByCountry(String country);

}
