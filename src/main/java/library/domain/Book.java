package library.domain;

public class Book {

    private String ISBN;
    private String title;
    private String author;
    private Integer noCopies;

    public Book() {
    }

    public Book(String ISBN, String title, String author, Integer noCopies) {
        this.ISBN = ISBN;
        this.title = title;
        this.author = author;
        this.noCopies = noCopies;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getNoCopies() {
        return noCopies;
    }

    public void setNoCopies(Integer noCopies) {
        this.noCopies = noCopies;
    }
}
