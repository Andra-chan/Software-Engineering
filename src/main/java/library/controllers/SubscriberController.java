package library.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import library.domain.Book;
import library.domain.User;
import library.service.Service;
import library.validators.exceptions.RepositoryException;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SubscriberController {

    public TableView table;
    public TableColumn isbnCol;
    public TableColumn authorCol;
    public TableColumn titleCol;
    public TableColumn noCopiesCol;

    public TextArea textArea;
    public Button borrowBtn;

    private User user;
    private Service service;
    private Map<Book, Integer> booksToBorrow = new LinkedHashMap<>();

    public void setService(Service service, User user) {
        this.service = service;
        this.user = user;
    }

    public void initialize() {
        table.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                Book book = (Book) table.getSelectionModel().getSelectedItem();
                addToCart(book);
                table.getSelectionModel().clearSelection();
            }
        });
    }

    private void addToCart(Book book) {

        textArea.appendText("ISBN: " + book.getISBN() + ", Title: " + book.getTitle() + ", Author: " + book.getAuthor() + "\n");
        if (booksToBorrow.containsKey(book))
            booksToBorrow.put(book, booksToBorrow.get(book) + 1);
        else
            booksToBorrow.putIfAbsent(book, 1);

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

    public void onBorrowButtonClick(ActionEvent actionEvent) throws RepositoryException {

        service.borrowBooks(user, booksToBorrow);
        textArea.setText("");
        booksToBorrow.clear();
        initialize_();

    }

}
