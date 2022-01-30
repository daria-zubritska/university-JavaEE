import kma.topic2.junit.exceptions.ConstraintViolationException;
import kma.topic2.junit.model.NewUser;
import kma.topic2.junit.repository.UserRepository;
import kma.topic2.junit.service.UserService;
import kma.topic2.junit.validation.UserValidator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private UserValidator userValidator;
    @Autowired
    private UserService userService;

    @ParameterizedTest
    @ValueSource(strings = {"ps", "password too long", "symbols%+", ""})
    void testNewUserWithIncorrectPassword(String password) {
        Assertions.assertThatThrownBy(() -> userService.createNewUser(NewUser.of("login", password, "fullname")))
                .isInstanceOf(ConstraintViolationException.class);

        Mockito.verify(userValidator).validateNewUser(ArgumentMatchers.any());
    }

}
