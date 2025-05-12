package plum.it.backend.customermanagement.ui.view;

import plum.it.backend.base.ui.component.ViewToolbar;
import plum.it.backend.customermanagement.domain.Customer;
import plum.it.backend.customermanagement.service.CustomerService;

import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.textfield.TextField;

import com.vaadin.flow.theme.lumo.LumoUtility;

import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;

import static com.vaadin.flow.spring.data.VaadinSpringDataHelpers.toSpringPageRequest;

@Route("customer/list")
@PageTitle("Customer List")
@Menu(order = 0, icon = "vaadin:clipboard-check", title = "Customer List")
public class CustomerListView extends Main implements BeforeEnterObserver{

    private Customer selectedCustomer;

    final Grid<Customer> customerGrid;

    public CustomerListView(CustomerService customerService) {
        addClassName(LumoUtility.Padding.MEDIUM);
   
        // Create a search field
        TextField searchField = new TextField();
        searchField.setPlaceholder("Search...");
        searchField.setClearButtonVisible(true);
        searchField.setWidth("250px"); // fixed width for alignment
        searchField.addClassName(LumoUtility.Margin.Start.AUTO); // push to right

        // Optional: Add search logic
        searchField.addValueChangeListener(event -> {
            String searchQuery = event.getValue();
            System.out.println("Search: " + searchQuery); // Replace with real logic
        });

        Button addCustomerButton = new Button("Add Customer");

        // Set up navigation
        addCustomerButton.addClickListener(event -> 
            UI.getCurrent().navigate("customer/create")
        );

        Button editCustomerButton = new Button("Edit Customer", event -> {
            if (selectedCustomer != null) {
                UI.getCurrent().navigate("customer/edit/" + selectedCustomer.getId());
            }
        });
        editCustomerButton.setEnabled(false); // Initially disabled

        customerGrid = new Grid<>();
        
        Button deleteCustomerButton = new Button("Delete Customer", event -> {
            if (selectedCustomer != null) {
                customerService.deleteCustomer(selectedCustomer.getId()); // Ensure this method exists
                customerGrid.getDataProvider().refreshAll();
                selectedCustomer = null;
                editCustomerButton.setEnabled(false);
            }
        });
        deleteCustomerButton.setEnabled(false); // Initially disabled
        
        // Optionales Styling
        setSizeFull();
        addClassNames(LumoUtility.BoxSizing.BORDER, LumoUtility.Display.FLEX, LumoUtility.FlexDirection.COLUMN,
                LumoUtility.Padding.MEDIUM, LumoUtility.Gap.SMALL);
        add(new ViewToolbar("Menu", ViewToolbar.group(addCustomerButton, editCustomerButton, deleteCustomerButton, searchField)));

        H2 title = new H2("Customer List");
        add(title);

        customerGrid.setItems(query -> customerService.list(toSpringPageRequest(query)).stream());
        customerGrid.addColumn(Customer::getForename).setHeader("Forename");
        customerGrid.addColumn(Customer::getSurname).setHeader("Surname");
        customerGrid.addColumn(Customer::getStreetname).setHeader("Streetname");
        customerGrid.addColumn(Customer::getStreetnumber).setHeader("Streetnumber");
        customerGrid.addColumn(Customer::getCity).setHeader("City");
        customerGrid.addColumn(Customer::getPlz).setHeader("PLZ");
        customerGrid.addColumn(Customer::getCountry).setHeader("Country");
        customerGrid.addColumn(Customer::getEmail).setHeader("Email");
        customerGrid.addColumn(Customer::getMobil).setHeader("Mobil");
        customerGrid.addColumn(Customer::getContactperson).setHeader("Contact Person");
        customerGrid.setSizeFull();

        customerGrid.asSingleSelect().addValueChangeListener(event -> {
            selectedCustomer = event.getValue();
            boolean hasSelection = selectedCustomer != null;
            editCustomerButton.setEnabled(hasSelection);
            deleteCustomerButton.setEnabled(hasSelection);
        });

        add(customerGrid);

    }
    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        customerGrid.getDataProvider().refreshAll(); // 🔁 Refresh on view entry
    }
}