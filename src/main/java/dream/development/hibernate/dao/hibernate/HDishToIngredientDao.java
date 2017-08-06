package dream.development.hibernate.dao.hibernate;

import dream.development.hibernate.dao.interfaces.DishToIngredientDao;
import dream.development.hibernate.model.DishToIngredient;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Hibernate Dish to Ingredient DAO
 * Created by Splayd on 03.08.2017.
 */
public class HDishToIngredientDao implements DishToIngredientDao {

    private SessionFactory sessionFactory;

    public static final Logger LOGGER = LoggerFactory.getLogger(HDishToIngredientDao.class);

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public DishToIngredient getIdDishToIngredient(Long dishId, Long ingredientId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("SELECT di FROM DishToIngredient di WHERE di.dish.id = :dishId AND di.ingredient.id = :ingredientId");
        query.setParameter("dishId", dishId);
        query.setParameter("ingredientId", ingredientId);
        return (DishToIngredient) query.uniqueResult();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<DishToIngredient> getById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("SELECT di FROM DishToIngredient di WHERE di.dish.id = :dishId ORDER BY di.id");
        query.setParameter("dishId", id);
        return query.list();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void update(DishToIngredient dishToIngredient) {
        sessionFactory.getCurrentSession().saveOrUpdate(dishToIngredient);
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
