package org.example.ui;

import org.example.entity.Apartment;
import org.example.entity.Owner;
import org.example.service.ApartmentService;
import org.example.service.OwnerService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class ApartmentMenu {
    private final Scanner scanner;
    private final ApartmentService apartmentService;
    private final OwnerService ownerService;

    public ApartmentMenu(Scanner scanner) {
        this.scanner = scanner;
        this.apartmentService = new ApartmentService();
        this.ownerService = new OwnerService();
    }

    public void showMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Apartment Management ---");
            System.out.println("1. Create Apartment");
            System.out.println("2. Create Owner");
            System.out.println("3. Update Apartment");
            System.out.println("4. Update Owner");
            System.out.println("5. Delete Apartment");
            System.out.println("6. Delete Owner");
            System.out.println("7. View All Apartments");
            System.out.println("8. View All Owners");
            System.out.println("9. View Apartment by ID");
            System.out.println("10. View Owner by ID");
            System.out.println("11. View Apartments by Building");
            System.out.println("0. Back to Main Menu");

            int choice = ConsoleUI.getIntInput(scanner, "Enter your choice: ");

            switch (choice) {
                case 1:
                    createApartment();
                    break;
                case 2:
                    createOwner();
                    break;
                case 3:
                    updateApartment();
                    break;
                case 4:
                    updateOwner();
                    break;
                case 5:
                    deleteApartment();
                    break;
                case 6:
                    deleteOwner();
                    break;
                case 7:
                    viewAllApartments();
                    break;
                case 8:
                    viewAllOwners();
                    break;
                case 9:
                    viewApartmentById();
                    break;
                case 10:
                    viewOwnerById();
                    break;
                case 11:
                    viewApartmentsByBuilding();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void createOwner() {
        scanner.nextLine();
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter phone: ");
        String phone = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        try {
            Owner owner = ownerService.createOwner(firstName, lastName, phone, email);
            System.out.println("Owner created successfully: " + owner);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void createApartment() {
        Long buildingId = (long) ConsoleUI.getIntInput(scanner, "Enter building ID: ");
        Long ownerId = (long) ConsoleUI.getIntInput(scanner, "Enter owner ID: ");
        scanner.nextLine();
        System.out.print("Enter apartment number: ");
        String number = scanner.nextLine();
        System.out.print("Enter floor: ");
        int floor = scanner.nextInt();
        System.out.print("Enter area: ");
        BigDecimal area = scanner.nextBigDecimal();

        try {
            Apartment apartment = apartmentService.createApartment(number, floor, area, buildingId, ownerId);
            System.out.println("Apartment created successfully: " + apartment);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void updateApartment() {
        Long id = (long) ConsoleUI.getIntInput(scanner, "Enter apartment ID: ");
        scanner.nextLine();
        System.out.print("Enter apartment number: ");
        String number = scanner.nextLine();
        System.out.print("Enter floor: ");
        int floor = scanner.nextInt();
        System.out.print("Enter area: ");
        BigDecimal area = scanner.nextBigDecimal();

        try {
            Apartment apartment = apartmentService.updateApartment(id, number, floor, area);
            System.out.println("Apartment updated successfully: " + apartment);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void updateOwner() {
        Long id = (long) ConsoleUI.getIntInput(scanner, "Enter owner ID: ");
        scanner.nextLine();
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter phone: ");
        String phone = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        try {
            Owner owner = ownerService.updateOwner(id, firstName, lastName, phone, email);
            System.out.println("Owner updated successfully: " + owner);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void deleteApartment() {
        Long id = (long) ConsoleUI.getIntInput(scanner, "Enter apartment ID: ");
        try {
            apartmentService.deleteApartment(id);
            System.out.println("Apartment deleted successfully");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void deleteOwner() {
        Long id = (long) ConsoleUI.getIntInput(scanner, "Enter owner ID: ");
        try {
            ownerService.deleteOwner(id);
            System.out.println("Owner deleted successfully");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewAllApartments() {
        try {
            List<Apartment> apartments = apartmentService.getAllApartments();
            if (apartments.isEmpty()) {
                System.out.println("No apartments found.");
            } else {
                apartments.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewAllOwners() {
        try {
            List<Owner> owners = ownerService.getAllOwners();
            if (owners.isEmpty()) {
                System.out.println("No owners found.");
            } else {
                owners.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewApartmentById() {
        Long id = (long) ConsoleUI.getIntInput(scanner, "Enter apartment ID: ");
        try {
            Apartment apartment = apartmentService.getApartmentById(id);
            if (apartment != null) {
                System.out.println(apartment);
            } else {
                System.out.println("Apartment not found.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewOwnerById() {
        Long id = (long) ConsoleUI.getIntInput(scanner, "Enter owner ID: ");
        try {
            Owner owner = ownerService.getOwnerById(id);
            if (owner != null) {
                System.out.println(owner);
            } else {
                System.out.println("Owner not found.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewApartmentsByBuilding() {
        Long buildingId = (long) ConsoleUI.getIntInput(scanner, "Enter building ID: ");
        try {
            List<Apartment> apartments = apartmentService.getApartmentsByBuilding(buildingId);
            if (apartments.isEmpty()) {
                System.out.println("No apartments found for this building.");
            } else {
                apartments.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
