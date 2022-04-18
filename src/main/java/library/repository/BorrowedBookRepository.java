package library.repository;

import library.domain.BorrowedBook;
import library.repository.interfaces.IBorrowedBookRepository;
import library.validators.exceptions.RepositoryException;

import java.sql.*;

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
            update(book);
        else {
            String sql = "insert into BorrowedBook(isbn, username, dateOfBorrow, dateOfReturn, noCopies) values (?, ?, ?, ?, ?)";
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

    private void update(BorrowedBook book) {
    }

    private Boolean findOne(String ISBN, String username) throws RepositoryException {

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from BorrowedBook WHERE isbn =? and username = ?")) {
            statement.setString(1, ISBN);
            statement.setString(2, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            throw new RepositoryException("SQL Exception!");
        }
        return false;
    }

    public Integer delete(String isbn, String username) throws RepositoryException {

        String sql = "DELETE FROM BorrowedBook WHERE isbn = ? and username = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, isbn);
            ps.setString(2, username);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                String isbnNew = resultSet.getString("isbn");
                String usernameNew = resultSet.getString("username");
                String dateOfBorrow = resultSet.getString("dateOfBorrow");
                String dateOfReturn = resultSet.getString("dateOfReturn");
                Integer noCopies = resultSet.getInt("noCopies");

                BorrowedBook book = new BorrowedBook(isbnNew, usernameNew, dateOfBorrow, dateOfReturn, noCopies);
                return book.getNoCopies();
            }
        } catch (SQLException e) {
            throw new RepositoryException("SQL exception!");
        }
        return null;
    }
}

