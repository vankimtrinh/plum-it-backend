package plum.it.backend.customermanagement.ui.view;

import plum.it.backend.base.ui.component.ViewToolbar;
import plum.it.backend.customermanagement.domain.Customer;
import plum.it.backend.customermanagement.service.CustomerService;

import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H2;

import com.vaadin.flow.router.BeforeEnterObserver;

import com.vaadin.flow.theme.lumo.LumoUtility;
import java.util.Optional;

@Route("customer/edit/:id")
@PageTitle("Edit Customer")
public class CustomerEditView extends Main implements BeforeEnterObserver{

    private final CustomerService customerService;

    private Customer customer;

    private final TextField forename = new TextField("Forename");
    private final TextField surname = new TextField("Surname");
    private final TextField streetname = new TextField("Streetname");
    private final TextField streetnumber = new TextField("Streetnumber");
    private final TextField city = new TextField("City");
    private final TextField plz = new TextField("PLZ");
    private final TextField country = new TextField("Country");
    private final TextField email = new TextField("Email");
    private final TextField mobil = new TextField("Mobil");
    private final TextField contactperson = new TextField("Contact Person");

    private Long customerId;

    public CustomerEditView(CustomerService customerService) {

        // Optionales Styling
        setSizeFull();
        addClassNames(LumoUtility.BoxSizing.BORDER, LumoUtility.Display.FLEX, LumoUtility.FlexDirection.COLUMN,
                LumoUtility.Padding.MEDIUM, LumoUtility.Gap.SMALL);
        add(new ViewToolbar("Menu"));

        H2 title = new H2("Edit Customer");
        add(title);


        this.customerService = customerService;

        Button saveButton = new Button("Save", event -> saveCustomer());

        add(ViewToolbar.group(forename, surname));
        add(ViewToolbar.group(streetname, streetnumber));
        add(ViewToolbar.group(city, plz));
        add(ViewToolbar.group(country));
        add(ViewToolbar.group(email, mobil));
        add(ViewToolbar.group(contactperson));
        add(ViewToolbar.group(saveButton));
         }
    
    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        this.customerId = event.getRouteParameters()
                .get("id")
                .map(Long::valueOf)
                .orElse(null);

        if (customerId != null) {
            Optional<Customer> customerOpt = customerService.findById(customerId);
            if (customerOpt.isPresent()) {
                this.customer = customerOpt.get();
                populateForm();
            } else {
                Notification.show("Customer not found", 3000, Notification.Position.MIDDLE);
                UI.getCurrent().navigate("customer/list");
            }
        }
    }

    private void populateForm() {
        forename.setValue(Optional.ofNullable(customer.getForename()).orElse(""));
        surname.setValue(Optional.ofNullable(customer.getSurname()).orElse(""));
        streetname.setValue(Optional.ofNullable(customer.getStreetname()).orElse(""));
        streetnumber.setValue(Optional.ofNullable(customer.getStreetnumber()).orElse(""));
        city.setValue(Optional.ofNullable(customer.getCity()).orElse(""));
        plz.setValue(Optional.ofNullable(customer.getPlz()).orElse(""));
        country.setValue(Optional.ofNullable(customer.getCountry()).orElse(""));
        email.setValue(Optional.ofNullable(customer.getEmail()).orElse(""));
        mobil.setValue(Optional.ofNullable(customer.getMobil()).orElse(""));
        contactperson.setValue(Optional.ofNullable(customer.getContactperson()).orElse(""));
    }

    private void saveCustomer() {
        customer.setForename(forename.getValue());
        customer.setSurname(surname.getValue());
        customer.setStreetname(streetname.getValue());
        customer.setStreetnumber(streetnumber.getValue());
        customer.setCity(city.getValue());
        customer.setPlz(plz.getValue());
        customer.setCountry(country.getValue());
        customer.setEmail(email.getValue());
        customer.setMobil(mobil.getValue());
        customer.setContactperson(contactperson.getValue());

        customerService.updateCustomer(customerId,customer);
        Notification.show("Customer updated", 3000, Notification.Position.BOTTOM_END);
        UI.getCurrent().navigate("customer/list");
    }
}