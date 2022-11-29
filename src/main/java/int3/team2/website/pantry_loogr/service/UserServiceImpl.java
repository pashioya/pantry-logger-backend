package int3.team2.website.pantry_loogr.service;

import int3.team2.website.pantry_loogr.domain.EndUser;
import int3.team2.website.pantry_loogr.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
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


    public EndUser authenticate(String username, String password) {
        if(this.usernameExists(username)) {
            EndUser user = this.getByUsername(username);
            if (user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
    @Override
    public EndUser getByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    @Override
    public EndUser getByEmail(String email) {
        return userRepository.findByEmail(email);
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
