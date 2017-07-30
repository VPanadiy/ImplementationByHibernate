package dream.development.hibernate.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * MENU TABLE
 * Created by Splayd on 09.05.2017.
 */

@Entity
@Table(name = "menu")
public class Menu {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "menu_to_dishes",
            joinColumns = @JoinColumn(name = "menu_id"),
            inverseJoinColumns = @JoinColumn(name = "dish_id"))
    private List<Dish> dishes;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
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
                ", name='" + name + '\'' +
                ", dishes=" + dishResult;
    }
}
