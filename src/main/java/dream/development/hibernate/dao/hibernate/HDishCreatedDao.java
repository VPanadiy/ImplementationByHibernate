package dream.development.hibernate.dao.hibernate;

import dream.development.hibernate.dao.interfaces.DishCreatedDao;
import dream.development.hibernate.model.DishCreated;
import dream.development.hibernate.model.DishOrders;
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
 * Hibernate Dish Created DAO
 * Created by Splayd on 23.07.2017.
 */
public class HDishCreatedDao implements DishCreatedDao {

    private SessionFactory sessionFactory;

    public static final Logger LOGGER = LoggerFactory.getLogger(HDishCreatedDao.class);

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void insert(DishCreated createdDishes) {
        sessionFactory.getCurrentSession().save(createdDishes);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<DishCreated> getAll() {
        return sessionFactory.getCurrentSession().createQuery("SELECT dc FROM DishCreated dc ORDER BY dc.id").list();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void remove(Orders order) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("DELETE FROM DishCreated dc WHERE dc.orderId = :order");
        query.setParameter("order", order);
        query.executeUpdate();
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
