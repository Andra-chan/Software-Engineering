package library.repository;

import library.domain.Subscriber;
import library.domain.User;
import library.repository.interfaces.IUserRepository;
import library.validators.IValidator;
import library.validators.exceptions.RepositoryException;
import library.validators.exceptions.UserException;
import library.validators.exceptions.ValidationException;

import java.sql.*;

public class UserRepository implements IUserRepository {

    private final String url;
    private final String username;
    private final String password;
    IValidator<User> validator;

    public UserRepository(String url, String username, String password, IValidator<User> userValidator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = userValidator;
    }

    @Override
    public User findUser(String usernm) throws UserException, ValidationException, RepositoryException {
        if (username.equals("") || username == null) throw new ValidationException("Username can't be null!");
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from users WHERE username =?")) {
            statement.setString(1, usernm);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String usernameNew = resultSet.getString("username");
                String password = resultSet.getString("pass");
                String type = resultSet.getString("type_u");

                User user = new User(usernameNew, password, type);
                return user;
            }
        } catch (SQLException e) {
            throw new RepositoryException("SQL Exception!");
        }
        throw new UserException("User not found!");
    }

    public void addSubscriber(Subscriber subscriber) throws RepositoryException {

        String sql_user = "insert into users(username, pass, type_u) values (?, ?, ?)";
        String sql_subscriber = "insert into subscriber(first_name, last_name, cnp, address, phone_number, username) values (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql_user);
             PreparedStatement ps_sub = connection.prepareStatement(sql_subscriber)) {
            ps.setString(1, subscriber.getUsername());
            ps.setString(2, subscriber.getPassword());
            ps.setString(3, "SUBSCRIBER");
            ps.executeUpdate();

            ps_sub.setString(1, subscriber.getFirstName());
            ps_sub.setString(2, subscriber.getLastName());
            ps_sub.setString(3, subscriber.getCNP());
            ps_sub.setString(4, subscriber.getAddress());
            ps_sub.setString(5, subscriber.getPhone());
            ps_sub.setString(6, subscriber.getUsername());
            ps_sub.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("SQL exception!");
        }
    }

}

