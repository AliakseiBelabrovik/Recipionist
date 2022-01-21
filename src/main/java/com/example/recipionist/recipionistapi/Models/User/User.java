package com.example.recipionist.recipionistapi.Models.User;

import com.example.recipionist.recipionistapi.Models.Meals.Meal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

//to map the User class with the database
@Entity(name = "users") //for hibernate
@Table(
        name = "users",
        uniqueConstraints = @UniqueConstraint(
                name = "email_unique",
                columnNames = "email"
        )
)//for table in database
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails {

    @Id //For database
    @SequenceGenerator( //to make it BIGSERIAL and create sequence
            name = "user_ID_seq",
            sequenceName = "user_ID_seq",
            allocationSize = 1
    )
    @GeneratedValue( //for database
            strategy = GenerationType.SEQUENCE,
            generator = "user_ID_seq"
    )
    @Column(name="ID", updatable = false)
    private Long id;

    @Column(name = "first_name", nullable = false, columnDefinition = "TEXT")
    private String firstName;

    @Column(name = "last_name", nullable = false, columnDefinition = "TEXT")
    private String lastName;

    @Enumerated(EnumType.STRING)
    private UserRole userRole; // to manage roles


    @Column(name = "email", nullable = false, columnDefinition = "TEXT")
    private String email;

    @Column(name = "password", nullable = false, columnDefinition = "TEXT")
    private String password;


    private Boolean locked = false; //default values for this boolean variables
    //set to true, after the user confirmed his email
    private Boolean enabled = false;

    /*
    @Fetch(FetchMode.JOIN)
    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "user") //already mapped by user attribute in Meal class
    private List<Meal> meals;


    public void addMeal(Meal meal) {
        addMeal(meal, true);
    }

    public void addMeal(Meal meal, boolean set) {
        if (meals == null) {
            meals = new ArrayList<>();
        }
        if (meals != null) {
            if (this.getMeals().contains(meal)) {
                this.getMeals().set(this.getMeals().indexOf(meal), meal);
            } else {
                this.getMeals().add(meal);
            }
            if (set) {
                meal.setUser(this, false);
            }
        }
    }
    public void removeMeal(Meal meal) {
        this.getMeals().remove(meal);
        meal.setUser(null);
    }
    */
    public User(String firstName,
                String lastName,
                UserRole userRole,
                String email,
                String password,
                Boolean locked,
                Boolean enabled,
                List<Meal> meals) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userRole = userRole;
        this.email = email;
        this.password = password;
        //this.meals = meals;
    }

    /*

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

     */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    /**
     * Methods from Spring Security (UserDetails interface)
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userRole.name());
        return Collections.singletonList(authority);
    }

    public String getPassword() {
        return password;
    }

    //username is email
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; //we don't manage this
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; //we don't manage this
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
