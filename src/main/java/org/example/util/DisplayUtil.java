package org.example.util;

import org.example.entity.*;

import java.util.List;

public class DisplayUtil {

    public static void displayCompany(Company company) {
        System.out.println("\n=== Company ===");
        System.out.println("ID: " + company.getId());
        System.out.println("Name: " + company.getName());
        System.out.println("Address: " + company.getAddress());
        System.out.println("Phone: " + company.getPhone());
        System.out.println("Email: " + company.getEmail());
        System.out.println("================\n");
    }

    public static void displayCompanies(List<Company> companies) {
        if (companies.isEmpty()) {
            System.out.println("No companies found.");
            return;
        }
        System.out.println("\n=== Companies ===");
        System.out.println(String.format("%-5s | %-30s | %-30s | %-15s | %-30s", "ID", "Name", "Address", "Phone", "Email"));
        System.out.println("----------------------------------------------------------------------------------------------------------------");
        for (Company c : companies) {
            System.out.println(String.format("%-5s | %-30s | %-30s | %-15s | %-30s",
                    c.getId(),
                    truncate(c.getName(), 30),
                    truncate(c.getAddress(), 30),
                    c.getPhone(),
                    truncate(c.getEmail(), 30)));
        }
        System.out.println();
    }

    public static void displayEmployee(Employee employee) {
        System.out.println("\n=== Employee ===");
        System.out.println("ID: " + employee.getId());
        System.out.println("Name: " + employee.getFullName());
        System.out.println("Phone: " + employee.getPhone());
        System.out.println("Company: " + (employee.getCompany() != null ? employee.getCompany().getName() : "N/A"));
        System.out.println("================\n");
    }

