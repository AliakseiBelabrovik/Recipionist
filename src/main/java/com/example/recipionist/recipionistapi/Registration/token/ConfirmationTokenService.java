package com.example.recipionist.recipionistapi.Registration.token;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {

    private final ConfirmationTokenRep confirmationTokenRep;

    public void saveConfirmationToken(ConfirmationToken confirmationToken) {
        confirmationTokenRep.save(confirmationToken);
    }

    public Optional<ConfirmationToken> getConfirmationTokenByToken(String token) {
        return confirmationTokenRep.findConfirmationTokenByToken(token);
    }
    public int updateConfirmedAt(String token) {
        return confirmationTokenRep.updateConfirmedAt(token, LocalDateTime.now());
    }





}
