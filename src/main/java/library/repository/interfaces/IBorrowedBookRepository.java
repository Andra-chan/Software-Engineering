package library.repository.interfaces;

import library.domain.BorrowedBook;

public interface IBorrowedBookRepository {

    void add(BorrowedBook book);

    Integer delete(String ISBN, String username);

}