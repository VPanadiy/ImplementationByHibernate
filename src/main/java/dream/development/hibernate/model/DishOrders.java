package dream.development.hibernate.model;

import javax.persistence.*;

/**
 * DISH ORDERS TABLE
 * Created by Splayd on 23.07.2017.
 */

@Entity
@Table(name = "dish_to_order")
public class DishOrders {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    private Orders orders;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dish_id")
    private Dish dish;

    //additional table
    @Column(name = "is_created")
    private Boolean status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "DishOrders{" +
                "id=" + id +
                ", orders=" + orders.getId() +
                ", dish=" + dish.getName() +
                ", status=" + status +
                '}';
    }
}
