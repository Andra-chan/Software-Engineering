package library.validators;

import library.domain.Book;
import library.validators.exceptions.ValidationException;

public class BookValidator implements IValidator<Book> {

    private String errors = "";

    @Override
    public void validate(Book book) throws ValidationException {

        if (book.getISBN() == null || book.getISBN().equals("")) {
            errors += "ISBN can't be null!\n";
        }
        if (book.getTitle() == null || book.getTitle().equals("")) {
            errors += "Title can't be null!\n";
        }
        if (book.getAuthor() == null || book.getAuthor().equals("")) {
            errors += "Author can't be null!\n";
        }
        if (book.getNoCopies() < 0) {
            errors += "The number of copies can't be negative!\n";
        }
        if (errors.length() > 0) {
            throw new ValidationException(errors);
        }
    }

}
