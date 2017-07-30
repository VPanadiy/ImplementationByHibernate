package dream.development.hibernate.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

/**
 * DISH TABLE
 * Created by Splayd on 27.06.2017.
 */

@Entity
@Table(name = "dish")
public class Dish {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "dish_to_ingredient",
            joinColumns = @JoinColumn(name = "dish_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private List<Ingredient> ingredients;

    @Column(name = "cost")
    private float cost;

    @Column(name = "weight")
    private float weight;

    @OneToOne
    @JoinColumn(name = "category_id")
    private Menu menu;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "menu_to_dishes",
            joinColumns = @JoinColumn(name = "dish_id"),
            inverseJoinColumns = @JoinColumn(name = "menu_id")
    )
    private List<Menu> menus;

    @OneToMany(mappedBy="orders")
    private List<DishOrders> dishOrders;

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

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    public List<DishOrders> getDishOrders() {
        return dishOrders;
    }

    public void setDishOrders(List<DishOrders> dishOrders) {
        this.dishOrders = dishOrders;
    }

    @Override
    public String toString() {
        return "id=" + id +
                ", name='" + name + '\'' +
                ", ingredients=" + ingredients +
                ", cost=" + cost +
                "$, weight=" + weight +
                " grams, menu=\'" + menu.getName() + "\'";
    }
}
