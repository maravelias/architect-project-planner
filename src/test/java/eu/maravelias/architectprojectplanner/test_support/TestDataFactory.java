package eu.maravelias.architectprojectplanner.test_support;

import eu.maravelias.architectprojectplanner.entity.Client;
import eu.maravelias.architectprojectplanner.entity.Deliverable;
import eu.maravelias.architectprojectplanner.entity.Phase;
import eu.maravelias.architectprojectplanner.entity.Project;
import eu.maravelias.architectprojectplanner.entity.Status;
import eu.maravelias.architectprojectplanner.entity.Task;
import eu.maravelias.architectprojectplanner.entity.TimeLog;
import eu.maravelias.architectprojectplanner.entity.User;
import eu.maravelias.architectprojectplanner.entity.WeeklyCapacity;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class TestDataFactory {

    private final DataManager dataManager;
    private final PasswordEncoder passwordEncoder;

    public TestDataFactory(DataManager dataManager, ObjectProvider<PasswordEncoder> passwordEncoderProvider) {
        this.dataManager = dataManager;
        this.passwordEncoder = passwordEncoderProvider.getIfAvailable();
    }

    public Client newClient() {
        return newClient("Test-Client-" + System.currentTimeMillis());
    }

    public Client newClient(String name) {
        Client client = dataManager.create(Client.class);
        client.setName(name);
        return dataManager.save(client);
    }

    public Project newProject(Client client) {
        return newProject(client, Status.NOT_STARTED, "Test-Project-" + System.currentTimeMillis());
    }

    public Project newProject(Client client, Status status, String projectName) {
        Project project = dataManager.create(Project.class);
        project.setClient(client);
        project.setProjectName(projectName);
        project.setStatus(status);
        return project;
    }

    public User newUser(String username, String rawPassword) {
        User user = dataManager.create(User.class);
        user.setUsername(username);
        if (passwordEncoder != null) {
            user.setPassword(passwordEncoder.encode(rawPassword));
        } else {
            user.setPassword(rawPassword);
        }
        return dataManager.save(user);
    }

    public Task newTask() {
        Task task = dataManager.create(Task.class);
        return dataManager.save(task);
    }

    public Deliverable newDeliverable() {
        Deliverable deliverable = dataManager.create(Deliverable.class);
        return dataManager.save(deliverable);
    }

    public Phase newPhase() {
        Phase phase = dataManager.create(Phase.class);
        return dataManager.save(phase);
    }

    public WeeklyCapacity newWeeklyCapacity() {
        WeeklyCapacity weeklyCapacity = dataManager.create(WeeklyCapacity.class);
        return dataManager.save(weeklyCapacity);
    }

    public TimeLog newTimeLog() {
        TimeLog timeLog = dataManager.create(TimeLog.class);
        return dataManager.save(timeLog);
    }
}
