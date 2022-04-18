package library.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import library.App;
import library.domain.Subscriber;
import library.service.Service;
import library.validators.exceptions.ValidationException;

public class RegisterController {
    public TextField firstName;
    public TextField secondName;
    public TextField phoneNumber;
    public TextField cnp;
    public TextField address;
    public TextField username;
    public TextField password;
    public TextField confirmPassword;
    private Service service;

    private void verifyData() throws Exception {

        if (firstName.getText().isEmpty()) throw new ValidationException("First Name shouldn't be empty!");
        if (firstName.getText().charAt(0) > 'Z')
            throw new ValidationException("First Name must start with capital letter!");
        if (secondName.getText().isEmpty()) throw new ValidationException("Last Name shouldn't be empty!");
        if (secondName.getText().charAt(0) > 'Z')
            throw new ValidationException("Last Name must start with capital letter!");
        if (cnp.getText().isEmpty()) throw new ValidationException("CNP shouldn't be empty!");
        if (address.getText().isEmpty()) throw new ValidationException("Address shouldn't be empty!");
        try {
            service.findUser(username.getText());
            throw new ValidationException("User already exists!");
        } catch (Exception ignored) {
        }
        if (!password.getText().equals(confirmPassword.getText()))
            throw new ValidationException("Passwords don't match!");


    }

    public void handleRegister(ActionEvent actionEvent) {
        try {
            verifyData();
            Subscriber subscriber = new Subscriber(firstName.getText(), secondName.getText(), cnp.getText(), address.getText(), phoneNumber.getText(), username.getText(), password.getText());
            service.addSubscriber(subscriber);
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("A problem appeared!");
            alert.setHeaderText("");
            alert.setContentText(ex.getMessage());
            alert.show();
        }

    }

    public void onReturnButtonPress() {
        App.changeSceneToLogin(service);
    }

    public void setService(Service service) {

        this.service = service;

    }
}
