package dream.development.hibernate.dao.interfaces;

import dream.development.hibernate.model.Dish;

import java.util.List;

/**
 * Dish Interface
 * Created by Splayd on 27.06.2017.
 */
public interface DishDao {

    void insert(Dish dish);

    List<Dish> getAll();

    Dish getById(Long id);

    Dish getByName(String name);

    void remove(String name);
}
