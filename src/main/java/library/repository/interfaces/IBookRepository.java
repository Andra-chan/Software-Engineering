package library.repository.interfaces;

import library.domain.Book;
import library.validators.exceptions.RepositoryException;
import library.validators.exceptions.ValidationException;

import java.util.List;

public interface IBookRepository {

    void addBook(Book book) throws ValidationException, RepositoryException;

    void deleteBook(String ISBN) throws ValidationException, RepositoryException;

    void updateBook(Book book) throws ValidationException, RepositoryException;

    Book findBook(String ISBN) throws ValidationException, RepositoryException;

    List<Book> getAll() throws RepositoryException;

}
