package library.repository;

import library.domain.BorrowedBook;
import library.repository.interfaces.IBorrowedBookRepository;
import library.validators.exceptions.RepositoryException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BorrowedBookRepository implements IBorrowedBookRepository {

    private final String url;
    private final String username;
    private final String password;

    public BorrowedBookRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public void add(BorrowedBook book) throws RepositoryException {

        if (findOne(book.getISBN(), book.getUsername()))
            return;
        else {
            String sql = "insert into borrowed_book(isbn, username, date_of_borrow, date_of_return, no_copies) values (?, ?, ?, ?, ?)";
            try (Connection connection = DriverManager.getConnection(url, username, password);
                 PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, book.getISBN());
                ps.setString(2, book.getUsername());
                ps.setString(3, book.getDateOfBorrow());
                ps.setString(4, book.getDateOfReturn());
                ps.setInt(5, book.getNoCopies());

                ps.executeUpdate();
            } catch (SQLException e) {
                throw new RepositoryException("SQL exception!");
            }
        }
    }


     public List<BorrowedBook> getAll() throws RepositoryException{
        List<BorrowedBook> books = new ArrayList<>();
         try (Connection connection = DriverManager.getConnection(url, username, password);
              PreparedStatement statement = connection.prepareStatement("SELECT * from borrowed_book")) {
             ResultSet resultSet = statement.executeQuery();

             while (resultSet.next()) {
                 String isbnNew = resultSet.getString("isbn");
                 String usernameNew = resultSet.getString("username");
                 String dateOfBorrow = resultSet.getString("date_of_borrow");
                 String dateOfReturn = resultSet.getString("date_of_return");
                 Integer noCopies = resultSet.getInt("no_copies");

                 BorrowedBook book = new BorrowedBook(isbnNew, usernameNew, dateOfBorrow, dateOfReturn, noCopies);
                 books.add(book);
             }
             return books;
         } catch (SQLException e) {
             throw new RepositoryException("SQL Exception!");
         }
    }

    private Boolean findOne(String ISBN, String user) throws RepositoryException {

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from borrowed_book WHERE isbn =? and username = ?")) {
            statement.setString(1, ISBN);
            statement.setString(2, user);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            throw new RepositoryException("SQL Exception!");
        }
        return false;
    }

    public Integer delete(String isbn, String user) throws RepositoryException {
        String search ="SELECT * from borrowed_book WHERE isbn =? and username = ?";
        String sql = "DELETE FROM borrowed_book WHERE isbn = ? and username = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement psearch = connection.prepareStatement(search);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            psearch.setString(1, isbn);
            psearch.setString(2, user);
            ResultSet resultSet = psearch.executeQuery();

            while (resultSet.next()) {
                String isbnNew = resultSet.getString("isbn");
                String usernameNew = resultSet.getString("username");
                String dateOfBorrow = resultSet.getString("date_of_borrow");
                String dateOfReturn = resultSet.getString("date_of_return");
                Integer noCopies = resultSet.getInt("no_copies");

                BorrowedBook book = new BorrowedBook(isbnNew, usernameNew, dateOfBorrow, dateOfReturn, noCopies);

                ps.setString(1, isbn);
                ps.setString(2, user);
                ps.executeUpdate();
                return book.getNoCopies();
            }
        } catch (SQLException e) {
            throw new RepositoryException("SQL exception!");
        }
        return null;
    }
}

