package library.repository.interfaces;

import library.domain.Subscriber;
import library.domain.User;
import library.validators.exceptions.UserException;
import library.validators.exceptions.ValidationException;

public interface IUserRepository {

    User findUser(String username) throws UserException, ValidationException;

    void addSubscriber(Subscriber subscriber);

}
