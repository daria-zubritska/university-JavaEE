import kma.topic2.junit.exceptions.ConstraintViolationException;
import kma.topic2.junit.exceptions.LoginExistsException;
import kma.topic2.junit.model.NewUser;
import kma.topic2.junit.repository.UserRepository;
import kma.topic2.junit.validation.UserValidator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserValidatorTest {

    @Spy
    private UserRepository userRepository;
    @InjectMocks
    @Spy
    private UserValidator userValidator;

    @ParameterizedTest
    @ValueSource(strings = {"ps", "password too long", "symbols%+", ""})
    void testIncorrectPassword(String password) {
        Assertions.assertThatThrownBy(() -> userValidator.validateNewUser(NewUser.of("login", password, "fullname")))
                .isInstanceOf(ConstraintViolationException.class);

        Mockito.verify(userValidator).validateNewUser(ArgumentMatchers.any());
    }

    @ParameterizedTest
    @ValueSource(strings = {"login1", "login2", "login3"})
    void existingUser(String login) {
        Assertions.assertThatThrownBy(() -> userValidator.validateNewUser(NewUser.of(login, "passw", "fullname")))
                .isInstanceOf(LoginExistsException.class);

        Mockito.verify(userRepository).isLoginExists(ArgumentMatchers.any());
    }

}
