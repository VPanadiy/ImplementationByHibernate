package dream.development.hibernate.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * WAREHOUSE TABLE
 * Created by Splayd on 16.05.2017.
 */

@Entity
@Table(name = "warehouse")
public class Warehouse implements Serializable {

    @Id
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    @Column(name = "amount")
    private float amount;

    @Column(name = "unit")
    private String unit;

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "ingredient=" + ingredient +
                ", amount=" + amount +
                ", unit='" + unit + '\'';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Warehouse warehouse = (Warehouse) o;

        return ingredient != null ? ingredient.equals(warehouse.ingredient) : warehouse.ingredient == null;
    }

    @Override
    public int hashCode() {
        return ingredient != null ? ingredient.hashCode() : 0;
    }
}
