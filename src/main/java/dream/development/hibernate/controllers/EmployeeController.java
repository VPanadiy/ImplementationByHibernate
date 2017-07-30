package dream.development.hibernate.controllers;

import dream.development.hibernate.model.Employee;
import dream.development.hibernate.dao.interfaces.EmployeeDao;
import dream.development.hibernate.model.enums.Position;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * Employee Controller
 * Created by Splayd on 27.06.2017.
 */
public class EmployeeController {

    private EmployeeDao employeeDao;

    @Transactional
    public void chooseAction() throws ParseException {
        System.out.println("Choose Action (insert, getAll, getById, getByName, remove): ");
        Scanner scanner = new Scanner(System.in);
        switch (scanner.nextLine()) {
            case "insert": {
                createEmployee();
                break;
            }
            case "getAll": {
                List<Employee> employees = getAllEmployees();

                for (Employee employee : employees) {
                    System.out.println(employee);
                }
                break;
            }
            case "getById": {
                System.out.println("Please, enter id of looking employee: ");
                System.out.println(getEmployeeById(scanner.nextLong()));
                break;
            }
            case "getByName": {
                System.out.println("Please, enter the name of looking employee: ");
                System.out.println(getEmployeeByName(scanner.nextLine()));
                break;
            }
            case "remove": {
                System.out.println("[WARNING]: This will remove all employees with specify name");
                System.out.println("Please, enter the name of employees for removing: ");
                removeEmployeeByName(scanner.nextLine());
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
    public void createEmployee() throws ParseException {
        Scanner scanner = new Scanner(System.in);

        Employee employee = new Employee();

        System.out.println("Enter surname: ");
        employee.setSurname(scanner.nextLine());
        System.out.println("Enter name: ");
        employee.setName(scanner.nextLine());
        System.out.println("Enter date birth (date format: yyyy-MM-dd): ");
        String date_birth = scanner.next();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormat.parse(date_birth);
        Instant instant = date.toInstant();
        employee.setDateBirth(instant.atZone(ZoneId.systemDefault()).toLocalDate());
        scanner.nextLine();
        System.out.println("Enter phone number (eg. +38(044)777-77-77): ");
        employee.setPhoneNumber(scanner.nextLine());
        System.out.println("Enter appointment: ");
        Position appointment = Position.valueOf(scanner.nextLine().toUpperCase());
        employee.setPosition(appointment);
        System.out.println("Enter salary: ");
        employee.setSalary(scanner.nextFloat());
        scanner.close();

        employeeDao.insert(employee);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public List<Employee> getAllEmployees() {
        return employeeDao.getAll();
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public Employee getEmployeeById(Long id) {
        return employeeDao.getById(id);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public Employee getEmployeeByName(String name) {
        return employeeDao.getByName(name);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void removeEmployeeByName(String name) {
        employeeDao.remove(name);
    }

    public void setEmployeeDao(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }
}
