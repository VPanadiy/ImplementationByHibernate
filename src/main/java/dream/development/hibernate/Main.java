package dream.development.hibernate;

import dream.development.hibernate.controllers.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.ParseException;
import java.util.Scanner;

/**
 * BOOT CLASS
 * Created by Splayd on 24.06.2017.
 */
public class Main {

    private EmployeeController employeeController;
    private DishController dishController;
    private OrderController orderController;
    private MenuController menuController;
    private IngredientController ingredientController;
    private WarehouseController warehouseController;
    private DishCreatedController dishCreatedController;

    public static void main(String[] args) throws ParseException {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-context.xml", "hibernate-context.xml");
        Main main = applicationContext.getBean(Main.class);
        main.choseTableForAction();
    }

    private void choseTableForAction() throws ParseException {
        System.out.println("Choose Table (employee, dish, warehouse, menu, order, kitchen): ");
        Scanner scanner = new Scanner(System.in);
        switch (scanner.nextLine()) {
            case "employee": {
                employeeController.chooseAction();
                break;
            }
            case "dish": {
                dishController.chooseAction();
                break;
            }
            case "warehouse": {
                warehouseController.chooseAction();
                break;
            }
            case "menu": {
                menuController.chooseAction();
                break;
            }
            case "order": {
                orderController.chooseAction();
                break;
            }
            case "kitchen": {
                dishCreatedController.chooseAction();
                break;
            }
            case "ingredient": {
                ingredientController.chooseAction();
                break;
            }
            default: {
                System.out.println("Wrong value!");
                choseTableForAction();
            }
            case "exit":
                return;
        }
        scanner.close();
    }

    public void setEmployeeController(EmployeeController employeeController) {
        this.employeeController = employeeController;
    }

    public void setDishController(DishController dishController) {
        this.dishController = dishController;
    }

    public void setOrderController(OrderController orderController) {
        this.orderController = orderController;
    }

    public void setMenuController(MenuController menuController) {
        this.menuController = menuController;
    }

    public void setIngredientController(IngredientController ingredientController) {
        this.ingredientController = ingredientController;
    }

    public void setWarehouseController(WarehouseController warehouseController) {
        this.warehouseController = warehouseController;
    }

    public void setDishCreatedController(DishCreatedController dishCreatedController) {
        this.dishCreatedController = dishCreatedController;
    }
}
