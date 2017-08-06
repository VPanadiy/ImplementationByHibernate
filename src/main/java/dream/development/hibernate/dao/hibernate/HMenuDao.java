package dream.development.hibernate.dao.hibernate;

import dream.development.hibernate.dao.interfaces.MenuDao;
import dream.development.hibernate.model.Menu;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Hibernate Menu DAO
 * Created by Splayd on 01.07.2017.
 */
public class HMenuDao implements MenuDao {

    private SessionFactory sessionFactory;

    public static final Logger LOGGER = LoggerFactory.getLogger(HMenuDao.class);

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void insert(Menu menu) {
        sessionFactory.getCurrentSession().save(menu);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Menu> getAll() {
        return sessionFactory.getCurrentSession().createQuery("SELECT m FROM Menu m ORDER BY m.id").list();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Menu getById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("SELECT m FROM Menu m WHERE m.id = :id");
        query.setParameter("id", id);
        return (Menu) query.uniqueResult();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Menu getByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("SELECT m FROM Menu m WHERE m.name LIKE :name");
        query.setParameter("name", name);
        return (Menu) query.uniqueResult();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void remove(String name) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("DELETE FROM Menu m WHERE m.name LIKE :name");
        query.setParameter("name", name);
        query.executeUpdate();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void update(Menu menu) {
        sessionFactory.getCurrentSession().saveOrUpdate(menu);
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
