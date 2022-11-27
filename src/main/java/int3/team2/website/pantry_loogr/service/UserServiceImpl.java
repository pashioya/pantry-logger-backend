package int3.team2.website.pantry_loogr.service;

import int3.team2.website.pantry_loogr.domain.EndUser;
import int3.team2.website.pantry_loogr.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserServiceImpl implements UserService {

    private Logger logger;
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        logger = LoggerFactory.getLogger(this.getClass());
        this.userRepository = userRepository;
    }


    @Override
    public List<EndUser> getAll() {
        return userRepository.findAll();
    }

    @Override
    public EndUser get(int userID) {
        return userRepository.get(userID);
    }

    @Override
    public EndUser add(EndUser endUser) {
        if (
                !this.usernameExists(endUser.getUsername())
                && !this.emailExists(endUser.getEmail())
        ) {
            return userRepository.add(endUser);
        } else {
            return null;
        }
    }

    @Override
    public EndUser getByUsername(String username) {
        List<EndUser> list = userRepository.findByUsername(username);
        if (list.size() > 1) {
            logger.error("More then one user was returned when searching by username. Usernames should be unique.");
        }
        return list.get(0);
    }

    @Override
    public List<EndUser> getByFirstName(String firstName) {
        return userRepository.findByFirstName(firstName);
    }

    @Override
    public List<EndUser> getByLastName(String lastName) {
        return userRepository.findByLastName(lastName);
    }

    @Override
    public EndUser getByEmail(String email) {
        List<EndUser> list = userRepository.findByEmail(email);
        if (list.size() > 1) {
            logger.error("More then one user was returned when searching by email. Emails should be unique.");
        }
        return list.get(0);
    }

    @Override
    public List<EndUser> getByCity(String city) {
        return userRepository.findByCity(city);
    }

    @Override
    public List<EndUser> getByStateRegion(String stateRegion) {
        return userRepository.findByStateRegion(stateRegion);
    }

    @Override
    public List<EndUser> getByZip(String zip) {
        return userRepository.findByZip(zip);
    }

    @Override
    public List<EndUser> getByCountry(String country) {
        return userRepository.findByCountry(country);
    }

    @Override
    public boolean usernameExists(String username) {
        return this.getByUsername(username) != null;
    }

    @Override
    public boolean emailExists(String email) {
        return this.getByEmail(email) != null;
    }
}
