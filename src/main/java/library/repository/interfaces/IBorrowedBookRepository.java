package library.repository.interfaces;

import library.domain.BorrowedBook;
import library.validators.exceptions.RepositoryException;

public interface IBorrowedBookRepository {

    void add(BorrowedBook book) throws RepositoryException;

    Integer delete(String ISBN, String username) throws RepositoryException;

}