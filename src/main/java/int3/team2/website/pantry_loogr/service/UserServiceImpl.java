package int3.team2.website.pantry_loogr.service;

import int3.team2.website.pantry_loogr.domain.EndUser;
import int3.team2.website.pantry_loogr.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
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
    public List<EndUser> add(EndUser endUser) {
        return null;
    }

    @Override
    public List<EndUser> getByUsername(String username) {
        return userRepository.findByUsername(username);
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
    public List<EndUser> getByEmail(String email) {
        return userRepository.findByEmail(email);
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
}
