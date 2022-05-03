package library.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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

    public void handleLogin() {
        String user = username.getText();
        String pass = password.getText();
        try {
            String type = service.loginState(new User(user, pass, "unknown"));
            switch (type) {
                case "SUBSCRIBER": {
                    App.openSubscriberPage(this.service, new User(user, pass, type));
                    break;
                }
                case "LIBRARIAN":
                    System.out.println("Librarian");
                    App.openLibrarianPage(this.service, new User(user, pass, type));
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
