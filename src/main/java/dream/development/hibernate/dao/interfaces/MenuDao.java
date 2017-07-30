package dream.development.hibernate.dao.interfaces;

import dream.development.hibernate.model.Menu;

import java.util.List;

/**
 * Menu Interface
 * Created by Splayd on 01.07.2017.
 */
public interface MenuDao {

    void insert(Menu menu);

    List<Menu> getAll();

    Menu getById(Long id);

    Menu getByName(String name);

    void remove(String name);

    void update(Menu menu);
}
