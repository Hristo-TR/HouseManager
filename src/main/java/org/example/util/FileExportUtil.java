package org.example.util;

import org.example.entity.Payment;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FileExportUtil {
    private static final String EXPORT_DIRECTORY = "exports";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void exportPaymentsToFile(List<Payment> payments, String filename) throws IOException {
        java.io.File dir = new java.io.File(EXPORT_DIRECTORY);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String filepath = EXPORT_DIRECTORY + "/" + filename;
        try (PrintWriter writer = new PrintWriter(new FileWriter(filepath))) {
            for (Payment payment : payments) {
                writer.println("Payment Receipt");
                writer.println("================");
                writer.println("Company: " + payment.getApartment().getBuilding().getCompany().getName());
                writer.println("Employee: " + payment.getApartment().getBuilding().getEmployee().getFullName());
                writer.println("Building: " + payment.getApartment().getBuilding().getAddress());
                writer.println("Apartment: " + payment.getApartment().getNumber());
                writer.println("Amount: " + payment.getAmount() + " BGN");
                writer.println("Date: " + payment.getPaymentDate().format(DATE_FORMATTER));
                writer.println("================\n");
            }
        }
    }
}
