package plum.it.backend.customermanagement.ui.view;

import plum.it.backend.base.ui.component.ViewToolbar;
import plum.it.backend.customermanagement.domain.Customer;
import plum.it.backend.customermanagement.service.CustomerService;

import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H2;

import com.vaadin.flow.theme.lumo.LumoUtility;

@Route("customer/create")
@PageTitle("Create Customer")
public class CustomerCreateView extends Main {

    private final CustomerService customerService;

    final TextField forename;
    final TextField surname;
    final TextField streetname;
    final TextField streetnumber;
    final TextField city;
    final TextField plz;
    final TextField country;
    final TextField email;
    final TextField mobil;
    final TextField contactperson;

    final Button createBtn;

    public CustomerCreateView(CustomerService customerService) {
        this.customerService = customerService;
            
        // Optionales Styling
        setSizeFull();
        addClassNames(LumoUtility.BoxSizing.BORDER, LumoUtility.Display.FLEX, LumoUtility.FlexDirection.COLUMN,
                LumoUtility.Padding.MEDIUM, LumoUtility.Gap.SMALL);
        add(new ViewToolbar("Menu"));

        H2 title = new H2("Create Customer");
        add(title);

        forename = new TextField();
        forename.setPlaceholder("John");
        forename.setLabel("Forename");
        forename.setMaxLength(Customer.DESCRIPTION_MAX_LENGTH);
        forename.setMinWidth("20em");

        surname = new TextField();
        surname.setPlaceholder("Smith");
        surname.setLabel("Surname");
        surname.setMaxLength(Customer.DESCRIPTION_MAX_LENGTH);
        surname.setMinWidth("20em");

        add(ViewToolbar.group(forename, surname));

        streetname = new TextField();
        streetname.setPlaceholder("Universitätsstraße");
        streetname.setLabel("Streetname");
        streetname.setMaxLength(Customer.DESCRIPTION_MAX_LENGTH);
        streetname.setMinWidth("20em");

        streetnumber = new TextField();
        streetnumber.setPlaceholder("100");
        streetnumber.setLabel("Streetnumber");
        streetnumber.setMaxLength(Customer.DESCRIPTION_MAX_LENGTH);
        streetnumber.setMinWidth("20em");

        add(ViewToolbar.group(streetname, streetnumber));

        city = new TextField();
        city.setPlaceholder("Dortmund");
        city.setLabel("City");
        city.setMaxLength(Customer.DESCRIPTION_MAX_LENGTH);
        city.setMinWidth("20em");

        plz = new TextField();
        plz.setPlaceholder("44226");
        plz.setLabel("PLZ");
        plz.setMaxLength(Customer.DESCRIPTION_MAX_LENGTH);
        plz.setMinWidth("19em");

        add(ViewToolbar.group(plz, city));

        country = new TextField();
        country.setPlaceholder("Deutschland");
        country.setLabel("Country");
        country.setMaxLength(Customer.DESCRIPTION_MAX_LENGTH);
        country.setMinWidth("20em");

        add(ViewToolbar.group(country));

        email = new TextField();
        email.setPlaceholder("john.smith@email.com");
        email.setLabel("Email");
        email.setMaxLength(Customer.DESCRIPTION_MAX_LENGTH);
        email.setMinWidth("20em");

        mobil = new TextField();
        mobil.setPlaceholder("+49 123 45678910");
        mobil.setLabel("Mobil");
        mobil.setMaxLength(Customer.DESCRIPTION_MAX_LENGTH);
        mobil.setMinWidth("19em");

        add(ViewToolbar.group(email, mobil));
        
        contactperson = new TextField();
        contactperson.setPlaceholder("Max Müller");
        contactperson.setLabel("Contact person");
        contactperson.setMaxLength(Customer.DESCRIPTION_MAX_LENGTH);
        contactperson.setMinWidth("20em");

        add(ViewToolbar.group(contactperson));

        createBtn = new Button("Create", event -> {createCustomer();
            UI.getCurrent().navigate("customer/list"); // or the correct path
            });
        createBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        add(ViewToolbar.group(createBtn));
    }

    private void createCustomer() {
        customerService.createCustomer(forename.getValue(),surname.getValue(),streetname.getValue(),streetnumber.getValue(),city.getValue(),plz.getValue(),country.getValue(),email.getValue(),mobil.getValue(),contactperson.getValue());
        forename.clear();
        surname.clear();
        streetname.clear();
        streetnumber.clear();
        city.clear();
        plz.clear();
        country.clear();
        email.clear();
        mobil.clear();
        contactperson.clear();

        Notification.show("Customer added", 3000, Notification.Position.BOTTOM_END)
                .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }
}