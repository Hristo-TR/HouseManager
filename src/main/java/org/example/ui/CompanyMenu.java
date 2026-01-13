package org.example.ui;

import org.example.entity.Company;
import org.example.service.CompanyService;
import org.example.util.DisplayUtil;

import java.util.List;
import java.util.Scanner;

public class CompanyMenu {
    private final Scanner scanner;
    private final CompanyService companyService;

    public CompanyMenu(Scanner scanner) {
        this.scanner = scanner;
        this.companyService = new CompanyService();
    }

    public void showMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Company Management ---");
            System.out.println("1. Create Company");
            System.out.println("2. Update Company");
            System.out.println("3. Delete Company");
            System.out.println("4. View All Companies");
            System.out.println("5. View Company by ID");
            System.out.println("6. View Companies Sorted by Revenue");
            System.out.println("0. Back to Main Menu");

            int choice = ConsoleUI.getIntInput(scanner, "Enter your choice: ");

            switch (choice) {
                case 1:
                    createCompany();
                    break;
                case 2:
                    updateCompany();
                    break;
                case 3:
                    deleteCompany();
                    break;
                case 4:
                    viewAllCompanies();
                    break;
                case 5:
                    viewCompanyById();
                    break;
                case 6:
                    viewCompaniesSortedByRevenue();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void createCompany() {
        scanner.nextLine();
        System.out.print("Enter company name: ");
        String name = scanner.nextLine();
        System.out.print("Enter address: ");
        String address = scanner.nextLine();
        System.out.print("Enter phone: ");
        String phone = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        try {
            Company company = companyService.createCompany(name, address, phone, email);
            System.out.println("Company created successfully!");
            DisplayUtil.displayCompany(company);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void updateCompany() {
        Long id = (long) ConsoleUI.getIntInput(scanner, "Enter company ID: ");
        scanner.nextLine();
        System.out.print("Enter company name: ");
        String name = scanner.nextLine();
        System.out.print("Enter address: ");
        String address = scanner.nextLine();
        System.out.print("Enter phone: ");
        String phone = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        try {
            Company company = companyService.updateCompany(id, name, address, phone, email);
            System.out.println("Company updated successfully!");
            DisplayUtil.displayCompany(company);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void deleteCompany() {
        Long id = (long) ConsoleUI.getIntInput(scanner, "Enter company ID: ");
        try {
            companyService.deleteCompany(id);
            System.out.println("Company deleted successfully");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewAllCompanies() {
        try {
            List<Company> companies = companyService.getAllCompanies();
            DisplayUtil.displayCompanies(companies);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewCompanyById() {
        Long id = (long) ConsoleUI.getIntInput(scanner, "Enter company ID: ");
        try {
            Company company = companyService.getCompanyById(id);
            if (company != null) {
                DisplayUtil.displayCompany(company);
            } else {
                System.out.println("Company not found.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewCompaniesSortedByRevenue() {
        try {
            List<Company> companies = companyService.getAllCompaniesSortedByRevenue();
            if (companies.isEmpty()) {
                System.out.println("No companies found.");
            } else {
                System.out.println("\n=== Companies Sorted by Revenue ===");
                System.out.println(String.format("%-5s | %-30s | %-15s", "ID", "Name", "Revenue"));
                System.out.println("--------------------------------------------------------");
                org.example.repository.CompanyRepository repo = new org.example.repository.CompanyRepository();
                for (Company c : companies) {
                    System.out.println(String.format("%-5s | %-30s | %-15s BGN",
                            c.getId(),
                            c.getName(),
                            repo.getTotalRevenue(c.getId())));
                }
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
