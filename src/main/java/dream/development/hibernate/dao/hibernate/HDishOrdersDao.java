package dream.development.hibernate.dao.hibernate;

import dream.development.hibernate.dao.interfaces.DishOrdersDao;
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
 * Hibernate DishOrders DAO
 * Created by Splayd on 29.07.2017.
 */
public class HDishOrdersDao implements DishOrdersDao {

    private SessionFactory sessionFactory;

    public static final Logger LOGGER = LoggerFactory.getLogger(HEmployeeDao.class);

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<DishOrders> getOpened() {
        return sessionFactory.getCurrentSession().createQuery("SELECT do FROM DishOrders do WHERE do.status = false ORDER BY do.id").list();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public DishOrders getById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("SELECT do FROM DishOrders do WHERE do.id = :id");
        query.setParameter("id", id);
        return (DishOrders) query.uniqueResult();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void update(DishOrders dishOrders) {
        sessionFactory.getCurrentSession().saveOrUpdate(dishOrders);
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
