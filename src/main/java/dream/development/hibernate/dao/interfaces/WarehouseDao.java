package dream.development.hibernate.dao.interfaces;

import dream.development.hibernate.model.Warehouse;

import java.util.List;

/**
 * Warehouse Interface
 * Created by Splayd on 01.07.2017.
 */
public interface WarehouseDao {

    void insert(Warehouse warehouse);

    List<Warehouse> getAll();

    Warehouse getByName(String name);

    List<Warehouse> getIngredientWithConditionLess(Float amount);

    void remove(String name);

    void update(Warehouse warehouse);
}
