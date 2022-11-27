package int3.team2.website.pantry_loogr.repository;

import int3.team2.website.pantry_loogr.domain.EndUser;
import int3.team2.website.pantry_loogr.domain.Recipe;

import java.util.List;

public interface UserRepository {
    List<EndUser> findAll();
    EndUser get(int id);

    EndUser add(EndUser user);

    List<EndUser> findByUsername(String username);
    List<EndUser> findByFirstName(String firstName);
    List<EndUser> findByLastName(String lastName);
    List<EndUser> findByEmail(String email);
    List<EndUser> findByCity(String city);
    List<EndUser> findByStateRegion(String stateRegion);
    List<EndUser> findByZip(String zip);
    List<EndUser> findByCountry(String country);
}
