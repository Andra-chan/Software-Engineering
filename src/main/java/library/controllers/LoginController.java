package library.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import library.App;
import library.domain.User;
import library.service.Service;
import library.validators.exceptions.RepositoryException;
import library.validators.exceptions.UserException;
import library.validators.exceptions.ValidationException;

import java.io.IOException;

public class LoginController {

    public TextField username;
    public TextField password;
    @FXML
    Button loginButton;
    @FXML
    Button registerButton;
    private Service service;

    public void setService(Service service) {
        this.service = service;
    }

    private void openSubscriberPage(User user) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("subscriberPage.fxml"));
            AnchorPane root = fxmlLoader.load();
            App.currentStage.setScene(new Scene(root, root.getPrefWidth(), root.getPrefHeight()));
            App.currentStage.setMinHeight(root.getPrefHeight());
            App.currentStage.setMinWidth(root.getPrefWidth());
            App.currentStage.setResizable(false);

            SubscriberController controller = fxmlLoader.getController();
            controller.setService(this.service, user);
            controller.initialize_();
        } catch (IOException | RepositoryException ex) {
            ex.printStackTrace();
        }
    }

    private void openLibrarianPage(User user) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("librarianPage.fxml"));
            AnchorPane root = fxmlLoader.load();
            App.currentStage.setScene(new Scene(root, root.getPrefWidth(), root.getPrefHeight()));
            App.currentStage.setMinHeight(root.getPrefHeight());
            App.currentStage.setMinWidth(root.getPrefWidth());
            App.currentStage.setResizable(false);

            LibrarianController controller = fxmlLoader.getController();
            controller.setService(this.service, user);
            controller.initialize_();
        } catch (IOException | RepositoryException ex) {
            ex.printStackTrace();
        }
    }

    public void onLoginButtonClick() {
        String user = username.getText();
        String pass = password.getText();
        try {
            String type = service.loginState(new User(user, pass, "unknown"));
            switch (type) {
                case "SUBSCRIBER": {
                    openSubscriberPage(new User(user, pass, type));
                    break;
                }
                case "LIBRARIAN":
                    System.out.println("Librarian");
                    openLibrarianPage(new User(user, pass, type));
                    break;
                default:
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Wrong credentials!");
                    alert.setHeaderText("");
                    alert.setContentText("Username or password are incorrect.");
                    alert.show();
                    break;
            }
        } catch (IOException | UserException | ValidationException | RepositoryException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("A problem appeared!");
            alert.setHeaderText("");
            alert.setContentText(ex.getMessage());
            alert.show();
        }
    }

    /**
     * Switch to register window
     */
    public void onRegisterButtonClick() {
        App.changeSceneToRegister(service);
    }

}
