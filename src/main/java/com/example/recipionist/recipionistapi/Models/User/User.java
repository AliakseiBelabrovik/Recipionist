package com.example.recipionist.recipionistapi.Models.User;

import com.example.recipionist.recipionistapi.Models.Meals.Meal;
import com.example.recipionist.recipionistapi.Models.Meals.MealIngredient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
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
public class User {

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

    @Column(name = "email", nullable = false, columnDefinition = "TEXT")
    private String email;

    @Column(name = "password", nullable = false, columnDefinition = "TEXT")
    private String password;

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


    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

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

    public String getPassword() {
        return password;
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