    public static void displayEmployees(List<Employee> employees) {
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }
        System.out.println("\n=== Employees ===");
        System.out.println(String.format("%-5s | %-30s | %-15s | %-30s", "ID", "Name", "Phone", "Company"));
        System.out.println("--------------------------------------------------------------------------------");
        for (Employee e : employees) {
            System.out.println(String.format("%-5s | %-30s | %-15s | %-30s",
                    e.getId(),
                    e.getFullName(),
                    e.getPhone(),
                    e.getCompany() != null ? truncate(e.getCompany().getName(), 30) : "N/A"));
        }
        System.out.println();
    }

    public static void displayBuilding(Building building) {
        System.out.println("\n=== Building ===");
        System.out.println("ID: " + building.getId());
        System.out.println("Address: " + building.getAddress());
        System.out.println("Floors: " + building.getFloors());
        System.out.println("Apartments: " + building.getApartmentCount());
        System.out.println("Built Area: " + building.getBuiltArea() + " sqm");
        System.out.println("Common Area: " + building.getCommonArea() + " sqm");
        System.out.println("Company: " + (building.getCompany() != null ? building.getCompany().getName() : "N/A"));
        System.out.println("Employee: " + (building.getEmployee() != null ? building.getEmployee().getFullName() : "N/A"));
        System.out.println("================\n");
    }

    public static void displayBuildings(List<Building> buildings) {
        if (buildings.isEmpty()) {
            System.out.println("No buildings found.");
            return;
        }
        System.out.println("\n=== Buildings ===");
        System.out.println(String.format("%-5s | %-40s | %-6s | %-10s | %-12s | %-12s", "ID", "Address", "Floors", "Apt Count", "Built Area", "Common Area"));
        System.out.println("------------------------------------------------------------------------------------------------------------------------");
        for (Building b : buildings) {
            System.out.println(String.format("%-5s | %-40s | %-6s | %-10s | %-12s | %-12s",
                    b.getId(),
                    truncate(b.getAddress(), 40),
                    b.getFloors(),
                    b.getApartmentCount(),
                    b.getBuiltArea() + " sqm",
                    b.getCommonArea() + " sqm"));
        }
        System.out.println();
    }

    public static void displayApartment(Apartment apartment) {
        System.out.println("\n=== Apartment ===");
        System.out.println("ID: " + apartment.getId());
        System.out.println("Number: " + apartment.getNumber());
        System.out.println("Floor: " + apartment.getFloor());
        System.out.println("Area: " + apartment.getArea() + " sqm");
        System.out.println("Building: " + (apartment.getBuilding() != null ? apartment.getBuilding().getAddress() : "N/A"));
        System.out.println("Owner: " + (apartment.getOwner() != null ? apartment.getOwner().getFullName() : "N/A"));
        System.out.println("================\n");
    }

    public static void displayApartments(List<Apartment> apartments) {
        if (apartments.isEmpty()) {
            System.out.println("No apartments found.");
            return;
        }
        System.out.println("\n=== Apartments ===");
        System.out.println(String.format("%-5s | %-10s | %-6s | %-12s | %-40s | %-30s", "ID", "Number", "Floor", "Area", "Building", "Owner"));
        System.out.println("------------------------------------------------------------------------------------------------------------------------");
        for (Apartment a : apartments) {
            System.out.println(String.format("%-5s | %-10s | %-6s | %-12s | %-40s | %-30s",
                    a.getId(),
                    a.getNumber(),
                    a.getFloor(),
                    a.getArea() + " sqm",
                    a.getBuilding() != null ? truncate(a.getBuilding().getAddress(), 40) : "N/A",
                    a.getOwner() != null ? truncate(a.getOwner().getFullName(), 30) : "N/A"));
        }
        System.out.println();
    }

    public static void displayOwner(Owner owner) {
        System.out.println("\n=== Owner ===");
        System.out.println("ID: " + owner.getId());
        System.out.println("Name: " + owner.getFullName());
        System.out.println("Phone: " + owner.getPhone());
        System.out.println("Email: " + owner.getEmail());
        System.out.println("================\n");
    }

    public static void displayOwners(List<Owner> owners) {
        if (owners.isEmpty()) {
            System.out.println("No owners found.");
            return;
        }
        System.out.println("\n=== Owners ===");
        System.out.println(String.format("%-5s | %-30s | %-15s | %-30s", "ID", "Name", "Phone", "Email"));
        System.out.println("--------------------------------------------------------------------------------");
        for (Owner o : owners) {
            System.out.println(String.format("%-5s | %-30s | %-15s | %-30s",
                    o.getId(),
                    o.getFullName(),
                    o.getPhone(),
                    truncate(o.getEmail(), 30)));
        }
        System.out.println();
    }

    public static void displayResident(Resident resident) {
        System.out.println("\n=== Resident ===");
        System.out.println("ID: " + resident.getId());
        System.out.println("Name: " + resident.getFullName());
        System.out.println("Age: " + resident.getAge());
        System.out.println("Birth Date: " + resident.getBirthDate());
        System.out.println("Uses Elevator: " + resident.getUsesElevator());
        System.out.println("Apartment: " + (resident.getApartment() != null ? resident.getApartment().getNumber() : "N/A"));
        System.out.println("================\n");
    }

    public static void displayResidents(List<Resident> residents) {
        if (residents.isEmpty()) {
            System.out.println("No residents found.");
            return;
        }
        System.out.println("\n=== Residents ===");
        System.out.println(String.format("%-5s | %-30s | %-5s | %-12s | %-5s | %-10s", "ID", "Name", "Age", "Birth Date", "Elevator", "Apartment"));
        System.out.println("------------------------------------------------------------------------------------------------");
        for (Resident r : residents) {
            System.out.println(String.format("%-5s | %-30s | %-5s | %-12s | %-5s | %-10s",
                    r.getId(),
                    r.getFullName(),
                    r.getAge(),
                    r.getBirthDate(),
                    r.getUsesElevator() ? "Yes" : "No",
                    r.getApartment() != null ? r.getApartment().getNumber() : "N/A"));
        }
        System.out.println();
    }

    public static void displayPayment(Payment payment) {
        System.out.println("\n=== Payment ===");
        System.out.println("ID: " + payment.getId());
        System.out.println("Amount: " + payment.getAmount() + " BGN");
        System.out.println("Date: " + payment.getPaymentDate());
        System.out.println("Month: " + payment.getMonth());
        System.out.println("Year: " + payment.getYear());
        System.out.println("Apartment: " + (payment.getApartment() != null ? payment.getApartment().getNumber() : "N/A"));
        System.out.println("================\n");
    }

    public static void displayPayments(List<Payment> payments) {
        if (payments.isEmpty()) {
            System.out.println("No payments found.");
            return;
        }
        System.out.println("\n=== Payments ===");
        System.out.println(String.format("%-5s | %-15s | %-12s | %-6s | %-6s | %-10s", "ID", "Amount", "Date", "Month", "Year", "Apartment"));
        System.out.println("----------------------------------------------------------------------------");
        for (Payment p : payments) {
            System.out.println(String.format("%-5s | %-15s | %-12s | %-6s | %-6s | %-10s",
                    p.getId(),
                    p.getAmount() + " BGN",
                    p.getPaymentDate(),
                    p.getMonth(),
                    p.getYear(),
                    p.getApartment() != null ? p.getApartment().getNumber() : "N/A"));
        }
        System.out.println();
    }

    private static String truncate(String str, int maxLength) {
        if (str == null) return "N/A";
        return str.length() > maxLength ? str.substring(0, maxLength - 3) + "..." : str;
    }
}
