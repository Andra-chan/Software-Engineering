package library.repository;

import library.domain.Book;
import library.repository.interfaces.IBookRepository;
import library.validators.IValidator;
import library.validators.exceptions.RepositoryException;
import library.validators.exceptions.ValidationException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookRepository implements IBookRepository {

    private final String url;
    private final String username;
    private final String password;
    IValidator<Book> valid;

    public BookRepository(String url, String username, String password, IValidator<Book> bookValidator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.valid = bookValidator;
    }

    @Override
    public void addBook(Book book) throws ValidationException, RepositoryException {

        valid.validate(book);
        if (findBook(book.getISBN()) != null)
            updateBook(book);
        else {
            String sql = "insert into book(isbn, title, author, no_books) values (?, ?, ?, ?)";
            try (Connection connection = DriverManager.getConnection(url, username, password);
                 PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, book.getISBN());
                ps.setString(2, book.getTitle());
                ps.setString(3, book.getAuthor());
                ps.setInt(4, book.getNoCopies());

                ps.executeUpdate();
            } catch (SQLException e) {
                throw new RepositoryException("SQL exception!");
            }
        }

    }

    @Override
    public void deleteBook(String ISBN) throws ValidationException, RepositoryException {

        if (ISBN == null || ISBN.equals("")) throw new ValidationException("ISBN can't be null!");
        String sql = "DELETE FROM book WHERE isbn = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, ISBN);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("SQL exception!");
        }

    }

    @Override
    public void updateBook(Book book) throws ValidationException, RepositoryException {

        valid.validate(book);
        String sql = "UPDATE book SET title = ?, author = ?, no_books = ? WHERE isbn = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setInt(3, book.getNoCopies());
            ps.setString(4, book.getISBN());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("SQL exception!");
        }
    }

    @Override
    public Book findBook(String ISBN) throws ValidationException, RepositoryException {

        if (ISBN == null || ISBN.equals("")) throw new ValidationException("ISBN can't be null!");
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from book WHERE isbn =?")) {
            statement.setString(1, ISBN);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String isbn = resultSet.getString("isbn");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                Integer noBooks = resultSet.getInt("no_books");

                Book book = new Book(isbn, title, author, noBooks);
                return book;
            }
        } catch (SQLException e) {
            throw new RepositoryException("SQL Exception!");
        }
        return null;
    }


    @Override
    public List<Book> getAll() throws RepositoryException {

        List<Book> books = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from book");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String isbn = resultSet.getString("isbn");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                Integer noBooks = resultSet.getInt("no_books");

                Book book = new Book(isbn, title, author, noBooks);
                books.add(book);
            }
            return books;
        } catch (SQLException e) {
            throw new RepositoryException("SQL Exception!");
        }
    }

}
