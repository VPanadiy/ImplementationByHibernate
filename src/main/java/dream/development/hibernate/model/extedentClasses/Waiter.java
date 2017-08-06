package dream.development.hibernate.model.extedentClasses;

import dream.development.hibernate.model.Employee;
import dream.development.hibernate.model.Orders;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Extend Employee Table
 * Created by Splayd on 30.07.2017.
 */
@Entity
public class Waiter extends Employee {

    @OneToMany
    @JoinColumn(name = "employee_id")
    @Fetch(FetchMode.JOIN)
    private List<Orders> orders;

    public List<Orders> getOrders() {
        return orders;
    }

    public void setOrders(List<Orders> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Waiter {\n");
        builder.append("    id = ").append(getId()).append("\n");
        builder.append("    surname = ").append(getSurname()).append("\n");
        builder.append("    name = ").append(getName()).append("\n");
        builder.append("    dateBirth = ").append(getDateBirth()).append("\n");
        builder.append("    phoneNumber = ").append(getPhoneNumber()).append("\n");
        builder.append("    salary = ").append(getSalary()).append("\n");
        builder.append("    orders = {\n");
        orders.forEach(orders -> builder.append("      ").append(orders).append("\n"));
        builder.append("    }\n");
        builder.append("}\n");
        return builder.toString();
    }
}
