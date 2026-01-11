package org.example.ui;

import org.example.entity.Employee;
import org.example.service.EmployeeService;

import java.util.List;
import java.util.Scanner;

public class EmployeeMenu {
    private final Scanner scanner;
    private final EmployeeService employeeService;

    public EmployeeMenu(Scanner scanner) {
        this.scanner = scanner;
        this.employeeService = new EmployeeService();
    }

    public void showMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Employee Management ---");
            System.out.println("1. Create Employee");
            System.out.println("2. Update Employee");
            System.out.println("3. Delete Employee");
            System.out.println("4. View All Employees");
            System.out.println("5. View Employee by ID");
            System.out.println("6. View Employees by Company");
            System.out.println("7. View Employees Sorted by Name");
            System.out.println("8. View Employees Sorted by Building Count");
            System.out.println("0. Back to Main Menu");

            int choice = ConsoleUI.getIntInput(scanner, "Enter your choice: ");

            switch (choice) {
                case 1:
                    createEmployee();
                    break;
                case 2:
                    updateEmployee();
                    break;
                case 3:
                    deleteEmployee();
                    break;
                case 4:
                    viewAllEmployees();
                    break;
                case 5:
                    viewEmployeeById();
                    break;
                case 6:
                    viewEmployeesByCompany();
                    break;
                case 7:
                    viewEmployeesSortedByName();
                    break;
                case 8:
                    viewEmployeesSortedByBuildingCount();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void createEmployee() {
        Long companyId = (long) ConsoleUI.getIntInput(scanner, "Enter company ID: ");
        scanner.nextLine();
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter phone: ");
        String phone = scanner.nextLine();

        try {
            Employee employee = employeeService.createEmployee(firstName, lastName, phone, companyId);
            System.out.println("Employee created successfully: " + employee);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void updateEmployee() {
        Long id = (long) ConsoleUI.getIntInput(scanner, "Enter employee ID: ");
        scanner.nextLine();
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter phone: ");
        String phone = scanner.nextLine();

        try {
            Employee employee = employeeService.updateEmployee(id, firstName, lastName, phone);
            System.out.println("Employee updated successfully: " + employee);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void deleteEmployee() {
        Long id = (long) ConsoleUI.getIntInput(scanner, "Enter employee ID: ");
        try {
            employeeService.deleteEmployee(id);
            System.out.println("Employee deleted successfully. Buildings redistributed.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewAllEmployees() {
        try {
            List<Employee> employees = employeeService.getAllEmployees();
            if (employees.isEmpty()) {
                System.out.println("No employees found.");
            } else {
                employees.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewEmployeeById() {
        Long id = (long) ConsoleUI.getIntInput(scanner, "Enter employee ID: ");
        try {
            Employee employee = employeeService.getEmployeeById(id);
            if (employee != null) {
                System.out.println(employee);
            } else {
                System.out.println("Employee not found.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewEmployeesByCompany() {
        Long companyId = (long) ConsoleUI.getIntInput(scanner, "Enter company ID: ");
        try {
            List<Employee> employees = employeeService.getEmployeesByCompany(companyId);
            if (employees.isEmpty()) {
                System.out.println("No employees found for this company.");
            } else {
                employees.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewEmployeesSortedByName() {
        try {
            List<Employee> employees = employeeService.getAllEmployeesSortedByName();
            if (employees.isEmpty()) {
                System.out.println("No employees found.");
            } else {
                employees.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewEmployeesSortedByBuildingCount() {
        try {
            List<Employee> employees = employeeService.getAllEmployeesSortedByBuildingCount();
            if (employees.isEmpty()) {
                System.out.println("No employees found.");
            } else {
                employees.forEach(e -> {
                    int count = e.getBuildings().size();
                    System.out.println(e + " - Buildings: " + count);
                });
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
