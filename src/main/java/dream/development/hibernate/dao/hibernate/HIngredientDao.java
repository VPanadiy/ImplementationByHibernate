package dream.development.hibernate.dao.hibernate;

import dream.development.hibernate.dao.interfaces.IngredientDao;
import dream.development.hibernate.model.Ingredient;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Hibernate Ingredient DAO
 * Created by Splayd on 01.07.2017.
 */
public class HIngredientDao implements IngredientDao {

    private SessionFactory sessionFactory;

    public static final Logger LOGGER = LoggerFactory.getLogger(HDishDao.class);

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void insert(Ingredient ingredient) {
        sessionFactory.getCurrentSession().save(ingredient);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Ingredient> getAll() {
        return sessionFactory.getCurrentSession().createQuery("SELECT i FROM Ingredient i ORDER BY i.id").list();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Ingredient getById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("SELECT i FROM Ingredient i WHERE i.id = :id");
        query.setParameter("id", id);
        return (Ingredient) query.uniqueResult();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Ingredient getByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("SELECT i FROM Ingredient i WHERE i.name LIKE :name");
        query.setParameter("name", name);
        return (Ingredient) query.uniqueResult();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void remove(String name) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("DELETE FROM Ingredient i WHERE i.name LIKE :name");
        query.setParameter("name", name);
        query.executeUpdate();
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
