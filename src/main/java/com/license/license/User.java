package com.license.license;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "usersRegistered")
public class User implements Comparable<User>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    // @NotEmpty(message = "First name cannot be empty")
    @Column(name= "firstName", nullable = false, length = 255)
    private String firstName;

    @Column(name= "lastName", nullable = false, length = 255)
    private String lastName;
    
    @Column(name = "email", nullable = false, unique = true, length = 50)
    private String email;

    @Column(name= "phone", nullable = false, length = 20) 
    private String phone;

    @Column(name= "password", nullable = false, length = 64)
    private String password;
    
    @Column(name= "confirmPassword", nullable = false, length = 64)
    private String confirmPassword;

    private boolean enabledUser;

    @Column(name = "verificationCode", nullable = false, length = 64)
    private String verificationCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "auth_producer")
    private AuthenticationProducer authenticationProducer;

    public User(){
    }

    public User(String email) {
        this.email = email;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
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

    public boolean isEnabledUser() {
        return enabledUser;
    }

    public void setEnabledUser(boolean enabledUser) {
        this.enabledUser = enabledUser;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public AuthenticationProducer getAuthenticationProducer() {
        return authenticationProducer;
    }

    public void setAuthenticationProducer(AuthenticationProducer authenticationProducer) {
        this.authenticationProducer = authenticationProducer;
    }

    @Override
    public String toString() {
        return "User [confirmPassword=" + confirmPassword + ", email=" + email + ", firstName=" + firstName + ", id="
                + id + ", lastName=" + lastName + ", password=" + password + ", phone=" + phone + "]";
    }

    @Override
    public int compareTo(User user) {
        int compareEmail = this.email.compareTo(email);
        if(compareEmail < 0){
            return -1;
        }
        if(compareEmail > 0){
            return 1;
        }
        return 0;
    }


}
