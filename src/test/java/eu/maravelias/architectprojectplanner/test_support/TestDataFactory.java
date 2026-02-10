package eu.maravelias.architectprojectplanner.test_support;

import eu.maravelias.architectprojectplanner.entity.Client;
import eu.maravelias.architectprojectplanner.entity.Deliverable;
import eu.maravelias.architectprojectplanner.entity.Phase;
import eu.maravelias.architectprojectplanner.entity.Priority;
import eu.maravelias.architectprojectplanner.entity.Project;
import eu.maravelias.architectprojectplanner.entity.Status;
import eu.maravelias.architectprojectplanner.entity.Task;
import eu.maravelias.architectprojectplanner.entity.TimeLog;
import eu.maravelias.architectprojectplanner.entity.User;
import eu.maravelias.architectprojectplanner.entity.WeeklyCapacity;
import io.jmix.core.DataManager;
import io.jmix.core.EntityStates;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class TestDataFactory {

    private final DataManager dataManager;
    private final EntityStates entityStates;
    private final PasswordEncoder passwordEncoder;

    public TestDataFactory(DataManager dataManager, EntityStates entityStates,
                           ObjectProvider<PasswordEncoder> passwordEncoderProvider) {
        this.dataManager = dataManager;
        this.entityStates = entityStates;
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
        Deliverable deliverable = newDeliverable();
        task.setDeliverable(deliverable);
        task.setName("Test-Task-" + System.currentTimeMillis());
        task.setStatus(Status.NOT_STARTED);
        return dataManager.save(task);
    }

    public Deliverable newDeliverable() {
        Client client = newClient();
        Project project = newProject(client);
        if (entityStates.isNew(project)) {
            project = dataManager.save(project);
        }
        User owner = newUser("deliverable-owner-" + System.currentTimeMillis(), "test-password");

        Deliverable deliverable = dataManager.create(Deliverable.class);
        deliverable.setProject(project);
        deliverable.setName("Test-Deliverable-" + System.currentTimeMillis());
        deliverable.setDescription("Test deliverable description");
        deliverable.setCompletionCriteria("Test completion criteria");
        deliverable.setOwner(owner);
        deliverable.setPriority(Priority.NORMAL);
        deliverable.setStatus(Status.NOT_STARTED);
        return dataManager.save(deliverable);
    }

    public Phase newPhase() {
        Client client = newClient();
        Project project = newProject(client);
        return newPhase(project);
    }

    public Phase newPhase(Project project) {
        Project savedProject = project;
        if (savedProject.getClient() != null && entityStates.isNew(savedProject.getClient())) {
            savedProject.setClient(dataManager.save(savedProject.getClient()));
        }
        if (entityStates.isNew(savedProject)) {
            savedProject = dataManager.save(savedProject);
        }
        Phase phase = dataManager.create(Phase.class);
        phase.setProject(savedProject);
        phase.setName("Test-Phase-" + System.currentTimeMillis());
        phase.setStatus(Status.NOT_STARTED);
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
