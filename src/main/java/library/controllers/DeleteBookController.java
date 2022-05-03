package library.controllers;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import library.App;
import library.domain.User;
import library.service.Service;

import java.io.IOException;

public class DeleteBookController {
    public TextField ISBNField;
    public Label label;
    private Service service;
    private User user;

    public void setService(Service service, User user) {
        this.service = service;
        this.user = user;
    }

    public void handleDelete() {

        try {
            String isbn = ISBNField.getText();
            service.deleteBook(isbn);
            label.setVisible(true);
        }
        catch(Exception ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("A problem appeared!");
            alert.setHeaderText("");
            alert.setContentText(ex.getMessage());
            alert.show();
        }

    }

    public void onLogoutButtonClick() {
        App.changeSceneToLogin(service);
    }

    public void onMenuAddClick(){
        App.changeSceneToAddBook(service, user);
    }

    public void onMenuUpdateClick(){
        App.changeSceneToUpdateBook(service, user);
    }

    public void onMenuDeleteClick(){
        App.changeSceneToDeleteBook(service, user);
    }

    public void onMenuReturnClick(){
        App.changeSceneToReturnBook(service, user);
    }

    public void onMenuClick() throws IOException {
        App.openLibrarianPage(service, user);
    }
}
