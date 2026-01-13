package org.example.ui;

import org.example.entity.Building;
import org.example.service.BuildingService;
import org.example.util.DisplayUtil;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class BuildingMenu {
    private final Scanner scanner;
    private final BuildingService buildingService;

    public BuildingMenu(Scanner scanner) {
        this.scanner = scanner;
        this.buildingService = new BuildingService();
    }

    public void showMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Building Management ---");
            System.out.println("1. Create Building");
            System.out.println("2. Update Building");
            System.out.println("3. Delete Building");
            System.out.println("4. View All Buildings");
            System.out.println("5. View Building by ID");
            System.out.println("6. View Buildings by Company");
            System.out.println("7. View Buildings by Employee");
            System.out.println("0. Back to Main Menu");

            int choice = ConsoleUI.getIntInput(scanner, "Enter your choice: ");

            switch (choice) {
                case 1:
                    createBuilding();
                    break;
                case 2:
                    updateBuilding();
                    break;
                case 3:
                    deleteBuilding();
                    break;
                case 4:
                    viewAllBuildings();
                    break;
                case 5:
                    viewBuildingById();
                    break;
                case 6:
                    viewBuildingsByCompany();
                    break;
                case 7:
                    viewBuildingsByEmployee();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void createBuilding() {
        Long companyId = (long) ConsoleUI.getIntInput(scanner, "Enter company ID: ");
        scanner.nextLine();
        System.out.print("Enter address: ");
        String address = scanner.nextLine();
        System.out.print("Enter number of floors: ");
        int floors = scanner.nextInt();
        System.out.print("Enter apartment count: ");
        int apartmentCount = scanner.nextInt();
        System.out.print("Enter built area: ");
        BigDecimal builtArea = scanner.nextBigDecimal();
        System.out.print("Enter common area: ");
        BigDecimal commonArea = scanner.nextBigDecimal();

        try {
            Building building = buildingService.createBuilding(address, floors, apartmentCount,
                    builtArea, commonArea, companyId);
            System.out.println("Building created successfully!");
            DisplayUtil.displayBuilding(building);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void updateBuilding() {
        Long id = (long) ConsoleUI.getIntInput(scanner, "Enter building ID: ");
        scanner.nextLine();
        System.out.print("Enter address: ");
        String address = scanner.nextLine();
        System.out.print("Enter number of floors: ");
        int floors = scanner.nextInt();
        System.out.print("Enter apartment count: ");
        int apartmentCount = scanner.nextInt();
        System.out.print("Enter built area: ");
        BigDecimal builtArea = scanner.nextBigDecimal();
        System.out.print("Enter common area: ");
        BigDecimal commonArea = scanner.nextBigDecimal();

        try {
            Building building = buildingService.updateBuilding(id, address, floors,
                    apartmentCount, builtArea, commonArea);
            System.out.println("Building updated successfully!");
            DisplayUtil.displayBuilding(building);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void deleteBuilding() {
        Long id = (long) ConsoleUI.getIntInput(scanner, "Enter building ID: ");
        try {
            buildingService.deleteBuilding(id);
            System.out.println("Building deleted successfully");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewAllBuildings() {
        try {
            List<Building> buildings = buildingService.getAllBuildings();
            if (buildings.isEmpty()) {
                System.out.println("No buildings found.");
            } else {
                DisplayUtil.displayBuildings(buildings);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewBuildingById() {
        Long id = (long) ConsoleUI.getIntInput(scanner, "Enter building ID: ");
        try {
            Building building = buildingService.getBuildingById(id);
            if (building != null) {
                DisplayUtil.displayBuilding(building);
            } else {
                System.out.println("Building not found.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewBuildingsByCompany() {
        Long companyId = (long) ConsoleUI.getIntInput(scanner, "Enter company ID: ");
        try {
            List<Building> buildings = buildingService.getBuildingsByCompany(companyId);
            if (buildings.isEmpty()) {
                System.out.println("No buildings found for this company.");
            } else {
                DisplayUtil.displayBuildings(buildings);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewBuildingsByEmployee() {
        Long employeeId = (long) ConsoleUI.getIntInput(scanner, "Enter employee ID: ");
        try {
            List<Building> buildings = buildingService.getBuildingsByEmployee(employeeId);
            if (buildings.isEmpty()) {
                System.out.println("No buildings found for this employee.");
            } else {
                DisplayUtil.displayBuildings(buildings);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
