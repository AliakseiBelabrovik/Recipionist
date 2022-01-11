package com.example.recipionist.recipionistapi.Services;

import com.example.recipionist.recipionistapi.Models.User.User;
import com.example.recipionist.recipionistapi.Registration.token.ConfirmationToken;
import com.example.recipionist.recipionistapi.Registration.token.ConfirmationTokenService;
import com.example.recipionist.recipionistapi.Repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;


@Service //we tell the Spring that this is service class
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final String USER_NOT_FOUND_MSG = "User with email %s not found";
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private ConfirmationTokenService confirmationTokenService;
    /*
    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
     */


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /*
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

     */

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


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(email)
                .orElseThrow(()->
                        new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }

    public String singUpUser(User user) {
        boolean userExists = userRepository.findUserByEmail(user.getEmail()).isPresent();

        //TODO: change the way the problem with already existing user is handled
        if (userExists) {
            // TODO check of attributes are the same and
            // TODO if email not confirmed send confirmation email.
            throw new IllegalStateException("email already taken");
        }
        //encoding the password
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        //saving the user in the database
        userRepository.save(user);


        //creating token for email verification
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(30),
                user
        );
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        //TODO: Send confirmation token via email

        return token;
    }

    public int enableUser(String email) {
        return userRepository.enableUser(email);
    }

    public User getCurrentUser() {

        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        currentUser = userRepository.findById(currentUser.getId()).get();
        return currentUser;
    }

}
