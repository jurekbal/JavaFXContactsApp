package com.balwinski.contacts;

import com.balwinski.contacts.datamodel.Contact;
import com.balwinski.contacts.datamodel.ContactData;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;

import java.io.IOException;
import java.util.Optional;

public class Controller {

    @FXML
    private TableView<Contact> tableView;
    @FXML
    private ContextMenu contextMenu;

    public void initialize() {
//        SortedList<Contact> sortedList = new SortedList<>(ContactData.getInstance().getContacts(),
//                ((o1, o2) -> o1.getLastName().compareTo(o2.getLastName()))
//        );
//        sortedList.comparatorProperty().bind(tableView.comparatorProperty());
//        tableView.setItems(sortedList);
        tableView.setItems(ContactData.getInstance().getContacts());

        //uzyskujemy dostęp do wiersza (TableRow) aby przypisać właściwy kontekst dla menu podręcznego
        tableView.setRowFactory(param -> {
            final TableRow<Contact> row = new TableRow<>();
            contextMenu = new ContextMenu();
            MenuItem deleteMenuItem = new MenuItem("Delete contact");
            deleteMenuItem.setOnAction(event -> delete(row.getItem()));
            contextMenu.getItems().addAll(deleteMenuItem);

            // ustawiamy menu tylko jeśli jest wiersz pod wskaźnikiem
            row.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
                if (isNowEmpty) {
                    row.setContextMenu(null);
                } else {
                    row.setContextMenu(contextMenu);
                }
            });
            //można też to zrobić z wykorzystaniem Bindings zamiast listener
            //https://web.archive.org/web/20140406113922/https://www.marshall.edu/genomicjava/2013/12/30/javafx-tableviews-with-contextmenus/

            return row;
        });
    }

    private void delete(Contact contact) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete contact");
        alert.setHeaderText("Delete contact " + contact.getFirstName() + " " + contact.getLastName() + "?");
        alert.setContentText("Are You Sure? Press OK to confirm");
        Optional<ButtonType> confirmation = alert.showAndWait();
        if (confirmation.isPresent() && confirmation.get() == ButtonType.OK) {
            ContactData.getInstance().deleteContact(contact);
        }
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
            ContactData.getInstance().addContact(contact);
        }
    }

    public void showEditContactDialog(ActionEvent actionEvent) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(tableView.getScene().getWindow());
        dialog.setTitle("Edit contact");
        dialog.setHeaderText("Edit details of the contact");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("contactDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Couldn't load the dialog");
            return;
        }

        DialogController dialogController = fxmlLoader.getController();
        Contact contact = tableView.getSelectionModel().getSelectedItem();
        dialogController.editContact(contact);

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
            dialogController.updateContact(contact);
        }
    }

    public void handleDelete(ActionEvent actionEvent) {
        Contact contact = tableView.getSelectionModel().getSelectedItem();
        delete(contact);
    }

    public void handleKeyPressed(KeyEvent keyEvent) {
        Contact contact = tableView.getSelectionModel().getSelectedItem();
        if(contact != null) {
            if(keyEvent.getCode().equals(KeyCode.DELETE)){
                delete(contact);
            }
        }

    }

    public void handleExit(ActionEvent actionEvent) {
        Platform.exit();
    }

}
