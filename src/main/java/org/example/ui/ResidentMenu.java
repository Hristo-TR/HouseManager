package org.example.ui;

import org.example.entity.Resident;
import org.example.service.ResidentService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ResidentMenu {
    private final Scanner scanner;
    private final ResidentService residentService;

    public ResidentMenu(Scanner scanner) {
        this.scanner = scanner;
        this.residentService = new ResidentService();
    }

    public void showMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Resident Management ---");
            System.out.println("1. Create Resident");
            System.out.println("2. Update Resident");
            System.out.println("3. Delete Resident");
            System.out.println("4. View All Residents");
            System.out.println("5. View Resident by ID");
            System.out.println("6. View Residents by Apartment");
            System.out.println("7. View Residents by Building");
            System.out.println("8. View Residents Sorted by Name");
            System.out.println("9. View Residents Sorted by Age");
            System.out.println("0. Back to Main Menu");

            int choice = ConsoleUI.getIntInput(scanner, "Enter your choice: ");

            switch (choice) {
                case 1:
                    createResident();
                    break;
                case 2:
                    updateResident();
                    break;
                case 3:
                    deleteResident();
                    break;
                case 4:
                    viewAllResidents();
                    break;
                case 5:
                    viewResidentById();
                    break;
                case 6:
                    viewResidentsByApartment();
                    break;
                case 7:
                    viewResidentsByBuilding();
                    break;
                case 8:
                    viewResidentsSortedByName();
                    break;
                case 9:
                    viewResidentsSortedByAge();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void createResident() {
        Long apartmentId = (long) ConsoleUI.getIntInput(scanner, "Enter apartment ID: ");
        scanner.nextLine();
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter birth date (YYYY-MM-DD): ");
        String dateStr = scanner.nextLine();
        LocalDate birthDate = LocalDate.parse(dateStr);
        System.out.print("Uses elevator? (true/false): ");
        Boolean usesElevator = scanner.nextBoolean();

        try {
            Resident resident = residentService.createResident(firstName, lastName, birthDate,
                    usesElevator, apartmentId);
            System.out.println("Resident created successfully: " + resident);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void updateResident() {
        Long id = (long) ConsoleUI.getIntInput(scanner, "Enter resident ID: ");
        scanner.nextLine();
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter birth date (YYYY-MM-DD): ");
        String dateStr = scanner.nextLine();
        LocalDate birthDate = LocalDate.parse(dateStr);
        System.out.print("Uses elevator? (true/false): ");
        Boolean usesElevator = scanner.nextBoolean();

        try {
            Resident resident = residentService.updateResident(id, firstName, lastName,
                    birthDate, usesElevator);
            System.out.println("Resident updated successfully: " + resident);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void deleteResident() {
        Long id = (long) ConsoleUI.getIntInput(scanner, "Enter resident ID: ");
        try {
            residentService.deleteResident(id);
            System.out.println("Resident deleted successfully");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewAllResidents() {
        try {
            List<Resident> residents = residentService.getAllResidents();
            if (residents.isEmpty()) {
                System.out.println("No residents found.");
            } else {
                residents.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewResidentById() {
        Long id = (long) ConsoleUI.getIntInput(scanner, "Enter resident ID: ");
        try {
            Resident resident = residentService.getResidentById(id);
            if (resident != null) {
                System.out.println(resident);
            } else {
                System.out.println("Resident not found.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewResidentsByApartment() {
        Long apartmentId = (long) ConsoleUI.getIntInput(scanner, "Enter apartment ID: ");
        try {
            List<Resident> residents = residentService.getResidentsByApartment(apartmentId);
            if (residents.isEmpty()) {
                System.out.println("No residents found for this apartment.");
            } else {
                residents.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewResidentsByBuilding() {
        Long buildingId = (long) ConsoleUI.getIntInput(scanner, "Enter building ID: ");
        try {
            List<Resident> residents = residentService.getResidentsByBuilding(buildingId);
            if (residents.isEmpty()) {
                System.out.println("No residents found for this building.");
            } else {
                residents.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewResidentsSortedByName() {
        try {
            List<Resident> residents = residentService.getAllResidentsSortedByName();
            if (residents.isEmpty()) {
                System.out.println("No residents found.");
            } else {
                residents.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewResidentsSortedByAge() {
        try {
            List<Resident> residents = residentService.getAllResidentsSortedByAge();
            if (residents.isEmpty()) {
                System.out.println("No residents found.");
            } else {
                residents.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
