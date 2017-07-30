package dream.development.hibernate.dao.interfaces;

import dream.development.hibernate.model.Ingredient;

import java.util.List;

/**
 * Ingredient Interface
 * Created by Splayd on 01.07.2017.
 */
public interface IngredientDao {

    void insert(Ingredient ingredient);

    List<Ingredient> getAll();

    Ingredient getById(Long id);

    Ingredient getByName(String name);

    void remove(String name);
}
