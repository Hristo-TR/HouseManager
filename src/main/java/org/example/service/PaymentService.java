package org.example.service;

import org.example.dao.ApartmentDAO;
import org.example.dao.PaymentDAO;
import org.example.entity.Apartment;
import org.example.entity.Payment;
import org.example.util.FileExportUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class PaymentService {
    private final PaymentDAO paymentDAO;
    private final ApartmentDAO apartmentDAO;
    private final FeeService feeService;

    public PaymentService() {
        this.paymentDAO = new PaymentDAO();
        this.apartmentDAO = new ApartmentDAO();
        this.feeService = new FeeService();
    }

    public Payment recordPayment(Long apartmentId, BigDecimal amount, LocalDate paymentDate,
                                 Integer month, Integer year) {
        Apartment apartment = apartmentDAO.findById(apartmentId);
        if (apartment == null) {
            throw new IllegalArgumentException("Apartment not found with id: " + apartmentId);
        }

        Payment payment = new Payment(amount, paymentDate, month, year, apartment);
        Payment savedPayment = paymentDAO.save(payment);

        // Export payment to file
        try {
            String filename = "payment_" + apartmentId + "_" + year + "_" + month + ".txt";
            FileExportUtil.exportPaymentsToFile(List.of(savedPayment), filename);
        } catch (Exception e) {
            System.err.println("Warning: Failed to export payment to file: " + e.getMessage());
        }

        return savedPayment;
    }

    public Payment recordPaymentForMonth(Long apartmentId, Integer month, Integer year) {
        BigDecimal amount = feeService.calculateMonthlyFee(apartmentId);
        return recordPayment(apartmentId, amount, LocalDate.now(), month, year);
    }

    public List<Payment> getPaymentsByApartment(Long apartmentId) {
        return paymentDAO.findByApartment(apartmentId);
    }

    public List<Payment> getPaymentsByBuilding(Long buildingId) {
        return paymentDAO.findByBuilding(buildingId);
    }

    public List<Payment> getPaymentsByCompany(Long companyId) {
        return paymentDAO.findByCompany(companyId);
    }

    public List<Payment> getPaymentsByEmployee(Long employeeId) {
        return paymentDAO.findByEmployee(employeeId);
    }

    public BigDecimal getTotalPaidByApartment(Long apartmentId) {
        return paymentDAO.getTotalAmountByApartment(apartmentId);
    }

    public BigDecimal getTotalPaidByBuilding(Long buildingId) {
        return paymentDAO.getTotalAmountByBuilding(buildingId);
    }

    public BigDecimal getTotalPaidByCompany(Long companyId) {
        return paymentDAO.getTotalAmountByCompany(companyId);
    }

    public BigDecimal getTotalPaidByEmployee(Long employeeId) {
        return paymentDAO.getTotalAmountByEmployee(employeeId);
    }

    public void exportAllPaymentsToFile(String filename) {
        List<Payment> allPayments = paymentDAO.findAll();
        try {
            FileExportUtil.exportPaymentsToFile(allPayments, filename);
        } catch (Exception e) {
            throw new RuntimeException("Failed to export payments to file", e);
        }
    }
}
