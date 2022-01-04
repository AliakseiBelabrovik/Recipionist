package com.example.recipionist.recipionistapi.Services;

import com.example.recipionist.recipionistapi.Models.User.User;
import com.example.recipionist.recipionistapi.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service //we tell the Spring that this is service class
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void addNewUser(User user) {

        Optional<User> userOptional = userRepository.findUserByEmail(user.getEmail());
        System.out.println("Optional user is " + userOptional);
        if (userOptional.isPresent()) {
            System.out.println("Email taken by " + userOptional);
            throw new IllegalStateException("Email taken");
        }
        System.out.println("Adding new user " + user);


        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        boolean exists = userRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException("User with id " + id + " does not exist.");
        }
        userRepository.deleteById(id);
    }

    public void updateUser(Long id, String firstName, String lastName, String email) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("User with id" + id + "does not exist"));

        if (firstName != null && firstName.length() > 0 && !Objects.equals(user.getFirstName(), firstName)) {
            user.setFirstName(firstName);
        }
        if (lastName != null && lastName.length() > 0 && !Objects.equals(user.getLastName(), lastName)) {
            user.setLastName(lastName);
        }
        if (email != null && email.length() > 0 && !Objects.equals(user.getEmail(), email)) {

            Optional<User> userOptional = userRepository.findUserByEmail(user.getEmail());
            if (userOptional.isPresent()) {
                throw new IllegalStateException("Email taken");
            }
            user.setEmail(email);
        }

    }

}
