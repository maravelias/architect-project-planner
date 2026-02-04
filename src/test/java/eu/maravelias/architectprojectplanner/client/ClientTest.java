package eu.maravelias.architectprojectplanner.client;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import eu.maravelias.architectprojectplanner.entity.Client;
import eu.maravelias.architectprojectplanner.entity.Project;
import eu.maravelias.architectprojectplanner.entity.Status;
import eu.maravelias.architectprojectplanner.test_support.AuthenticatedAsAdmin;
import io.jmix.core.DataManager;
import jakarta.validation.ConstraintViolationException;
import java.sql.Timestamp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ExtendWith(AuthenticatedAsAdmin.class)
@ActiveProfiles("test")
public class ClientTest {

    @Autowired
    DataManager dataManager;

    @Autowired
    JdbcTemplate jdbcTemplate;

    Client savedClient;
    Project savedProject;

    @Test
    void test_saveAndLoad() {
        Client client = dataManager.create(Client.class);
        client.setName("Test-Client-" + System.currentTimeMillis());
        client.setDescription("This is a test client.");
        savedClient = dataManager.save(client);

        Client loadedClient = dataManager.load(Client.class).id(client.getId()).one();
        assertThat(loadedClient).isEqualTo(client);
    }

    @Test
    void test_nameIsMandatory() {
        Client client = dataManager.create(Client.class);
        client.setDescription("Missing name should fail validation.");

        assertThatThrownBy(() -> dataManager.save(client))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void test_softDeleteClient() {
        Client client = dataManager.create(Client.class);
        client.setName("Test-Client-" + System.currentTimeMillis());
        savedClient = dataManager.save(client);

        dataManager.remove(savedClient);

        assertThat(dataManager.load(Client.class)
                .id(savedClient.getId())
                .optional())
                .isEmpty();

        Timestamp deletedDate = jdbcTemplate.queryForObject(
                "select DELETED_DATE from CLIENT where ID = ?",
                Timestamp.class,
                savedClient.getId());
        assertThat(deletedDate).isNotNull();
    }

    @Test
    void test_deleteClientCascadesProjects() {
        Client client = dataManager.create(Client.class);
        client.setName("Test-Client-" + System.currentTimeMillis());
        savedClient = dataManager.save(client);

        Project project = dataManager.create(Project.class);
        project.setClient(savedClient);
        project.setProjectName("Test-Project-" + System.currentTimeMillis());
        project.setStatus(Status.IN_PROGRESS);
        savedProject = dataManager.save(project);

        Client clientToRemove = dataManager.load(Client.class)
                .id(savedClient.getId())
                .fetchPlan(builder -> builder.add("projects"))
                .one();
        dataManager.remove(clientToRemove);

        assertThat(dataManager.load(Project.class)
                .id(savedProject.getId())
                .optional())
                .isEmpty();

        Timestamp deletedDate = jdbcTemplate.queryForObject(
                "select DELETED_DATE from PROJECT where ID = ?",
                Timestamp.class,
                savedProject.getId());
        assertThat(deletedDate).isNotNull();

    }

    @AfterEach
    void tearDown() {
        if (savedProject != null) {
            jdbcTemplate.update("delete from PROJECT where ID = ?", savedProject.getId());
        }
        if (savedClient != null) {
            jdbcTemplate.update("delete from CLIENT where ID = ?", savedClient.getId());
        }
    }

}
