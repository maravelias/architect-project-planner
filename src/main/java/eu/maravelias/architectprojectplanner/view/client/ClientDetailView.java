package eu.maravelias.architectprojectplanner.view.client;

import com.vaadin.flow.router.Route;
import eu.maravelias.architectprojectplanner.entity.Client;
import eu.maravelias.architectprojectplanner.view.main.MainView;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "clients/:id", layout = MainView.class)
@ViewController(id = "client.detail")
@ViewDescriptor(path = "client-detail-view.xml")
@EditedEntityContainer("clientDc")
public class ClientDetailView extends StandardDetailView<Client> {
}