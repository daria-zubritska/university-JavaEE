package kma.topic2.junit.repository;

import kma.topic2.junit.model.NewUser;
import kma.topic2.junit.model.User;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class IntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @ParameterizedTest
    @ValueSource(strings = {"login4", "login5", "login6"})
    void testSaveUser(String login) {
        userRepository.saveNewUser(NewUser.of(login, "passw", "fullname"));

        org.junit.jupiter.api.Assertions.assertEquals(
                User.builder()
                        .fullName("fullname")
                        .login(login)
                        .password("passw")
                        .build(), userRepository.getUserByLogin(login));

    }

    @ParameterizedTest
    @ValueSource(strings = {"login1", "login2", "login3"})
    void testCheckUser(String login) {
        org.junit.jupiter.api.Assertions.assertTrue(userRepository.isLoginExists(login));
    }

}
