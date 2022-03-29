package library.domain;

public class BorrowedBook {

    private String ISBN;
    private String username;
    private String dateOfBorrow;
    private String dateOfReturn;
    private Integer noCopies;

    public BorrowedBook() {
    }

    public BorrowedBook(String ISBN, String username) {
        this.ISBN = ISBN;
        this.username = username;
    }

    public BorrowedBook(String ISBN, String username, String dateOfBorrow, String dateOfReturn, Integer noCopies) {
        this.ISBN = ISBN;
        this.username = username;
        this.dateOfBorrow = dateOfBorrow;
        this.dateOfReturn = dateOfReturn;
        this.noCopies = noCopies;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDateOfBorrow() {
        return dateOfBorrow;
    }

    public void setDateOfBorrow(String dateOfBorrow) {
        this.dateOfBorrow = dateOfBorrow;
    }

    public String getDateOfReturn() {
        return dateOfReturn;
    }

    public void setDateOfReturn(String dateOfReturn) {
        this.dateOfReturn = dateOfReturn;
    }

    public Integer getNoCopies() {
        return noCopies;
    }

    public void setNoCopies(Integer noCopies) {
        this.noCopies = noCopies;
    }
}