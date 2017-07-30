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
 * Ingredient Controller
 * Created by Splayd on 01.07.2017.
 */
public class IngredientController {

    private IngredientDao ingredientDao;
    private WarehouseDao warehouseDao;

    @Transactional
    public void chooseAction() throws ParseException {
        System.out.println("Choose Action (insert, getAll, getById, getByName, remove): ");
        Scanner scanner = new Scanner(System.in);
        switch (scanner.nextLine()) {
            case "insert": {
                createIngredient();
                break;
            }
            case "getAll": {
                List<Ingredient> ingredients = getAllIngredients();

                for (Ingredient ingredient : ingredients) {
                    System.out.println(ingredient);
                }
                break;
            }
            case "getById": {
                System.out.println("Please, enter id of looking ingredient: ");
                System.out.println(getIngredientById(scanner.nextLong()));
                break;
            }
            case "getByName": {
                System.out.println("Please, enter the name of looking ingredient: ");
                System.out.println(getIngredientByName(scanner.nextLine()));
                break;
            }
            case "remove": {
                System.out.println("Please, enter the name of looking dish for removing: ");
                removeIngredientByName(scanner.nextLine());
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
    public void createIngredient() {
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
    public List<Ingredient> getAllIngredients() {
        return ingredientDao.getAll();
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public Ingredient getIngredientById(Long id) {
        return ingredientDao.getById(id);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public Ingredient getIngredientByName(String name) {
        return ingredientDao.getByName(name);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void removeIngredientByName(String name) {
        warehouseDao.remove(name);
        ingredientDao.remove(name);
    }

    public void setIngredientDao(IngredientDao ingredientDao) {
        this.ingredientDao = ingredientDao;
    }

    public void setWarehouseDao(WarehouseDao warehouseDao) {
        this.warehouseDao = warehouseDao;
    }
}
