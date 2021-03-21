package com.balwinski.contacts;

import com.balwinski.contacts.datamodel.Contact;
import com.balwinski.contacts.datamodel.ContactData;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;


public class DialogController {

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextField notesField;


    public Contact addNewContact() {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String phoneNumber = phoneNumberField.getText().trim();
        String notes = notesField.getText().trim();

        Contact contact = new Contact(firstName, lastName, phoneNumber, notes);
        ContactData.getInstance().addContact(contact);
        return null;
    }
}
