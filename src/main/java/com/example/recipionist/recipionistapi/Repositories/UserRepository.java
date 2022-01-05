package com.example.recipionist.recipionistapi.Repositories;

import com.example.recipionist.recipionistapi.Models.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

//this interface is responsible for database access
@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    @Query("SELECT u FROM users u WHERE u.email = ?1")
    Optional<User> findUserByEmail(String email);

    @Modifying
    @Transactional
    @Query(
            nativeQuery = true,
            value = "UPDATE users SET first_name = ?1 WHERE email = ?2"
    )
    int updateStudentNameByEmail(String firstName, String email);

    /*
    @Query(
            value = "SELECT * FROM users u WHERE u.email = ?1",
            nativeQuery = true
    )
    Optional<User> findUserByEmail(String email);


     */

    /*
    //SELECT * FROM user WHERE email = ?
    @Query("SELECT u FROM User u WHERE u.email = ?1") //to find a user with the same email address
    Optional<User> findUserByEmail(String email);


    */


}

