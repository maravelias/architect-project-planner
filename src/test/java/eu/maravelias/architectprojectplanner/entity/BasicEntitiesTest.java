package eu.maravelias.architectprojectplanner.entity;

import static org.assertj.core.api.Assertions.assertThat;

import eu.maravelias.architectprojectplanner.test_support.AuthenticatedAsAdmin;
import eu.maravelias.architectprojectplanner.test_support.TestDataFactory;
import io.jmix.core.DataManager;
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
public class BasicEntitiesTest {

    @Autowired
    DataManager dataManager;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    TestDataFactory testDataFactory;

    Task savedTask;
    Deliverable savedDeliverable;
    Phase savedPhase;
    Project savedPhaseProject;
    Client savedPhaseClient;
    WeeklyCapacity savedWeeklyCapacity;
    TimeLog savedTimeLog;

    @Test
    void test_taskCrudAndSoftDelete() {
        savedTask = testDataFactory.newTask();

        Task loaded = dataManager.load(Task.class).id(savedTask.getId()).one();
        assertThat(loaded).isEqualTo(savedTask);
        assertThat(loaded.getCreatedDate()).isNotNull();
        assertThat(loaded.getCreatedBy()).isNotNull();

        assertSoftDelete(Task.class, "TASK", savedTask.getId());
    }

    @Test
    void test_deliverableCrudAndSoftDelete() {
        savedDeliverable = testDataFactory.newDeliverable();

        Deliverable loaded = dataManager.load(Deliverable.class).id(savedDeliverable.getId()).one();
        assertThat(loaded).isEqualTo(savedDeliverable);
        assertThat(loaded.getCreatedDate()).isNotNull();
        assertThat(loaded.getCreatedBy()).isNotNull();

        assertSoftDelete(Deliverable.class, "DELIVERABLE", savedDeliverable.getId());
    }

    @Test
    void test_phaseCrudAndSoftDelete() {
        savedPhase = testDataFactory.newPhase();
        savedPhaseProject = savedPhase.getProject();
        savedPhaseClient = savedPhaseProject == null ? null : savedPhaseProject.getClient();

        Phase loaded = dataManager.load(Phase.class).id(savedPhase.getId()).one();
        assertThat(loaded).isEqualTo(savedPhase);
        assertThat(loaded.getCreatedDate()).isNotNull();
        assertThat(loaded.getCreatedBy()).isNotNull();

        assertSoftDelete(Phase.class, "PHASE", savedPhase.getId());
    }

    @Test
    void test_weeklyCapacityCrudAndSoftDelete() {
        savedWeeklyCapacity = testDataFactory.newWeeklyCapacity();

        WeeklyCapacity loaded = dataManager.load(WeeklyCapacity.class).id(savedWeeklyCapacity.getId()).one();
        assertThat(loaded).isEqualTo(savedWeeklyCapacity);
        assertThat(loaded.getCreatedDate()).isNotNull();
        assertThat(loaded.getCreatedBy()).isNotNull();

        assertSoftDelete(WeeklyCapacity.class, "WEEKLY_CAPACITY", savedWeeklyCapacity.getId());
    }

    @Test
    void test_timeLogCrudAndSoftDelete() {
        savedTimeLog = testDataFactory.newTimeLog();

        TimeLog loaded = dataManager.load(TimeLog.class).id(savedTimeLog.getId()).one();
        assertThat(loaded).isEqualTo(savedTimeLog);
        assertThat(loaded.getCreatedDate()).isNotNull();
        assertThat(loaded.getCreatedBy()).isNotNull();

        assertSoftDelete(TimeLog.class, "TIME_LOG", savedTimeLog.getId());
    }

    @AfterEach
    void tearDown() {
        if (savedTask != null) {
            jdbcTemplate.update("delete from TASK where ID = ?", savedTask.getId());
        }
        if (savedDeliverable != null) {
            jdbcTemplate.update("delete from DELIVERABLE where ID = ?", savedDeliverable.getId());
        }
        if (savedPhase != null) {
            jdbcTemplate.update("delete from PHASE where ID = ?", savedPhase.getId());
        }
        if (savedPhaseProject != null) {
            jdbcTemplate.update("delete from PROJECT where ID = ?", savedPhaseProject.getId());
        }
        if (savedPhaseClient != null) {
            jdbcTemplate.update("delete from CLIENT where ID = ?", savedPhaseClient.getId());
        }
        if (savedWeeklyCapacity != null) {
            jdbcTemplate.update("delete from WEEKLY_CAPACITY where ID = ?", savedWeeklyCapacity.getId());
        }
        if (savedTimeLog != null) {
            jdbcTemplate.update("delete from TIME_LOG where ID = ?", savedTimeLog.getId());
        }
    }

    private <T> void assertSoftDelete(Class<T> entityClass, String tableName, Object id) {
        dataManager.remove(dataManager.getReference(entityClass, id));

        assertThat(dataManager.load(entityClass).id(id).optional()).isEmpty();

        Timestamp deletedDate = jdbcTemplate.queryForObject(
                "select DELETED_DATE from " + tableName + " where ID = ?",
                Timestamp.class,
                id);
        assertThat(deletedDate).isNotNull();
    }
}
