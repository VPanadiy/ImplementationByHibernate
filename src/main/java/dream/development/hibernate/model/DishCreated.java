package dream.development.hibernate.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * DISH_CREATED TABLE
 * Created by Splayd on 16.05.2017.
 */

@Entity
@Table(name = "dish_created")
public class DishCreated {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dish_id")
    private Dish dishId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    private Employee cook;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    private Orders orderId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Dish getDishId() {
        return dishId;
    }

    public void setDishId(Dish dishId) {
        this.dishId = dishId;
    }

    public Employee getCook() {
        return cook;
    }

    public void setCook(Employee cook) {
        this.cook = cook;
    }

    public Orders getOrderId() {
        return orderId;
    }

    public void setOrderId(Orders orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "id=" + id +
                ", dishId=" + dishId.getId() +
                ", cook=" + cook.getName() +
                ", tableNumber=" + orderId.getTableNumber();
    }
}
