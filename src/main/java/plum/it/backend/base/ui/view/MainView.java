package plum.it.backend.base.ui.view;

import plum.it.backend.base.ui.component.ViewToolbar;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

/**
 * This view shows up when a user navigates to the root ('/') of the application.
 */
@Route
public final class MainView extends Main {

    // TODO Replace with your own main view.

    MainView() {
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

        add(new ViewToolbar("Menu", searchField));
        add(new Div("Please select a view from the menu on the left."));
    }

    /**
     * Navigates to the main view.
     */
    public static void showMainView() {
        UI.getCurrent().navigate(MainView.class);
    }
}
