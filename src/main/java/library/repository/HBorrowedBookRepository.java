package library.repository;

import library.domain.BorrowedBook;
import library.repository.interfaces.IBorrowedBookRepository;
import library.utils.HibernateSession;
import library.validators.exceptions.RepositoryException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class HBorrowedBookRepository implements IBorrowedBookRepository {
    @Override
    public void add(BorrowedBook book) {

        SessionFactory sessionFactory = HibernateSession.getSessionFactory();
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            if(findOne(book.getISBN(), book.getUsername()))
                update(book);
            else {
                session.save(book);
                session.getTransaction().commit();
            }
        }
        HibernateSession.close();

    }

    private void update(BorrowedBook book){

    }

    private Boolean findOne(String ISBN, String username) {

        SessionFactory sessionFactory = HibernateSession.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("from BorrowedBook where ISBN = :isbn_ and username = :username_");
            query.setParameter("isbn_", ISBN);
            query.setParameter("username_", username);
            List result = query.getResultList();
            session.getTransaction().commit();
            if (result.size() != 0) {
                return true;
            } else {
                return false;
            }
        }

    }

    public Integer delete(String isbn, String username){

        SessionFactory sessionFactory = HibernateSession.getSessionFactory();
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            Query query = session.createQuery("from BorrowedBook where ISBN = :isbn_ and username = :user_");
            query.setParameter("isbn_", isbn);
            query.setParameter("user_", username);
            List result = query.getResultList();
            BorrowedBook borBook = (BorrowedBook) result.get(0);
            session.delete(borBook);
            session.getTransaction().commit();
            return borBook.getNoCopies();
        }

    }

    @Override
    public List<BorrowedBook> getAll() throws RepositoryException {

        SessionFactory sessionFactory = HibernateSession.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List result = session.createQuery("from BorrowedBook").list();
            List<BorrowedBook> books = new ArrayList<>();
            for(Object o : result){
                BorrowedBook book = (BorrowedBook) o;
                books.add(book);
            }
            session.getTransaction().commit();
            return books;
        }
    }

}
