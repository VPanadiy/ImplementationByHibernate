package dream.development.hibernate.dao.interfaces;

import dream.development.hibernate.model.DishToIngredient;

import java.util.List;

/**
 * Dish to Ingredient Interface
 * Created by Splayd on 03.08.2017.
 */
public interface DishToIngredientDao {

    DishToIngredient getIdDishToIngredient(Long dishId, Long ingredientId);

    List<DishToIngredient> getById(Long id);

    void update(DishToIngredient dishToIngredient);
}
