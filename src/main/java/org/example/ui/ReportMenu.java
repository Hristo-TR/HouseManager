package org.example.ui;

import org.example.entity.Apartment;
import org.example.entity.Building;
import org.example.entity.Resident;
import org.example.service.ReportService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class ReportMenu {
    private final Scanner scanner;
    private final ReportService reportService;

    public ReportMenu(Scanner scanner) {
        this.scanner = scanner;
        this.reportService = new ReportService();
    }

    public void showMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Reports ---");
            System.out.println("1. Buildings Serviced by Employee (Count)");
            System.out.println("2. Buildings Serviced by Employee (List)");
            System.out.println("3. Apartments in Building (Count)");
            System.out.println("4. Apartments in Building (List)");
            System.out.println("5. Residents in Building (Count)");
            System.out.println("6. Residents in Building (List)");
            System.out.println("7. Total Amount to Pay by Company");
            System.out.println("8. Total Amount to Pay by Building");
            System.out.println("9. Total Amount to Pay by Employee");
            System.out.println("10. Total Paid by Company");
            System.out.println("11. Total Paid by Building");
            System.out.println("12. Total Paid by Employee");
            System.out.println("0. Back to Main Menu");

            int choice = ConsoleUI.getIntInput(scanner, "Enter your choice: ");

            switch (choice) {
                case 1:
                    showBuildingCountByEmployee();
                    break;
                case 2:
                    showBuildingsByEmployee();
                    break;
                case 3:
                    showApartmentCountByBuilding();
                    break;
                case 4:
                    showApartmentsByBuilding();
                    break;
                case 5:
                    showResidentCountByBuilding();
                    break;
                case 6:
                    showResidentsByBuilding();
                    break;
                case 7:
                    showTotalAmountToPayByCompany();
                    break;
                case 8:
                    showTotalAmountToPayByBuilding();
                    break;
                case 9:
                    showTotalAmountToPayByEmployee();
                    break;
                case 10:
                    showTotalPaidByCompany();
                    break;
                case 11:
                    showTotalPaidByBuilding();
                    break;
                case 12:
                    showTotalPaidByEmployee();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void showBuildingCountByEmployee() {
        Long employeeId = (long) ConsoleUI.getIntInput(scanner, "Enter employee ID: ");
        try {
            int count = reportService.getBuildingCountByEmployee(employeeId);
            System.out.println("Number of buildings serviced: " + count);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void showBuildingsByEmployee() {
        Long employeeId = (long) ConsoleUI.getIntInput(scanner, "Enter employee ID: ");
        try {
            List<Building> buildings = reportService.getBuildingsByEmployee(employeeId);
            if (buildings.isEmpty()) {
                System.out.println("No buildings found for this employee.");
            } else {
                System.out.println("Buildings serviced by employee:");
                buildings.forEach(b -> System.out.println("  - " + b.getAddress()));
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void showApartmentCountByBuilding() {
        Long buildingId = (long) ConsoleUI.getIntInput(scanner, "Enter building ID: ");
        try {
            int count = reportService.getApartmentCountByBuilding(buildingId);
            System.out.println("Number of apartments: " + count);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void showApartmentsByBuilding() {
        Long buildingId = (long) ConsoleUI.getIntInput(scanner, "Enter building ID: ");
        try {
            List<Apartment> apartments = reportService.getApartmentsByBuilding(buildingId);
            if (apartments.isEmpty()) {
                System.out.println("No apartments found in this building.");
            } else {
                System.out.println("Apartments in building:");
                apartments.forEach(a -> System.out.println("  - Apartment " + a.getNumber() +
                        " (Floor " + a.getFloor() + ", Area: " + a.getArea() + " sqm)"));
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void showResidentCountByBuilding() {
        Long buildingId = (long) ConsoleUI.getIntInput(scanner, "Enter building ID: ");
        try {
            int count = reportService.getResidentCountByBuilding(buildingId);
            System.out.println("Number of residents: " + count);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void showResidentsByBuilding() {
        Long buildingId = (long) ConsoleUI.getIntInput(scanner, "Enter building ID: ");
        try {
            List<Resident> residents = reportService.getResidentsByBuilding(buildingId);
            if (residents.isEmpty()) {
                System.out.println("No residents found in this building.");
            } else {
                System.out.println("Residents in building:");
                residents.forEach(r -> System.out.println("  - " + r.getFullName() +
                        " (Age: " + r.getAge() + ")"));
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void showTotalAmountToPayByCompany() {
        Long companyId = (long) ConsoleUI.getIntInput(scanner, "Enter company ID: ");
        try {
            BigDecimal total = reportService.getTotalAmountToPayByCompany(companyId);
            System.out.println("Total amount to pay: " + total + " BGN");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void showTotalAmountToPayByBuilding() {
        Long buildingId = (long) ConsoleUI.getIntInput(scanner, "Enter building ID: ");
        try {
            BigDecimal total = reportService.getTotalAmountToPayByBuilding(buildingId);
            System.out.println("Total amount to pay: " + total + " BGN");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void showTotalAmountToPayByEmployee() {
        Long employeeId = (long) ConsoleUI.getIntInput(scanner, "Enter employee ID: ");
        try {
            BigDecimal total = reportService.getTotalAmountToPayByEmployee(employeeId);
            System.out.println("Total amount to pay: " + total + " BGN");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void showTotalPaidByCompany() {
        Long companyId = (long) ConsoleUI.getIntInput(scanner, "Enter company ID: ");
        try {
            BigDecimal total = reportService.getTotalPaidByCompany(companyId);
            System.out.println("Total paid: " + total + " BGN");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void showTotalPaidByBuilding() {
        Long buildingId = (long) ConsoleUI.getIntInput(scanner, "Enter building ID: ");
        try {
            BigDecimal total = reportService.getTotalPaidByBuilding(buildingId);
            System.out.println("Total paid: " + total + " BGN");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void showTotalPaidByEmployee() {
        Long employeeId = (long) ConsoleUI.getIntInput(scanner, "Enter employee ID: ");
        try {
            BigDecimal total = reportService.getTotalPaidByEmployee(employeeId);
            System.out.println("Total paid: " + total + " BGN");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
