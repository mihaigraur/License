package com.license.license;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> { //clasa asta face legatura cu baza de date. Cum? pai mosteneste JpaRepository care aia e baza de date in sine si are functii predefinite ca save(), permitAll() etc
    @Query("SELECT u FROM User u WHERE u.email = ?1")  //unde email u este primul parametru
    User findByEmail(String email);   //am declarat metoda asta in interfata ca sa gasim un user dupa email

    @Query("SELECT v FROM User v WHERE v.verificationCode = ?1")  //?1 inseamna ca ia primul parametru
    public User findByCodeVerification(String verificationCode);

    @Modifying
    @Query("UPDATE User u SET u.enabledUser = true WHERE u.id = ?1")
    public void enableUserForAuth(Integer id);
}
