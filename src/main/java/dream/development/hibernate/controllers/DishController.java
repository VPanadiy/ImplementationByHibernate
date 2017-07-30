package dream.development.hibernate.controllers;

import dream.development.hibernate.dao.interfaces.DishDao;
import dream.development.hibernate.dao.interfaces.IngredientDao;
import dream.development.hibernate.dao.interfaces.MenuDao;
import dream.development.hibernate.dao.interfaces.WarehouseDao;
import dream.development.hibernate.model.Dish;
import dream.development.hibernate.model.Ingredient;
import dream.development.hibernate.model.Menu;
import dream.development.hibernate.model.Warehouse;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.*;

/**
 * Dish Controller
 * Created by Splayd on 27.06.2017.
 */
public class DishController {

    private IngredientDao ingredientDao;
    private MenuDao menuDao;
    private DishDao dishDao;
    private WarehouseDao warehouseDao;

    @Transactional
    public void chooseAction() throws ParseException {
        System.out.println("Choose Action (insert, getAll, getById, getByName, remove): ");
        Scanner scanner = new Scanner(System.in);
        switch (scanner.nextLine()) {
            case "insert": {
                createDish();
                break;
            }
            case "getAll": {
                List<Dish> dishes = getAllDishes();

                for (Dish dish : dishes) {
                    System.out.println(dish.toString());
                }
                break;
            }
            case "getById": {
                System.out.println("Please, enter id of looking dish: ");
                System.out.println(getDishById(scanner.nextLong()));
                break;
            }
            case "getByName": {
                System.out.println("Please, enter the name of looking dish: ");
                System.out.println(getDishByName(scanner.nextLine()));
                break;
            }
            case "remove": {
                System.out.println("Please, enter the name of looking dish for removing: ");
                removeDishByName(scanner.nextLine());
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
    public void createDish() {
        Scanner scanner = new Scanner(System.in);

        Dish dish = new Dish();

        System.out.println("Enter name: ");
        dish.setName(scanner.nextLine());
        System.out.println("Enter cost: ");
        dish.setCost(scanner.nextFloat());
        System.out.println("Enter weight: ");
        dish.setWeight(scanner.nextFloat());
        scanner.nextLine();

        List<Ingredient> ingredientList = getIngredients(scanner);
        dish.setIngredients(ingredientList);

        Menu dishCategoryName = dishCategory();
        if (dishCategoryName == null) return;
        List<Menu> menusList = new ArrayList<>();
        menusList.add(dishCategoryName);
        dish.setMenu(dishCategoryName);
        dish.setMenus(menusList);

        dishDao.insert(dish);
    }

    private Menu dishCategory() {
        Scanner scanner = new Scanner(System.in);

        List<Menu> dishCategory = new ArrayList<>();

        System.out.println("Enter dish category name (type \"exit\" to break): ");
        String categoryName = scanner.nextLine();

        if (categoryName.equals("exit")) return null;

        dishCategory.add(menuDao.getByName(categoryName));
        if (dishCategory.size() == 0) {
            System.out.println("Menu with name " + categoryName + " does not exist!");
            dishCategory();
        }

        return dishCategory.get(0);
    }

    private List<Ingredient> getIngredients(Scanner scanner) {
        Set<Ingredient> allIngredients = new HashSet<>(ingredientDao.getAll());
        List<String> ingredientNameList = new ArrayList<>();
        List<Ingredient> ingredientList = new ArrayList<>();
        String ingredientName;
        int count = 1;

        for (Ingredient ingredient : allIngredients) {
            ingredientNameList.add(ingredient.toString().replace("\'",""));
        }

        do {
            System.out.println("Enter the name of " + count + " ingredient of the dish or type \"exit\" to exit: ");
            ingredientName = scanner.nextLine();

            boolean searchIngredient = false;
            for (String ingredients : ingredientNameList) {
                if (ingredients.equals(ingredientName)){
                    searchIngredient = true;
                }
            }

            if (ingredientName.equals("exit")) break;

            if (searchIngredient) {
                ingredientList.add(ingredientDao.getByName(ingredientName));
                count++;
            } else {
                System.out.println("Ingredient with such name not found. Do you want create new ingredient(y/n)?: ");
                String answerNewIngredient = scanner.nextLine();
                if (answerNewIngredient.equals("y")) {
                    Ingredient ingredient = new Ingredient();
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

                    ingredientList.add(ingredientDao.getByName(ingredientName));
                    count++;
                } else {
                    System.out.println("Ingredient not added!");
                }
            }
        } while (!ingredientName.equals("exit"));
        return ingredientList;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public List<Dish> getAllDishes() {
        return dishDao.getAll();
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public Dish getDishById(Long id) {
        return dishDao.getById(id);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public Dish getDishByName(String name) {
        return dishDao.getByName(name);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void removeDishByName(String name) {
        dishDao.remove(name);
    }

    public void setDishDao(DishDao dishDao) {
        this.dishDao = dishDao;
    }

    public void setIngredientDao(IngredientDao ingredientDao) {
        this.ingredientDao = ingredientDao;
    }

    public void setMenuDao(MenuDao menuDao) {
        this.menuDao = menuDao;
    }

    public void setWarehouseDao(WarehouseDao warehouseDao) {
        this.warehouseDao = warehouseDao;
    }
}
