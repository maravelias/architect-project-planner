package eu.maravelias.architectprojectplanner.project;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import eu.maravelias.architectprojectplanner.entity.Client;
import eu.maravelias.architectprojectplanner.entity.Project;
import eu.maravelias.architectprojectplanner.entity.Status;
import eu.maravelias.architectprojectplanner.test_support.AuthenticatedAsAdmin;
import io.jmix.core.DataManager;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.PersistenceException;
import jakarta.validation.ConstraintViolationException;
import java.sql.Timestamp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ExtendWith(AuthenticatedAsAdmin.class)
@ActiveProfiles("test")
public class ProjectTest {

    @Autowired
    DataManager dataManager;

    @Autowired
    JdbcTemplate jdbcTemplate;

    Client savedClient;
    Project savedProject;

    @Test
    void test_saveLoadUpdate() {
        savedClient = newClient();
        Project project = newProject(savedClient);
        savedProject = dataManager.save(project);

        Project loadedProject = dataManager.load(Project.class).id(savedProject.getId()).one();
        assertThat(loadedProject).isEqualTo(savedProject);

        loadedProject.setProjectName("Updated-Project-" + System.currentTimeMillis());
        loadedProject.setStatus(Status.UNDER_REVIEW);
        savedProject = dataManager.save(loadedProject);

        Project updatedProject = dataManager.load(Project.class).id(savedProject.getId()).one();
        assertThat(updatedProject.getProjectName()).isEqualTo(loadedProject.getProjectName());
        assertThat(updatedProject.getStatus()).isEqualTo(loadedProject.getStatus());
    }

    @Test
    void test_projectNameIsMandatory() {
        savedClient = newClient();
        Project project = newProject(savedClient);
        project.setProjectName(null);

        assertThatThrownBy(() -> dataManager.save(project))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void test_statusIsMandatory() {
        savedClient = newClient();
        Project project = newProject(savedClient);
        project.setStatus(null);

        assertThatThrownBy(() -> dataManager.save(project))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void test_clientIsMandatory() {
        Project project = dataManager.create(Project.class);
        project.setProjectName("Test-Project-" + System.currentTimeMillis());
        project.setStatus(Status.IN_PROGRESS);

        assertThatThrownBy(() -> dataManager.save(project))
                .isInstanceOfAny(ConstraintViolationException.class,
                        DataIntegrityViolationException.class,
                        PersistenceException.class);
    }

    @Test
    void test_deleteProjectDoesNotDeleteClient() {
        savedClient = newClient();
        savedProject = dataManager.save(newProject(savedClient));

        dataManager.remove(savedProject);

        assertThat(dataManager.load(Project.class)
                .id(savedProject.getId())
                .optional())
                .isEmpty();

        Timestamp clientDeletedDate = jdbcTemplate.queryForObject(
                "select DELETED_DATE from CLIENT where ID = ?",
                Timestamp.class,
                savedClient.getId());
        assertThat(clientDeletedDate).isNull();
    }

    @Test
    void test_optimisticLocking() {
        savedClient = newClient();
        savedProject = dataManager.save(newProject(savedClient));

        Project first = dataManager.load(Project.class).id(savedProject.getId()).one();
        Project second = dataManager.load(Project.class).id(savedProject.getId()).one();

        first.setProjectName("Lock-Project-" + System.currentTimeMillis());
        dataManager.save(first);

        second.setProjectName("Lock-Project-Second-" + System.currentTimeMillis());
        assertThatThrownBy(() -> dataManager.save(second))
                .isInstanceOf(OptimisticLockException.class);
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

    private Client newClient() {
        Client client = dataManager.create(Client.class);
        client.setName("Test-Client-" + System.currentTimeMillis());
        return dataManager.save(client);
    }

    private Project newProject(Client client) {
        Project project = dataManager.create(Project.class);
        project.setClient(client);
        project.setProjectName("Test-Project-" + System.currentTimeMillis());
        project.setStatus(Status.NOT_STARTED);
        return project;
    }
}
