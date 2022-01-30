import kma.topic2.junit.exceptions.ConstraintViolationException;
import kma.topic2.junit.exceptions.LoginExistsException;
import kma.topic2.junit.model.NewUser;
import kma.topic2.junit.model.User;
import kma.topic2.junit.repository.UserRepository;
import kma.topic2.junit.service.UserService;
import kma.topic2.junit.validation.UserValidator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Spy
    private UserValidator userValidator;
    @InjectMocks
    private UserService userService;

    @ParameterizedTest
    @ValueSource(strings = {"ps", "password too long", "symbols%+", ""})
    void testNewUserWithIncorrectPassword(String password) {
        Assertions.assertThatThrownBy(() -> userService.createNewUser(NewUser.of(password, password, "fullname")))
                .isInstanceOf(ConstraintViolationException.class);

        Mockito.verify(userValidator).validateNewUser(ArgumentMatchers.any());
    }

    @ParameterizedTest
    @ValueSource(strings = {"login1", "login2", "login3"})
    void testNewUserWithIllegalLogin(String login) {
        Assertions.assertThatThrownBy(() -> userService.createNewUser(NewUser.of(login, "passw", "fullname")))
                .isInstanceOf(LoginExistsException.class);

        Mockito.verify(userValidator).validateNewUser(ArgumentMatchers.any());
    }

    @ParameterizedTest
    @ValueSource(strings = {"login4", "login5", "login6"})
    void testNewUserWith(String login) {
        userService.createNewUser(NewUser.of(login, "passw", "fullname"));

        org.junit.jupiter.api.Assertions.assertEquals(
                User.of(login, "passw", "fullname"), userService.getUserByLogin(login));
    }

}
