package kma.topic2.junit.service;

import kma.topic2.junit.model.NewUser;
import kma.topic2.junit.model.User;
import kma.topic2.junit.repository.UserRepository;
import kma.topic2.junit.service.UserService;
import kma.topic2.junit.validation.UserValidator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

@SpringBootTest
public class IntegrationTest {

    @SpyBean
    private UserRepository userRepository;
    @SpyBean
    private UserValidator userValidator;
    @Autowired
    private UserService userService;

    @ParameterizedTest
    @ValueSource(strings = {"login4", "login5", "login6"})
    void testNewUserCreation(String login) {
        userService.createNewUser(NewUser.of(login, "passw", "fullname"));

        org.junit.jupiter.api.Assertions.assertEquals(
                User.builder()
                        .fullName("fullname")
                        .login(login)
                        .password("passw")
                        .build(), userService.getUserByLogin(login));
    }

}
