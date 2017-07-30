package dream.development.hibernate.dao.interfaces;

import dream.development.hibernate.model.DishCreated;
import dream.development.hibernate.model.Orders;

import java.util.List;

/**
 * Dish Created Interface
 * Created by Splayd on 23.07.2017.
 */
public interface DishCreatedDao {

    void insert(DishCreated createdDishes);

    List<DishCreated> getAll();

    void remove(Orders order);
}
