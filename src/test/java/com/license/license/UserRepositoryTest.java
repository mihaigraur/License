package com.license.license;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {
    @Autowired
    private UserRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testUserCreation(){
        User user = new User();
        user.setEmail("mihaiclau6@gmai.com");
        user.setFirstName("Mihai");
        user.setLastName("Graur");
        user.setPassword("password");
        user.setConfirmPassword("password");
        user.setPhone("0786545666");

        User userSaved = repository.save(user);

        User userExist = entityManager.find(User.class, userSaved.getId());

        assertEquals(userExist.getEmail(), user.getEmail());
    }

    // @Test
    // public void testFindUserByEmail(){
    //     String email = "mihaiclau6@gmai.com";
    //     User user = repository.findByEmal(email);

    //     assertNotEquals(user, null);
    // }
    
}
