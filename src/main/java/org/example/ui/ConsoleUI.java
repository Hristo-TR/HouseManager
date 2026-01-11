package org.example.ui;

import java.util.Scanner;

public class ConsoleUI {
    private final Scanner scanner;
    private final CompanyMenu companyMenu;
    private final EmployeeMenu employeeMenu;
    private final BuildingMenu buildingMenu;
    private final ApartmentMenu apartmentMenu;
    private final ResidentMenu residentMenu;
    private final PaymentMenu paymentMenu;
    private final ReportMenu reportMenu;

    public ConsoleUI() {
        this.scanner = new Scanner(System.in);
        this.companyMenu = new CompanyMenu(scanner);
        this.employeeMenu = new EmployeeMenu(scanner);
        this.buildingMenu = new BuildingMenu(scanner);
        this.apartmentMenu = new ApartmentMenu(scanner);
        this.residentMenu = new ResidentMenu(scanner);
        this.paymentMenu = new PaymentMenu(scanner);
        this.reportMenu = new ReportMenu(scanner);
    }

    public static int getIntInput(Scanner scanner, String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next();
            System.out.print(prompt);
        }
        return scanner.nextInt();
    }

    public void start() {
        System.out.println("=== Electronic House Manager ===");
        boolean running = true;

        while (running) {
            showMainMenu();
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    companyMenu.showMenu();
                    break;
                case 2:
                    employeeMenu.showMenu();
                    break;
                case 3:
                    buildingMenu.showMenu();
                    break;
                case 4:
                    apartmentMenu.showMenu();
                    break;
                case 5:
                    residentMenu.showMenu();
                    break;
                case 6:
                    paymentMenu.showMenu();
                    break;
                case 7:
                    reportMenu.showMenu();
                    break;
                case 0:
                    running = false;
                    System.out.println("Exiting application...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
        org.example.config.HibernateConfig.shutdown();
    }

    private void showMainMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Company Management");
        System.out.println("2. Employee Management");
        System.out.println("3. Building Management");
        System.out.println("4. Apartment Management");
        System.out.println("5. Resident Management");
        System.out.println("6. Payment Management");
        System.out.println("7. Reports");
        System.out.println("0. Exit");
    }

    private int getIntInput(String prompt) {
        return getIntInput(scanner, prompt);
    }
}
