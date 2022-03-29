package library.validators;

import library.validators.exceptions.ValidationException;

public interface Validator<entity> {
    void validate(entity e) throws ValidationException;
}
