package library.repository;

import library.domain.Book;
import library.repository.interfaces.IBookRepository;
import library.utils.HibernateSession;
import library.validators.IValidator;
import library.validators.exceptions.ValidationException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class HBookRepository implements IBookRepository {

    IValidator<Book> valid;

    public HBookRepository(IValidator<Book> bookValidator) {
        this.valid = bookValidator;
    }

    @Override
    public void addBook(Book book) throws ValidationException {

        valid.validate(book);
        if(findBook(book.getISBN()) != null)
            updateBook(book);
        else {
            SessionFactory sessionFactory = HibernateSession.getSessionFactory();
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();
                session.save(book);
                session.getTransaction().commit();
            }
            HibernateSession.close();
        }

    }

    @Override
    public void deleteBook(String ISBN) throws ValidationException {

        if(ISBN==null || ISBN.equals("")) throw new ValidationException("ISBN can't be null!");
        SessionFactory sessionFactory = HibernateSession.getSessionFactory();
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            Book book = session.find(Book.class, ISBN);
            session.remove(book);
            session.delete(book);
            session.getTransaction().commit();
        }
        HibernateSession.close();

    }

    @Override
    public void updateBook(Book book) throws ValidationException {

        valid.validate(book);
        SessionFactory sessionFactory = HibernateSession.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Book book_ = session.get(Book.class, book.getISBN());
            book_.setTitle(book.getTitle());
            book_.setAuthor(book.getAuthor());
            book_.setNoCopies(book.getNoCopies());
            session.save(book_);
            session.getTransaction().commit();
        }
        HibernateSession.close();

    }

    @Override
    public Book findBook(String ISBN) throws ValidationException {

        if(ISBN==null || ISBN.equals("")) throw new ValidationException("ISBN can't be null!");
        SessionFactory sessionFactory = HibernateSession.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("from Book where ISBN = :isbn_");
            query.setParameter("isbn_", ISBN);
            List result = query.getResultList();
            session.getTransaction().commit();
            if (result.size() != 0) {
                return (Book) result.get(0);
            } else {
                return null;
            }
        }

    }

    @Override
    public List<Book> getAll() {

        SessionFactory sessionFactory = HibernateSession.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List result = session.createQuery("from Book").list();
            List<Book> books = new ArrayList<>();
            for(Object o : result){
                Book book = (Book) o;
                books.add(book);
            }
            session.getTransaction().commit();
            return books;
        }

    }
}
