package library.service;

import library.domain.Book;
import library.domain.BorrowedBook;
import library.domain.Subscriber;
import library.domain.User;
import library.repository.interfaces.IBookRepository;
import library.repository.interfaces.IBorrowedBookRepository;
import library.repository.interfaces.IUserRepository;
import library.validators.exceptions.RepositoryException;
import library.validators.exceptions.UserException;
import library.validators.exceptions.ValidationException;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class Service {

    IUserRepository userRepository;
    IBookRepository bookRepository;
    IBorrowedBookRepository borrowedBookRepository;

    public Service(IUserRepository userRepository, IBookRepository bookRepository, IBorrowedBookRepository borrowedBookRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.borrowedBookRepository = borrowedBookRepository;
    }

    public String loginState(User user) throws UserException, ValidationException, RepositoryException {

        if (userRepository.findUser(user.getUsername()) == null)
            return "null";
        if (userRepository.findUser(user.getUsername()).getPassword().equals(user.getPassword()))
            return userRepository.findUser(user.getUsername()).getType();
        return "null";
    }

    public List<Book> getBooks() throws RepositoryException {
        return bookRepository.getAll();
    }

    public void borrowBooks(User user, Map<Book, Integer> books) {
        books.forEach((book, noCopies) -> {
            LocalDate ld = LocalDate.now();
            BorrowedBook borrowedBook = new BorrowedBook(book.getISBN(), user.getUsername(), ld.toString(), ld.plusWeeks(2).toString(), noCopies);
            try {
                borrowedBookRepository.add(borrowedBook);
            } catch (RepositoryException e) {
                e.printStackTrace();
            }
            Book updateNoCopies = null;
            try {
                updateNoCopies = bookRepository.findBook(book.getISBN());
            } catch (ValidationException | RepositoryException ignored) {
            }
            updateNoCopies.setNoCopies(updateNoCopies.getNoCopies() - noCopies);
            try {
                bookRepository.updateBook(updateNoCopies);
            } catch (ValidationException | RepositoryException ignored) {
            }
        });
    }

    public void addBook(Book book) throws ValidationException, RepositoryException {
        bookRepository.addBook(book);
    }

    public void updateBook(Book book) throws ValidationException, RepositoryException {
        bookRepository.updateBook(book);
    }

    public void deleteBook(String ISBN) throws ValidationException, RepositoryException {
        bookRepository.deleteBook(ISBN);
    }

    public void returnBook(String ISBN, String username) throws ValidationException, RepositoryException {
        Integer noCop = borrowedBookRepository.delete(ISBN, username);
        Book book = bookRepository.findBook(ISBN);
        book.setNoCopies(book.getNoCopies() + noCop);
        bookRepository.updateBook(book);
    }

    public Boolean findUser(String username) throws UserException, ValidationException, RepositoryException {
        return userRepository.findUser(username) != null;
    }

    public void addSubscriber(Subscriber subscriber) throws RepositoryException {
        userRepository.addSubscriber(subscriber);
    }

}