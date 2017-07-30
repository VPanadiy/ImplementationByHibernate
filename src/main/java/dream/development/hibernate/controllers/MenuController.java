package dream.development.hibernate.controllers;

import dream.development.hibernate.dao.interfaces.DishDao;
import dream.development.hibernate.dao.interfaces.MenuDao;
import dream.development.hibernate.model.Dish;
import dream.development.hibernate.model.Menu;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.*;

/**
 * Menu Controller
 * Created by Splayd on 01.07.2017.
 */
public class MenuController {

    private MenuDao menuDao;
    private DishDao dishDao;

    @Transactional
    public void chooseAction() throws ParseException {
        System.out.println("Choose Action (insert, getAll, getById, getByName, remove, update): ");
        Scanner scanner = new Scanner(System.in);
        switch (scanner.nextLine()) {
            case "insert": {
                createMenu();
                break;
            }
            case "getAll": {
                List<Menu> menus = getAllFromMenu();

                for (Menu menu : menus) {
                    System.out.println(menu);
                }
                break;
            }
            case "getById": {
                System.out.println("Please, enter id of looking menu: ");
                System.out.println(getMenuById(scanner.nextLong()));
                break;
            }
            case "getByName": {
                System.out.println("Please, enter the name of looking menu: ");
                System.out.println(getMenuByName(scanner.nextLine()));
                break;
            }
            case "remove": {
                System.out.println("Please, enter the name of looking menu for removing: ");
                removeMenuByName(scanner.nextLine());
                break;
            }
            case "update": {
                updateByName();
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
    public void createMenu() throws ParseException {
        Scanner scanner = new Scanner(System.in);

        Menu menu = new Menu();

        System.out.println("Enter the name of new menu: ");
        menu.setName(scanner.nextLine());

        boolean exit = false;
        do {
            System.out.println("Do you want to add dish into menu (y/n)");
            String answer = scanner.nextLine();
            if (answer.equals("y")) {
                List<Dish> dishesGetList = new ArrayList<>();
                List<Dish> dishList = getDishes(scanner, dishesGetList, false);
                menu.setDishes(dishList);
                exit = true;
            } else if (answer.equals("n")) {
                exit = true;
            } else {
                System.out.println("Wrong answer!");
            }

        } while (!exit);

        menuDao.insert(menu);
    }

    private List<Dish> getDishes(Scanner scanner, List<Dish> dishesGetList, boolean isRemove) {
        Set<Dish> dishesSet = new HashSet<>(dishDao.getAll());
        List<String> dishNameList = new ArrayList<>();
        List<Dish> dishList = dishesGetList;
        String dishName;
        int count = 1;

        for (Dish dish : dishesSet) {
            String beginNumString = "name='";
            String endNumString = "', ingredients=";
            int beginNumParse = dish.toString().lastIndexOf(beginNumString) + beginNumString.length();
            int endNumParse = dish.toString().indexOf(endNumString);
            dishNameList.add(dish.toString().substring(beginNumParse, endNumParse));
        }

        do {
            System.out.println("Enter the name of " + count + " dish of the menu or type \"exit\" to exit: ");
            dishName = scanner.nextLine();

            boolean searchDish = false;
            for (String dishes : dishNameList) {
                if (dishes.equals(dishName)) {
                    searchDish = true;
                }
            }

            if (dishName.equals("exit")) break;

            if (searchDish) {
                if (!isRemove) {
                    dishList.add(dishDao.getByName(dishName));
                    count++;
                } else {
                    dishList.remove(dishDao.getByName(dishName));
                    count++;
                }
            } else {
                System.out.println("Dish with such name not found!");
            }
        } while (!dishName.equals("exit"));

        return dishList;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public List<Menu> getAllFromMenu() {
        return menuDao.getAll();
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public Menu getMenuById(Long id) {
        return menuDao.getById(id);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public Menu getMenuByName(String name) {
        return menuDao.getByName(name);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void removeMenuByName(String name) {
        menuDao.remove(name);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void updateByName() {
        List<Dish> dishesList = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please, enter name of the menu which one you want to update (type \'exit\' to break): ");
        String searchMenuName = scanner.nextLine();
        Menu searchMenu = getMenuByName(searchMenuName);
        if (!searchMenuName.equals("exit")) {

            if (searchMenu == null) {
                System.out.println("Menu with such name not found!");
                updateByName();
            } else {
                dishesList = dishAddOrRemove(scanner, searchMenuName);
                Menu menu = getMenuByName(searchMenuName);
                menu.setDishes(dishesList);
                menuDao.update(menu);
            }
        }
    }

    private List<Dish> dishAddOrRemove(Scanner scanner, String menuName) {
        List<Dish> dishesGetList = new ArrayList<>(menuDao.getByName(menuName).getDishes());
        List<Dish> dishList = new ArrayList<>();
        System.out.println("Do you want add or remove dish (add/remove)?:");
        String answer = scanner.nextLine();
        if (answer.equals("add")) {
            dishList = getDishes(scanner, dishesGetList, false);
        } else if (answer.equals("remove")) {
            dishList = getDishes(scanner, dishesGetList, true);
        } else if (answer.equals("exit")) {
        } else {
            System.out.println("Wrong argument!");
            dishAddOrRemove(scanner, menuName);
        }
        return dishList;
    }

    public void setMenuDao(MenuDao menuDao) {
        this.menuDao = menuDao;
    }

    public void setDishDao(DishDao dishDao) {
        this.dishDao = dishDao;
    }
}


