package com.balwinski.contacts;

import com.balwinski.contacts.datamodel.ContactData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void init() {
        ContactData.getInstance().loadContacts();
//        ContactData.getInstance().loadTestContacts();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("mainwindow.fxml"));
        primaryStage.setTitle("JB's simple Contacts App");
        primaryStage.setScene(new Scene(root, 900, 500));
        primaryStage.show();
    }

    @Override
    public void stop() {
        ContactData.getInstance().saveContacts();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
