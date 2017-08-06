package dream.development.hibernate.controllers;

import dream.development.hibernate.dao.interfaces.DishDao;
import dream.development.hibernate.dao.interfaces.DishToIngredientDao;
import dream.development.hibernate.dao.interfaces.IngredientDao;
import dream.development.hibernate.dao.interfaces.WarehouseDao;
import dream.development.hibernate.model.Dish;
import dream.development.hibernate.model.DishToIngredient;
import dream.development.hibernate.model.Ingredient;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Ingredient Controller
 * Created by Splayd on 01.07.2017.
 */
public class IngredientController {

    private IngredientDao ingredientDao;
    private WarehouseDao warehouseDao;
    private DishDao dishDao;
    private DishToIngredientDao dishToIngredientDao;

    @Transactional
    public void chooseAction() throws ParseException {
        System.out.println("Choose Action (insert, getAll, getById, getByName, remove, update): ");
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
                System.out.println("Please, enter the name of looking ingredient for removing: ");
                removeIngredientByName(scanner.nextLine());
                break;
            }
            case "update": {
                updateDishToIngredient();
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
    public void createIngredient() throws ParseException {
        WarehouseController warehouseController = new WarehouseController();
        warehouseController.insertInWarehouse();
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

    @Transactional(propagation = Propagation.MANDATORY)
    public void updateDishToIngredient() {
        DishToIngredient dishToIngredient = new DishToIngredient();
        List<Dish> dishList = new ArrayList<>();
        List<Ingredient> ingredientList = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please, enter the name of the looking dish: ");
        String dishName = scanner.nextLine();
        dishList.add(dishDao.getByName(dishName));
        if (dishList.get(0) == null) {
            System.out.println("Dish with such name not found!");
        } else {
            System.out.println("Please, enter the name of the looking ingredient for updating amount of using in the dish: ");
            String ingredientName = scanner.nextLine();
            ingredientList.add(ingredientDao.getByName(ingredientName));
            if (ingredientList.get(0) == null) {
                System.out.println("Ingredient with such name not found!");
            } else {
                long dishId = dishDao.getByName(dishName).getId();
                long ingredientId = ingredientDao.getByName(ingredientName).getId();
                dishToIngredient = dishToIngredientDao.getIdDishToIngredient(dishId, ingredientId);
                System.out.println("Enter amount of using ingredient " + ingredientName + " for the dish " + dishName);
                dishToIngredient.setAmount(scanner.nextFloat());
                dishToIngredientDao.update(dishToIngredient);
            }
        }
    }

    public void setIngredientDao(IngredientDao ingredientDao) {
        this.ingredientDao = ingredientDao;
    }

    public void setWarehouseDao(WarehouseDao warehouseDao) {
        this.warehouseDao = warehouseDao;
    }

    public void setDishToIngredientDao(DishToIngredientDao dishToIngredientDao) {
        this.dishToIngredientDao = dishToIngredientDao;
    }

    public void setDishDao(DishDao dishDao) {
        this.dishDao = dishDao;
    }
}
