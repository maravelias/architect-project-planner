package eu.maravelias.architectprojectplanner.view.client;

import com.vaadin.flow.router.Route;
import eu.maravelias.architectprojectplanner.entity.Client;
import eu.maravelias.architectprojectplanner.view.main.MainView;
import io.jmix.flowui.view.DialogMode;
import io.jmix.flowui.view.LookupComponent;
import io.jmix.flowui.view.StandardListView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;


@Route(value = "clients", layout = MainView.class)
@ViewController(id = "client.list")
@ViewDescriptor(path = "client-list-view.xml")
@LookupComponent("clientsDataGrid")
@DialogMode(width = "64em")
public class ClientListView extends StandardListView<Client> {
}