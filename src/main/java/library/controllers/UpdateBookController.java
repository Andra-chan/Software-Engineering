package library.controllers;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import library.App;
import library.domain.Book;
import library.domain.User;
import library.service.Service;

import java.io.IOException;

public class UpdateBookController {
    public TextField ISBNField;
    public TextField titleField;
    public TextField authorField;
    public TextField copiesField;
    public Label label;
    private Service service;
    private User user;

    public void setService(Service service, User user) {
        this.service = service;
        this.user = user;
    }

    public void handleUpdate() {

        try {
            String isbn = ISBNField.getText();
            String title = titleField.getText();
            String author = authorField.getText();
            String noCopies = copiesField.getText();
            Book book = new Book(isbn, title, author, Integer.parseInt(noCopies));
            service.updateBook(book);
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
