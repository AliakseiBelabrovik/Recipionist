package com.example.recipionist.recipionistapi.Registration;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
@AllArgsConstructor
public class EmailValidator implements Predicate<String> {

    @Override
    public boolean test(String s) {
        //TODO: Regex to validate email
        return true;
    }
}
