package dream.development.hibernate.dao.hibernate;

import dream.development.hibernate.dao.interfaces.OrdersDao;
import dream.development.hibernate.model.Orders;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Hibernate Order DAO
 * Created by Splayd on 27.06.2017.
 */
public class HOrderDao implements OrdersDao {

    private SessionFactory sessionFactory;

    public static final Logger LOGGER = LoggerFactory.getLogger(HOrderDao.class);

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void insert(Orders orders) {
        sessionFactory.getCurrentSession().save(orders);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Orders> getAll() {
        return sessionFactory.getCurrentSession().createQuery("SELECT o FROM Orders o ORDER BY o.id").list();
    }

    @Override
    public List<Orders> getOpened() {
        return sessionFactory.getCurrentSession().createQuery("SELECT o FROM Orders o WHERE o.isClosed = false ORDER BY o.id").list();
    }

    @Override
    public List<Orders> getClosed() {
        return sessionFactory.getCurrentSession().createQuery("SELECT o FROM Orders o WHERE o.isClosed = true ORDER BY o.id").list();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Orders getById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("SELECT o FROM Orders o WHERE o.id = :id");
        query.setParameter("id", id);
        return (Orders) query.uniqueResult();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void remove(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("DELETE FROM Orders o WHERE o.id  = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void update(Orders order) {
        sessionFactory.getCurrentSession().saveOrUpdate(order);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Long lastId() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("SELECT count(o) FROM Orders o");
        return (Long) query.uniqueResult();
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
