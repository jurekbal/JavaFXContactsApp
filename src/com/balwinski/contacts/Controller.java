package com.balwinski.contacts;

import com.balwinski.contacts.datamodel.Contact;
import com.balwinski.contacts.datamodel.ContactData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.util.Optional;

public class Controller {

    @FXML
    private TableView<Contact> tableView;

    public void initialize() {
        tableView.setItems(ContactData.getInstance().getContacts());
    }

    public void showNewContactDialog(ActionEvent actionEvent) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(tableView.getScene().getWindow());
        dialog.setTitle("Add new contact");
        dialog.setHeaderText("Fill details of the contact");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("contactDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Couldn't load the dialog");
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
            DialogController controller = fxmlLoader.getController();
            Contact contact = controller.addNewContact();
        }
    }
}
