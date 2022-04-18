package library.validators;

import library.validators.exceptions.ValidationException;

public interface IValidator<entity> {
    void validate(entity e) throws ValidationException;
}
