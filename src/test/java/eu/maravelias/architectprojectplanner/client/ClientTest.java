package eu.maravelias.architectprojectplanner.client;

import static org.assertj.core.api.Assertions.assertThat;

import eu.maravelias.architectprojectplanner.entity.Client;
import eu.maravelias.architectprojectplanner.entity.User;
import eu.maravelias.architectprojectplanner.test_support.AuthenticatedAsAdmin;
import io.jmix.core.DataManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

@SpringBootTest
@ExtendWith(AuthenticatedAsAdmin.class)
public class ClientTest {

    @Autowired
    DataManager dataManager;

    Client savedClient;

    @Test
    void test() {
        // Create and save a new Client
        Client client = dataManager.create(Client.class);
        client.setName("Test-Client-"+System.currentTimeMillis());
        client.setDescription("This is a test client.");
        savedClient = dataManager.save(client);

        // Check the new user can be loaded
        Client loadedClient = dataManager.load(Client.class).id(client.getId()).one();
        assertThat(loadedClient).isEqualTo(client);

    }

    @AfterEach
    void tearDown() {
        if (savedClient != null)
            dataManager.remove(savedClient);
    }

}