package eu.maravelias.architectprojectplanner.user;

import eu.maravelias.architectprojectplanner.entity.User;
import eu.maravelias.architectprojectplanner.test_support.AuthenticatedAsAdmin;
import eu.maravelias.architectprojectplanner.test_support.TestDataFactory;
import io.jmix.core.DataManager;
import io.jmix.core.security.UserRepository;
import jakarta.persistence.OptimisticLockException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Sample integration test for the User entity.
 */
@SpringBootTest
@ExtendWith(AuthenticatedAsAdmin.class)
@ActiveProfiles("test")
public class UserTest {

    @Autowired
    DataManager dataManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TestDataFactory testDataFactory;

    User savedUser;

    @Test
    void test_saveAndLoad() {
        User user = testDataFactory.newUser("test-user-" + System.currentTimeMillis(), "test-passwd");
        savedUser = user;

        // Check the new user can be loaded
        User loadedUser = dataManager.load(User.class).id(user.getId()).one();
        assertThat(loadedUser).isEqualTo(user);

        // Check the new user is available through UserRepository
        UserDetails userDetails = userRepository.loadUserByUsername(user.getUsername());
        assertThat(userDetails).isEqualTo(user);
    }

    @Test
    void test_optimisticLocking() {
        savedUser = testDataFactory.newUser("test-user-lock-" + System.currentTimeMillis(), "test-passwd");

        User first = dataManager.load(User.class).id(savedUser.getId()).one();
        User second = dataManager.load(User.class).id(savedUser.getId()).one();

        first.setFirstName("First-" + System.currentTimeMillis());
        dataManager.save(first);

        second.setLastName("Second-" + System.currentTimeMillis());
        assertThatThrownBy(() -> dataManager.save(second))
                .isInstanceOf(OptimisticLockException.class);
    }

    @AfterEach
    void tearDown() {
        if (savedUser != null) {
            dataManager.load(User.class)
                    .id(savedUser.getId())
                    .optional()
                    .ifPresent(dataManager::remove);
        }
    }
}
