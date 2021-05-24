package web.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import web.model.User;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class UserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public List<User> getAllUsers() {
        EntityManager em = sessionFactory.createEntityManager();
        List<User> list =  em.createQuery("from User").getResultList();
        em.close();
        return list;
    }

    public void save(User user) {
        EntityManager em = sessionFactory.createEntityManager();
        em.getTransaction().begin();
        em.merge(user);
        em.getTransaction().commit();
        em.close();
    }

    public User findByUsername(String email) {
        EntityManager em = sessionFactory.createEntityManager();
        return  (User) em.createQuery("from User where email = :email")
                .setParameter("email", email).getSingleResult();
    }

    public User findById(Long id) {
        EntityManager em = sessionFactory.createEntityManager();
        return  em.find(User.class, id);
    }
}
