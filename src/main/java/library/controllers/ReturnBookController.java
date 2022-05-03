package library.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import library.App;
import library.domain.BorrowedBook;
import library.domain.User;
import library.service.Service;
import library.validators.exceptions.RepositoryException;

import java.io.IOException;
import java.util.List;

public class ReturnBookController {
    public TextField ISBNField;
    public TextField usernameField;
    public Label label;
    public TableView table;
    public TableColumn isbnCol;
    public TableColumn usernameCol;
    public TableColumn borrowCol;
    public TableColumn returnCol;
    public TableColumn noCopiesCol;
    private Service service;
    private User user;

    public void setService(Service service, User user) {
        this.service = service;
        this.user = user;
    }

    public void initialize_() throws RepositoryException {

        isbnCol.setCellValueFactory(new PropertyValueFactory("ISBN"));
        usernameCol.setCellValueFactory(new PropertyValueFactory("username"));
        borrowCol.setCellValueFactory(new PropertyValueFactory("dateOfBorrow"));
        returnCol.setCellValueFactory(new PropertyValueFactory("dateOfReturn"));
        noCopiesCol.setCellValueFactory(new PropertyValueFactory("noCopies"));
        updateModel();

    }

    public void handleReturn() {

        try {
            String isbn = ISBNField.getText();
            String username = usernameField.getText();
            service.returnBook(isbn, username);
            label.setVisible(true);
            updateModel();
        }
        catch(Exception ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("A problem appeared!");
            alert.setHeaderText("");
            alert.setContentText(ex.getMessage());
            alert.show();
        }

    }

    public void updateModel() {
        try {
            List<BorrowedBook> books = service.getBorrowedBooks();
            ObservableList<BorrowedBook> data = FXCollections.observableArrayList(books);
            table.setItems(data);
        } catch (Exception ex) {
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
