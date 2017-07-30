package dream.development.hibernate.dao.interfaces;

import dream.development.hibernate.model.Orders;

import java.util.List;

/**
 * Order Interface
 * Created by Splayd on 27.06.2017.
 */
public interface OrdersDao {

    void insert(Orders orders);

    List<Orders> getAll();

    List<Orders> getOpened();

    List<Orders> getClosed();

    Orders getById(Long id);

    void remove(Long id);

    void update(Orders order);

    Long lastId();
}
