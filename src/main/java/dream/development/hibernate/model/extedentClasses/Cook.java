package dream.development.hibernate.model.extedentClasses;

import dream.development.hibernate.model.DishCreated;
import dream.development.hibernate.model.Employee;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Extend Employee Table
 * Created by Splayd on 03.08.2017.
 */

@Entity
public class Cook extends Employee {

    @OneToMany
    @JoinColumn(name = "employee_id")
    @Fetch(FetchMode.JOIN)
    List<DishCreated> cookedDishes;

    public List<DishCreated> getCookedDishes() {
        return cookedDishes;
    }

    public void setCookedDishes(List<DishCreated> cookedDishes) {
        this.cookedDishes = cookedDishes;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Cook {\n");
        builder.append("    id = ").append(getId()).append("\n");
        builder.append("    surname = ").append(getSurname()).append("\n");
        builder.append("    name = ").append(getName()).append("\n");
        builder.append("    dateBirth = ").append(getDateBirth()).append("\n");
        builder.append("    phoneNumber = ").append(getPhoneNumber()).append("\n");
        builder.append("    salary = ").append(getSalary()).append("\n");
        builder.append("    orders = {\n");
        for (DishCreated cookedDish : cookedDishes) {
            builder.append("      ").append(cookedDish.getDishId()).append("\n");
        }
        builder.append("    }\n");
        builder.append("}\n");
        return builder.toString();
    }
}
