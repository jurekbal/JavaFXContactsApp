package com.balwinski.contacts;

import com.balwinski.contacts.datamodel.Contact;
import com.balwinski.contacts.datamodel.ContactData;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class Controller {

    @FXML
    private TableView<Contact> tableView;

    public void initialize() {
        tableView.setItems(ContactData.getInstance().getContacts());
    }
}
