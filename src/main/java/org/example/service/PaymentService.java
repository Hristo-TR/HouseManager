package org.example.service;

import org.example.entity.Apartment;
import org.example.entity.Payment;
import org.example.repository.ApartmentRepository;
import org.example.repository.PaymentRepository;
import org.example.util.FileExportUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final ApartmentRepository apartmentRepository;
    private final FeeService feeService;

    public PaymentService() {
        this.paymentRepository = new PaymentRepository();
        this.apartmentRepository = new ApartmentRepository();
        this.feeService = new FeeService();
    }

    public Payment recordPayment(Long apartmentId, BigDecimal amount, LocalDate paymentDate,
                                 Integer month, Integer year) {
        Apartment apartment = apartmentRepository.findById(apartmentId);
        if (apartment == null) {
            throw new IllegalArgumentException("Apartment not found with id: " + apartmentId);
        }

        Payment payment = new Payment(amount, paymentDate, month, year, apartment);
        Payment savedPayment = paymentRepository.save(payment);

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
        return paymentRepository.findByApartment(apartmentId);
    }

    public List<Payment> getPaymentsByBuilding(Long buildingId) {
        return paymentRepository.findByBuilding(buildingId);
    }

    public List<Payment> getPaymentsByCompany(Long companyId) {
        return paymentRepository.findByCompany(companyId);
    }

    public List<Payment> getPaymentsByEmployee(Long employeeId) {
        return paymentRepository.findByEmployee(employeeId);
    }

    public BigDecimal getTotalPaidByApartment(Long apartmentId) {
        return paymentRepository.getTotalAmountByApartment(apartmentId);
    }

    public BigDecimal getTotalPaidByBuilding(Long buildingId) {
        return paymentRepository.getTotalAmountByBuilding(buildingId);
    }

    public BigDecimal getTotalPaidByCompany(Long companyId) {
        return paymentRepository.getTotalAmountByCompany(companyId);
    }

    public BigDecimal getTotalPaidByEmployee(Long employeeId) {
        return paymentRepository.getTotalAmountByEmployee(employeeId);
    }

    public void exportAllPaymentsToFile(String filename) {
        List<Payment> allPayments = paymentRepository.findAll();
        try {
            FileExportUtil.exportPaymentsToFile(allPayments, filename);
        } catch (Exception e) {
            throw new RuntimeException("Failed to export payments to file", e);
        }
    }
}
