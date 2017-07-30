package dream.development.hibernate.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * ORDERS TABLE
 * Created by Splayd on 27.06.2017.
 */
@Entity
@Table(name = "orders")
public class Orders {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id")
    private long id;

    @Column(name = "table_number")
    private int tableNumber;

    @Column(name = "date_order")
    private Date dateOrder;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee waiter;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "dish_to_order",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "dish_id")
    )
    private List<Dish> dishes;

    @Column(name = "is_closed")
    private boolean isClosed;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public Date getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(Date dateOrder) {
        this.dateOrder = dateOrder;
    }

    public Employee getWaiter() {
        return waiter;
    }

    public void setWaiter(Employee waiter) {
        this.waiter = waiter;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    @Override
    public String toString() {
        List<String> dishResult = new ArrayList<>();
        for (Dish dish : dishes) {
            String beginNumString = "name=";
            String endNumString = ", ingredients=";
            int beginNumParse = dish.toString().lastIndexOf(beginNumString) + beginNumString.length();
            int endNumParse = dish.toString().indexOf(endNumString);
            dishResult.add(dish.toString().substring(beginNumParse, endNumParse));
        }

        return "id=" + id +
                ", tableNumber=" + tableNumber +
                ", dateOrder=" + dateOrder +
                ", waiter=" + waiter.getName() +
                ", dishes=" + dishResult +
                ", isClosed=" + isClosed;
    }
}
