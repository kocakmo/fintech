package com.fincom.fintech.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Entity(name = "users")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Email
    private String email;
    @Size(min = 4, message = "Username should have at least 4 characters")
    @NotNull
    private String userName;


    private String password;

    @Size(min = 2, message = "Name should have at least 2 characters")
    @NotNull
    private String firstName;

    @Size(min = 2, message = "Surname should have at least 2 characters")
    @NotNull
    private String lastName;
    @PastOrPresent
    @NotNull
    private LocalDate birthDate;

    @NotNull
    private String nationalIdNumber;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<MarketOrder> orders;

    public User() {
    }

    public User(Long userId, String email, String userName, String password, String firstName, String lastName, LocalDate birthDate, String nationalIdNumber, List<MarketOrder> orders) {
        this.userId = userId;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.nationalIdNumber = nationalIdNumber;
        this.orders = orders;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getNationalIdNumber() {
        return nationalIdNumber;
    }

    public void setNationalIdNumber(String nationalIdNumber) {
        this.nationalIdNumber = nationalIdNumber;
    }

    public List<MarketOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<MarketOrder> orders) {
        this.orders = orders;
    }

    public boolean isValidNationalIdNumber() {
        if (nationalIdNumber.length() != 11 || nationalIdNumber.charAt(0) == '0') {
            return false;
        }
        int oddSum = 0, evenSum = 0, controlDigit = 0;
        for (int i = 0; i <= 8; i++) {
            if (i % 2 == 0) {
                oddSum += Character.getNumericValue(nationalIdNumber.charAt(i));

            } else {
                evenSum += Character.getNumericValue(nationalIdNumber.charAt(i));
            }
        }
        controlDigit = (oddSum * 7 - evenSum) % 10;
        if (Character.getNumericValue(nationalIdNumber.charAt(9)) != controlDigit) {
            return false;
        }
        if (Character.getNumericValue(nationalIdNumber.charAt(10)) != (controlDigit + evenSum + oddSum) % 10) {
            return false;
        }
        return true;
    }


    public boolean isUserAdult() {

        LocalDate currentDate = LocalDate.now();
        Period age = Period.between(birthDate, currentDate);
        return age.getYears() >= 18;
    }

}
