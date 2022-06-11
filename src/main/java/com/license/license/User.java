package com.license.license;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "usersRegistered")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name= "firstName", nullable = false, length = 255)
    private String firstName;

    @Column(name= "lastName", nullable = false, length = 255)
    private String lastName;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name= "phone", nullable = false, length = 20)   //intrebare: cum se face sa mi afiseze un mesaj de eroare nu sa dea crash aplicatia
    private String phone;

    @Column(name= "password", nullable = false, length = 64)
    private String password;
    
    //@Pattern(regexp = "^((?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%&*]))", message = "Password must contain atleast 1 uppercase, 1 lowercase, 1 digit and 1 special character")
    @Column(name= "confirmPassword", nullable = false, length = 64)
    private String confirmPassword;

    public User(){
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
    public void setFirstName(String fisrtName) {
        this.firstName = fisrtName;
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

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    public String toString() {
        return "User [confirmPassword=" + confirmPassword + ", email=" + email + ", firstName=" + firstName + ", id="
                + id + ", lastName=" + lastName + ", password=" + password + ", phone=" + phone + "]";
    }
}
