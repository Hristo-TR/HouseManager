package org.example.ui;

import org.example.entity.Payment;
import org.example.service.FeeService;
import org.example.service.PaymentService;
import org.example.util.DisplayUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class PaymentMenu {
    private final Scanner scanner;
    private final PaymentService paymentService;
    private final FeeService feeService;

    public PaymentMenu(Scanner scanner) {
        this.scanner = scanner;
        this.paymentService = new PaymentService();
        this.feeService = new FeeService();
    }

    public void showMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Payment Management ---");
            System.out.println("1. Set Fee Configuration for Building");
            System.out.println("2. Calculate Monthly Fee for Apartment");
            System.out.println("3. Record Payment");
            System.out.println("4. Record Payment for Month");
            System.out.println("5. View Payments by Apartment");
            System.out.println("6. View Payments by Building");
            System.out.println("7. View Payments by Company");
            System.out.println("8. View Payments by Employee");
            System.out.println("0. Back to Main Menu");

            int choice = ConsoleUI.getIntInput(scanner, "Enter your choice: ");

            switch (choice) {
                case 1:
                    setFeeConfiguration();
                    break;
                case 2:
                    calculateMonthlyFee();
                    break;
                case 3:
                    recordPayment();
                    break;
                case 4:
                    recordPaymentForMonth();
                    break;
                case 5:
                    viewPaymentsByApartment();
                    break;
                case 6:
                    viewPaymentsByBuilding();
                    break;
                case 7:
                    viewPaymentsByCompany();
                    break;
                case 8:
                    viewPaymentsByEmployee();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void setFeeConfiguration() {
        Long buildingId = (long) ConsoleUI.getIntInput(scanner, "Enter building ID: ");
        System.out.print("Enter base rate per sqm: ");
        BigDecimal baseRate = scanner.nextBigDecimal();
        System.out.print("Enter elevator fee per person: ");
        BigDecimal elevatorFee = scanner.nextBigDecimal();
        System.out.print("Enter pet fee: ");
        BigDecimal petFee = scanner.nextBigDecimal();

        try {
            feeService.setFeeConfiguration(buildingId, baseRate, elevatorFee, petFee);
            System.out.println("Fee configuration set successfully");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void calculateMonthlyFee() {
        Long apartmentId = (long) ConsoleUI.getIntInput(scanner, "Enter apartment ID: ");
        try {
            BigDecimal fee = feeService.calculateMonthlyFee(apartmentId);
            System.out.println("Monthly fee: " + fee + " BGN");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void recordPayment() {
        Long apartmentId = (long) ConsoleUI.getIntInput(scanner, "Enter apartment ID: ");
        System.out.print("Enter amount: ");
        BigDecimal amount = scanner.nextBigDecimal();
        scanner.nextLine();
        System.out.print("Enter payment date (YYYY-MM-DD): ");
        String dateStr = scanner.nextLine();
        LocalDate paymentDate = LocalDate.parse(dateStr);
        System.out.print("Enter month (1-12): ");
        int month = scanner.nextInt();
        System.out.print("Enter year: ");
        int year = scanner.nextInt();

        try {
            Payment payment = paymentService.recordPayment(apartmentId, amount, paymentDate, month, year);
            System.out.println("Payment recorded successfully!");
            DisplayUtil.displayPayment(payment);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void recordPaymentForMonth() {
        Long apartmentId = (long) ConsoleUI.getIntInput(scanner, "Enter apartment ID: ");
        System.out.print("Enter month (1-12): ");
        int month = scanner.nextInt();
        System.out.print("Enter year: ");
        int year = scanner.nextInt();

        try {
            Payment payment = paymentService.recordPaymentForMonth(apartmentId, month, year);
            System.out.println("Payment recorded successfully!");
            DisplayUtil.displayPayment(payment);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewPaymentsByApartment() {
        Long apartmentId = (long) ConsoleUI.getIntInput(scanner, "Enter apartment ID: ");
        try {
            List<Payment> payments = paymentService.getPaymentsByApartment(apartmentId);
            if (payments.isEmpty()) {
                System.out.println("No payments found for this apartment.");
            } else {
                DisplayUtil.displayPayments(payments);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewPaymentsByBuilding() {
        Long buildingId = (long) ConsoleUI.getIntInput(scanner, "Enter building ID: ");
        try {
            List<Payment> payments = paymentService.getPaymentsByBuilding(buildingId);
            if (payments.isEmpty()) {
                System.out.println("No payments found for this building.");
            } else {
                DisplayUtil.displayPayments(payments);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewPaymentsByCompany() {
        Long companyId = (long) ConsoleUI.getIntInput(scanner, "Enter company ID: ");
        try {
            List<Payment> payments = paymentService.getPaymentsByCompany(companyId);
            if (payments.isEmpty()) {
                System.out.println("No payments found for this company.");
            } else {
                DisplayUtil.displayPayments(payments);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewPaymentsByEmployee() {
        Long employeeId = (long) ConsoleUI.getIntInput(scanner, "Enter employee ID: ");
        try {
            List<Payment> payments = paymentService.getPaymentsByEmployee(employeeId);
            if (payments.isEmpty()) {
                System.out.println("No payments found for this employee.");
            } else {
                DisplayUtil.displayPayments(payments);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
