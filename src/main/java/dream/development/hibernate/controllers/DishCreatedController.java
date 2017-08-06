package dream.development.hibernate.controllers;

import dream.development.hibernate.dao.interfaces.*;
import dream.development.hibernate.model.*;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.*;

/**
 * Dish Created Controller
 * Created by Splayd on 23.07.2017.
 */
public class DishCreatedController {

    private DishCreatedDao dishCreatedDao;
    private OrdersDao ordersDao;
    private DishOrdersDao dishOrdersDao;
    private DishDao dishDao;
    private EmployeeDao employeeDao;
    private DishToIngredientDao dishToIngredientDao;
    private WarehouseDao warehouseDao;
    private IngredientDao ingredientDao;

    @Transactional
    public void chooseAction() throws ParseException {
        System.out.println("Choose Action (insert, getAll, getAllOpened): ");
        Scanner scanner = new Scanner(System.in);
        switch (scanner.nextLine()) {
            case "insert": {
                insertCreatedDish();
                break;
            }
            case "getAll": {
                List<DishCreated> dishCreatedList = getAllCreatedDishes();

                for (DishCreated createdDishes : dishCreatedList) {
                    System.out.println(createdDishes);
                }
                break;
            }
            case "getAllOpened": {
                List<DishOrders> dishOrdersList = getAllOpenedDishes();

                for (DishOrders dishOrders : dishOrdersList) {
                    System.out.println(dishOrders);
                }
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
    public void insertCreatedDish() throws ParseException {
        DishCreated dishCreated = new DishCreated();

        List<DishOrders> getAllOpened = dishOrdersDao.getOpened();
        Map<Long, String> dishOpenedMap = new HashMap<>();
        for (DishOrders orders : getAllOpened) {
            String beginDishString = "dish=";
            String endDishString = ", status";
            int beginDishParse = orders.toString().lastIndexOf(beginDishString) + beginDishString.length();
            int endDishParse = orders.toString().indexOf(endDishString);
            String beginIdString = "id=";
            String endIdString = ", orders=";
            int beginIdParse = orders.toString().lastIndexOf(beginIdString) + beginIdString.length();
            int endIdParse = orders.toString().indexOf(endIdString);
            dishOpenedMap.put(Long.valueOf(orders.toString().substring(beginIdParse, endIdParse)), orders.toString().substring(beginDishParse, endDishParse));
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter created dish:");
        String dishName = scanner.nextLine();

        boolean dishFound = false;
        for (Map.Entry<Long, String> entry : dishOpenedMap.entrySet()) {
            if (!dishFound) {
                if (entry.getValue().equals(dishName)) {
                    dishFound = true;
                    System.out.println("Enter cooker name:");
                    String employeeName = scanner.nextLine();
                    List<Employee> cookerList = new ArrayList<>();
                    cookerList.add(employeeDao.getByName(employeeName));

                    if (cookerList.get(0) == null) {
                        System.out.println("Cooker with such name not found!");
                    } else {
                        boolean changeEnoughVale = false;
                        boolean enoughIngredient = true;
                        long dishId = dishDao.getByName(dishName).getId();
                        List<DishToIngredient> dishToIngredientList = dishToIngredientDao.getById(dishId);
                        for (DishToIngredient ingredient : dishToIngredientList) {
                            float amount = ingredient.getAmount();
                            String ingredientName = ingredientDao.getById(ingredient.getIngredient().getId()).toString().replace("\'", "");
                            Warehouse warehouse = warehouseDao.getByName(ingredientName);
                            float amountResult = warehouse.getAmount() - amount;
                            if (!changeEnoughVale) {
                                if (amountResult < 0) {
                                    enoughIngredient = false;
                                    changeEnoughVale = true;
                                }
                            }
                        }

                        if (!enoughIngredient) {
                            System.out.println("Not enough ingredients!");
                        } else {
                            dishCreated.setDishId(dishDao.getByName(dishName));
                            dishCreated.setOrderId(ordersDao.getById(dishOrdersDao.getById(entry.getKey()).getOrders().getId()));
                            dishCreated.setCook(employeeDao.getByName(employeeName));
                            dishCreatedDao.insert(dishCreated);

                            DishOrders dishOrders = dishOrdersDao.getById(entry.getKey());
                            dishOrders.setStatus(true);
                            dishOrdersDao.update(dishOrders);

                            for (DishToIngredient ingredient : dishToIngredientList) {
                                float amount = ingredient.getAmount();
                                String ingredientName = ingredientDao.getById(ingredient.getIngredient().getId()).toString().replace("\'", "");
                                Warehouse warehouse = warehouseDao.getByName(ingredientName);
                                float amountResult = warehouse.getAmount() - amount;

                                warehouse.setAmount(amountResult);
                                warehouseDao.update(warehouse);
                            }
                        }
                    }
                }
            }
        }

        if (!dishFound) {
            System.out.println("Dish with such name in orders not found!");
        }
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public List<DishCreated> getAllCreatedDishes() {
        return dishCreatedDao.getAll();
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public List<DishOrders> getAllOpenedDishes() {
        return dishOrdersDao.getOpened();
    }

    public void setDishCreatedDao(DishCreatedDao dishCreatedDao) {
        this.dishCreatedDao = dishCreatedDao;
    }

    public void setOrdersDao(OrdersDao ordersDao) {
        this.ordersDao = ordersDao;
    }

    public void setDishOrdersDao(DishOrdersDao dishOrdersDao) {
        this.dishOrdersDao = dishOrdersDao;
    }

    public void setDishDao(DishDao dishDao) {
        this.dishDao = dishDao;
    }

    public void setEmployeeDao(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    public void setDishToIngredientDao(DishToIngredientDao dishToIngredientDao) {
        this.dishToIngredientDao = dishToIngredientDao;
    }

    public void setWarehouseDao(WarehouseDao warehouseDao) {
        this.warehouseDao = warehouseDao;
    }

    public void setIngredientDao(IngredientDao ingredientDao) {
        this.ingredientDao = ingredientDao;
    }
}
