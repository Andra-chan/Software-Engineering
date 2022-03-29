package library.repository.interfaces;

import library.domain.Book;
import library.validators.exceptions.ValidationException;

import java.util.List;

public interface IBookRepository {

    void addBook(Book book) throws ValidationException;

    void deleteBook(String ISBN) throws ValidationException;

    void updateBook(Book book) throws ValidationException;

    Book findBook(String ISBN) throws ValidationException;

    List<Book> getAll();

}
