package library.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import library.App;
import library.domain.Book;
import library.domain.User;
import library.service.Service;
import library.validators.exceptions.RepositoryException;

import java.io.IOException;
import java.util.List;

public class LibrarianController {

    public TableView table;
    public TableColumn isbnCol;
    public TableColumn titleCol;
    public TableColumn authorCol;
    public TableColumn noCopiesCol;

    private Service service;
    private User user;

    public void setService(Service service, User user) {
        this.service = service;
        this.user = user;
    }

    public void initialize_() throws RepositoryException {

        isbnCol.setCellValueFactory(new PropertyValueFactory("ISBN"));
        authorCol.setCellValueFactory(new PropertyValueFactory("author"));
        titleCol.setCellValueFactory(new PropertyValueFactory("title"));
        noCopiesCol.setCellValueFactory(new PropertyValueFactory("noCopies"));
        List<Book> books = service.getBooks();
        ObservableList<Book> data = FXCollections.observableArrayList(books);
        table.setItems(data);

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
