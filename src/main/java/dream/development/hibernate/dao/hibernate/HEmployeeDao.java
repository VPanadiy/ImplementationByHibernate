package dream.development.hibernate.dao.hibernate;

import dream.development.hibernate.model.Employee;
import dream.development.hibernate.dao.interfaces.EmployeeDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Hibernate Employee DAO
 * Created by Splayd on 27.06.2017.
 */
public class HEmployeeDao implements EmployeeDao {

    private SessionFactory sessionFactory;

    public static final Logger LOGGER = LoggerFactory.getLogger(HEmployeeDao.class);

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void insert(Employee employee) {
        sessionFactory.getCurrentSession().save(employee);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Employee> getAll() {
        return sessionFactory.getCurrentSession().createQuery("SELECT e FROM Employee e ORDER BY e.id").list();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Employee getById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("SELECT e FROM Employee e WHERE e.id = :id");
        query.setParameter("id", id);
        return (Employee) query.uniqueResult();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Employee getByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("SELECT e FROM Employee e WHERE e.name LIKE :name");
        query.setParameter("name", name);
        return (Employee) query.uniqueResult();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void remove(String name) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("DELETE FROM Employee e WHERE e.name LIKE :name");
        query.setParameter("name", name);
        query.executeUpdate();
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
