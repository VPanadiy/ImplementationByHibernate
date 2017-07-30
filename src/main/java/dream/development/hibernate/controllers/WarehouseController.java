package dream.development.hibernate.controllers;

import dream.development.hibernate.dao.interfaces.IngredientDao;
import dream.development.hibernate.dao.interfaces.WarehouseDao;
import dream.development.hibernate.model.Ingredient;
import dream.development.hibernate.model.Warehouse;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.List;
import java.util.Scanner;

/**
 * Warehouse Controller
 * Created by Splayd on 01.07.2017.
 */
public class WarehouseController {

    private WarehouseDao warehouseDao;
    private IngredientDao ingredientDao;

    @Transactional
    public void chooseAction() throws ParseException {
        System.out.println("Choose Action (insert, getAll, getByName, getLess, remove, update): ");
        Scanner scanner = new Scanner(System.in);
        switch (scanner.nextLine()) {
            case "insert": {
                insertInWarehouse();
                break;
            }
            case "getAll": {
                List<Warehouse> warehouseIngredientList = getAllFromWarehouse();

                for (Warehouse warehouseIngredients : warehouseIngredientList) {
                    System.out.println(warehouseIngredients);
                }
                break;
            }
            case "getByName": {
                System.out.println("Please, enter the name of looking ingredient in the warehouse: ");
                System.out.println(getFromWarehouseByName(scanner.nextLine()));
                break;
            }
            case "getLess": {
                List<Warehouse> warehouseIngredientList = getAllFromWarehouseWithConditionLess();

                for (Warehouse warehouseIngredients : warehouseIngredientList) {
                    System.out.println(warehouseIngredients);
                }
                break;
            }
            case "remove": {
                System.out.println("Please, enter the name of ingredient for removing from warehouse: ");
                removeFromWarehouseByName(scanner.nextLine());
                break;
            }
            case "update": {
                System.out.println("Please, enter the name of ingredient in the warehouse which one values should be update: ");
                updateWarehouseIngrediets(scanner.nextLine());
                break;
            }
            default: {
                System.out.println("Wrong value!");
                chooseAction();
            }
            case "exit":
                return;
        }
        scanner.close();
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void insertInWarehouse() throws ParseException {
        Scanner scanner = new Scanner(System.in);

        Ingredient ingredient = new Ingredient();
        System.out.println("Enter the name of new ingredient: ");
        String ingredientName = scanner.nextLine();
        ingredient.setName(ingredientName);

        ingredientDao.insert(ingredient);

        Warehouse warehouse = new Warehouse();
        warehouse.setIngredient(ingredientDao.getByName(ingredientName));
        System.out.println("Enter amount of ingredient");
        warehouse.setAmount(scanner.nextFloat());
        scanner.nextLine();
        System.out.println("Enter unit of ingredient");
        warehouse.setUnit(scanner.nextLine());

        warehouseDao.insert(warehouse);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public List<Warehouse> getAllFromWarehouse() {
        return warehouseDao.getAll();
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public Warehouse getFromWarehouseByName(String name) {
        return warehouseDao.getByName(name);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public List<Warehouse> getAllFromWarehouseWithConditionLess() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter amount condition: ");
        float amountCondition = scanner.nextFloat();
        scanner.close();
        return warehouseDao.getIngredientWithConditionLess(amountCondition);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void removeFromWarehouseByName(String name) {
        warehouseDao.remove(name);
        ingredientDao.remove(name);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void updateWarehouseIngrediets(String name) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter amount of ingredient " + name + ": ");
        float ingredientAmount = scanner.nextFloat();
        Warehouse warehouse = getFromWarehouseByName(name);
        warehouse.setAmount(ingredientAmount);
        scanner.close();
        warehouseDao.update(warehouse);
    }

    public void setWarehouseDao(WarehouseDao warehouseDao) {
        this.warehouseDao = warehouseDao;
    }

    public void setIngredientDao(IngredientDao ingredientDao) {
        this.ingredientDao = ingredientDao;
    }
}
