package com.example.recipionist.recipionistapi.Registration;

import com.example.recipionist.recipionistapi.Models.User.UserRole;
import com.example.recipionist.recipionistapi.Models.User.User;
import com.example.recipionist.recipionistapi.Registration.token.ConfirmationToken;
import com.example.recipionist.recipionistapi.Registration.token.ConfirmationTokenService;
import com.example.recipionist.recipionistapi.Services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final UserService userService;
    private final EmailValidator emailValidator;
    private final ConfirmationTokenService confirmationTokenService;

    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());

        //TODO: change the way the exception is handled
        if (!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }

        return userService.singUpUser(User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(request.getPassword())
                .userRole(UserRole.USER)
                .locked(false)
                .enabled(true) //change to false, if want to use email verification
                .build());
    }

    @Transactional //we have two update statements in this method
    //setConfirmedAt and enableUser
    //->@Transactional used to define that it should be either updated both or nothing
    public String confirmToken(String token) {
        //check if confirmationToken is already in the database
        ConfirmationToken confirmationToken = confirmationTokenService
                .getConfirmationTokenByToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        //check if the token already confirmed
        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }
        //check if the token already expired
        LocalDateTime expiredAt = confirmationToken.getExpiresAt();
        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        //update confirmedAt column in database
        confirmationTokenService.updateConfirmedAt(token);
        //update user in database -> enable him/her
        userService.enableUser(confirmationToken.getUser().getEmail());


        return "confirmed";

    }




}
