package dream.development.hibernate.controllers;

import dream.development.hibernate.dao.interfaces.*;
import dream.development.hibernate.model.*;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;

/**
 * Order Controller
 * Created by Splayd on 28.06.2017.
 */
public class OrderController {

    private EmployeeDao employeeDao;
    private DishDao dishDao;
    private OrdersDao ordersDao;
    private DishOrdersDao dishOrdersDao;
    private DishCreatedDao dishCreatedDao;

    @Transactional
    public void chooseAction() throws ParseException {
        System.out.println("Choose Action (insert, getAll, getOpened, getClosed, getById, remove, update, setClose): ");
        Scanner scanner = new Scanner(System.in);
        switch (scanner.nextLine()) {
            case "insert": {
                createOrder();
                break;
            }
            case "getOpened": {
                List<Orders> ordersList = getAllOpened();

                for (Orders orders : ordersList) {
                    System.out.println(orders);
                }
                break;
            }
            case "getClosed": {
                List<Orders> ordersList = getAllClosed();

                for (Orders orders : ordersList) {
                    System.out.println(orders);
                }
                break;
            }
            case "getAll": {
                List<Orders> ordersList = getAllFromOrder();

                for (Orders orders : ordersList) {
                    System.out.println(orders);
                }
                break;
            }
            case "getById": {
                System.out.println("Please, enter id of looking order: ");
                System.out.println(getOrderById(scanner.nextLong()));
                break;
            }
            case "remove": {
                System.out.println("Please, enter id of the order for removing: ");
                removeOrder(scanner.nextLong());
                break;
            }
            case "update": {
                updateOrder();
                break;
            }
            case "setClose": {
                updateStatusOrder();
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
    public void createOrder() {
        Scanner scanner = new Scanner(System.in);

        Orders orders = new Orders();
        System.out.println("Please, enter table number: ");
        orders.setTableNumber(scanner.nextInt());
        scanner.nextLine();
        orders.setDateOrder(Date.valueOf(LocalDate.now()));
        Employee waiter = getEmployee(scanner);
        if (waiter == null) return;
        orders.setWaiter(waiter);
        orders.setClosed(false);

        boolean exit = false;
        do {
            System.out.println("Do you want to add dish into order (y/n)");
            String answer = scanner.nextLine();
            switch (answer) {
                case "y":
                    Long orderIdFound = ordersDao.lastId() + 1;
                    List<Dish> dishesGetList = new ArrayList<>();
                    List<Dish> dishList = getDishes(scanner, dishesGetList, false, orderIdFound);
                    orders.setDishes(dishList);
                    exit = true;
                    break;
                case "n":
                    exit = true;
                    break;
                default:
                    System.out.println("Wrong answer!");
                    break;
            }

        } while (!exit);

        ordersDao.insert(orders);
    }

    private List<Dish> getDishes(Scanner scanner, List<Dish> dishesGetList, boolean isRemove, Long orderId) {
        Set<Dish> dishesSet = new HashSet<>(dishDao.getAll());
        List<String> dishNameList = new ArrayList<>();
        String dishName;
        int count = 1;

        for (Dish dish : dishesSet) {
            String beginNameString = "name='";
            String endNameString = "', ingredients=";
            int beginNameParse = dish.toString().indexOf(beginNameString) + beginNameString.length();
            int endNameParse = dish.toString().indexOf(endNameString);
            dishNameList.add(dish.toString().substring(beginNameParse, endNameParse));
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
                    dishesGetList.add(dishDao.getByName(dishName));
                    count++;
                } else {
                    List<DishCreated> createdDishes = dishCreatedDao.getAll();

                    boolean dishFound = false;
                    for (DishCreated createdDish : createdDishes) {
                        if (createdDish.getOrderId().getId() == orderId && createdDish.getDishId().getName().equals(dishName)) {
                            dishFound = true;
                        }
                    }

                    if (dishFound) {
                        System.out.println("Can`t remove created dish!");
                    } else {
                        dishesGetList.remove(dishDao.getByName(dishName));
                        count++;
                    }
                }
            } else {
                System.out.println("Dish with such name not found!");
            }
        } while (!dishName.equals("exit"));

        return dishesGetList;
    }

    private Employee getEmployee(Scanner scanner) {
        System.out.println("Please, enter name of employee, that took order (type \'exit\' to exit): ");
        String waiterName = scanner.nextLine();
        Employee waiter = employeeDao.getByName(waiterName);

        if (waiterName.equals("exit")) return null;

        if (waiter == null) {
            System.out.println("Employee with such name not found!");
            getEmployee(scanner);
        }

        return waiter;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public List<Orders> getAllOpened() {
        return ordersDao.getOpened();
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public List<Orders> getAllClosed() {
        return ordersDao.getClosed();
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public List<Orders> getAllFromOrder() {
        return ordersDao.getAll();
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public Orders getOrderById(Long id) {
        return ordersDao.getById(id);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void removeOrder(Long id) {
        Orders orders = getOrderById(id);
        if (orders.isClosed()) {
            System.out.println("Order " + id + " is closed");
        } else {
            boolean dishFound = false;
            List<DishCreated> dishCreatedList = dishCreatedDao.getAll();
            for (DishCreated dishCreated : dishCreatedList) {
                if (Objects.equals(dishCreated.getOrderId().getId(), id)) {
                    dishFound = true;
                }
            }

            if (dishFound) {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Order contains creating dish! Do you realy want to remove this order(y/n): ");
                String answer = scanner.nextLine();
                if (answer.equals("y")) {
                    dishCreatedDao.remove(ordersDao.getById(id));
                    ordersDao.remove(id);
                } else if (answer.equals("n")) {
                    System.out.println("Remove canceled!");
                } else {
                    System.out.println("Wrong argument!");
                }
            } else {
                ordersDao.remove(id);
            }
        }
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void updateOrder() {
        List<Dish> dishesList;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please, enter id of the order which one you want to update (type \'exit\' to break): ");
        String searchOrderId = scanner.nextLine();

        if (!searchOrderId.equals("exit")) {

            Orders orders = getOrderById(Long.valueOf(searchOrderId));

            if (orders == null) {
                System.out.println("Order with such name not found!");
                updateOrder();
            } else {

                if (orders.isClosed()) {
                    System.out.println("Order " + Long.valueOf(searchOrderId) + " is closed");
                    updateOrder();
                } else {
                    dishesList = dishAddOrRemove(scanner, Long.valueOf(searchOrderId));
                    orders.setDishes(dishesList);
                    ordersDao.update(orders);
                }
            }
        }
    }

    private List<Dish> dishAddOrRemove(Scanner scanner, Long searchOrderId) {
        List<Dish> dishesGetList = new ArrayList<>(ordersDao.getById(searchOrderId).getDishes());
        List<Dish> dishList = new ArrayList<>();
        System.out.println("Do you want add or remove dish (add/remove)?:");
        String answer = scanner.nextLine();
        switch (answer) {
            case "add":
                dishList = getDishes(scanner, dishesGetList, false, searchOrderId);
                break;
            case "remove":
                dishList = getDishes(scanner, dishesGetList, true, searchOrderId);
                break;
            case "exit":
                break;
            default:
                System.out.println("Wrong argument!");
                dishAddOrRemove(scanner, searchOrderId);
                break;
        }
        return dishList;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void updateStatusOrder() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please, enter id of the order which one you want to close (type \'exit\' to break): ");
        String searchOrderId = scanner.nextLine();

        if (!searchOrderId.equals("exit")) {

            Orders orders = getOrderById(Long.valueOf(searchOrderId));

            if (orders == null) {
                System.out.println("Order with such name not found!");
                updateStatusOrder();
            } else {
                if (orders.isClosed()) {
                    System.out.println("Order " + Long.valueOf(searchOrderId) + " is already closed");
                    updateStatusOrder();
                } else {
                    boolean dishFound = DishStatus(searchOrderId);

                    if (dishFound) {
                        System.out.println("Can`t close! First of all close dish order!");
                    } else {
                        orders.setClosed(true);
                        ordersDao.update(orders);
                    }
                }
            }
        }
    }

    private boolean DishStatus(String searchOrderId) {
        List<DishOrders> getAllOpened = dishOrdersDao.getOpened();
        List<Long> dishOpenedList = new ArrayList<>();
        for (DishOrders openOrders : getAllOpened) {
            String beginOrderString = ", orders=";
            String endOrderString = ", dish=";
            int beginOrderParse = openOrders.toString().lastIndexOf(beginOrderString) + beginOrderString.length();
            int endOrderParse = openOrders.toString().indexOf(endOrderString);
            dishOpenedList.add(Long.valueOf(openOrders.toString().substring(beginOrderParse, endOrderParse)));
        }

        boolean dishFound = false;
        for (Long ordersId : dishOpenedList) {
            if (Objects.equals(ordersId, Long.valueOf(searchOrderId))) {
                dishFound = true;
            }
        }
        return dishFound;
    }

    public void setEmployeeDao(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    public void setDishDao(DishDao dishDao) {
        this.dishDao = dishDao;
    }

    public void setOrdersDao(OrdersDao ordersDao) {
        this.ordersDao = ordersDao;
    }

    public void setDishOrdersDao(DishOrdersDao dishOrdersDao) {
        this.dishOrdersDao = dishOrdersDao;
    }

    public void setDishCreatedDao(DishCreatedDao dishCreatedDao) {
        this.dishCreatedDao = dishCreatedDao;
    }
}
