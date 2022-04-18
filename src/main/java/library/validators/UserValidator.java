package library.validators;

import library.domain.User;
import library.validators.exceptions.ValidationException;

public class UserValidator implements IValidator<User> {

    private String errors = "";

    @Override
    public void validate(User entity) throws ValidationException {
        if (entity.getUsername() == null || entity.getUsername().equals("")) {
            errors += "Invalid username!\n";
        }
        if (entity.getPassword().length() == 0) {
            errors += "Password can't be null!\n";
        }
        if (errors.length() > 0) {
            throw new ValidationException(errors);
        }
    }
}
