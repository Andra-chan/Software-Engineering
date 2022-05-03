package library.repository.interfaces;

import library.domain.BorrowedBook;
import library.validators.exceptions.RepositoryException;

import java.util.List;

public interface IBorrowedBookRepository {

    void add(BorrowedBook book) throws RepositoryException;

    Integer delete(String ISBN, String username) throws RepositoryException;

    List<BorrowedBook> getAll() throws RepositoryException;

}