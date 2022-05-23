package library.repository;

import library.domain.Subscriber;
import library.domain.User;
import library.repository.interfaces.IUserRepository;
import library.utils.HibernateSession;
import library.validators.IValidator;
import library.validators.exceptions.UserException;
import library.validators.exceptions.ValidationException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class HUserRepository implements IUserRepository {

    IValidator<User> validator;

    public HUserRepository(IValidator<User> userValidator) {
        this.validator = userValidator;
    }

    @Override
    public User findUser(String username) throws UserException, ValidationException {
        if(username.equals("") || username==null) throw new ValidationException("Username can't be null!");
        SessionFactory sessionFactory = HibernateSession.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("from User where username = :user_");
            query.setParameter("user_", username);
            List result = query.getResultList();
            session.getTransaction().commit();
            if(result.size() == 0) throw new UserException("User not found!");
            return (User) result.get(0);
        }
    }

    public void addSubscriber(Subscriber subscriber){

        SessionFactory sessionFactory = HibernateSession.getSessionFactory();
        try(Session session = sessionFactory.openSession()){

            session.beginTransaction();
            User user = new User(subscriber.getUsername(), subscriber.getPassword(), "SUBSCRIBER");
            session.save(user);
            session.save(subscriber);
            session.getTransaction().commit();

        }
        HibernateSession.close();

    }

}
