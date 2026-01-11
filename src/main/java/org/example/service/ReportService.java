package org.example.service;

import org.example.dao.BuildingDAO;
import org.example.dao.CompanyDAO;
import org.example.entity.Apartment;
import org.example.entity.Building;
import org.example.entity.Company;
import org.example.entity.Resident;

import java.math.BigDecimal;
import java.util.List;

public class ReportService {
    private final CompanyDAO companyDAO;
    private final BuildingDAO buildingDAO;
    private final PaymentService paymentService;
    private final FeeService feeService;

    public ReportService() {
        this.companyDAO = new CompanyDAO();
        this.buildingDAO = new BuildingDAO();
        this.paymentService = new PaymentService();
        this.feeService = new FeeService();
    }

    // Reports for buildings serviced by each employee
    public int getBuildingCountByEmployee(Long employeeId) {
        List<Building> buildings = buildingDAO.findByEmployee(employeeId);
        return buildings.size();
    }

    public List<Building> getBuildingsByEmployee(Long employeeId) {
        return buildingDAO.findByEmployee(employeeId);
    }

    // Reports for apartments in a building
    public int getApartmentCountByBuilding(Long buildingId) {
        Building building = buildingDAO.findById(buildingId);
        return building != null ? building.getApartments().size() : 0;
    }

    public List<Apartment> getApartmentsByBuilding(Long buildingId) {
        Building building = buildingDAO.findById(buildingId);
        return building != null ? building.getApartments() : List.of();
    }

    // Reports for residents in a building
    public int getResidentCountByBuilding(Long buildingId) {
        Building building = buildingDAO.findById(buildingId);
        if (building == null) {
            return 0;
        }
        int count = 0;
        for (Apartment apartment : building.getApartments()) {
            count += apartment.getResidents().size();
        }
        return count;
    }

    public List<Resident> getResidentsByBuilding(Long buildingId) {
        Building building = buildingDAO.findById(buildingId);
        if (building == null) {
            return List.of();
        }
        return building.getApartments().stream()
                .flatMap(apartment -> apartment.getResidents().stream())
                .toList();
    }

    // Reports for amounts to be paid
    public BigDecimal getTotalAmountToPayByCompany(Long companyId) {
        Company company = companyDAO.findById(companyId);
        if (company == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal total = BigDecimal.ZERO;
        for (Building building : company.getBuildings()) {
            total = total.add(getTotalAmountToPayByBuilding(building.getId()));
        }
        return total;
    }

    public BigDecimal getTotalAmountToPayByBuilding(Long buildingId) {
        Building building = buildingDAO.findById(buildingId);
        if (building == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal total = BigDecimal.ZERO;
        for (Apartment apartment : building.getApartments()) {
            total = total.add(feeService.calculateMonthlyFee(apartment.getId()));
        }
        return total;
    }

    public BigDecimal getTotalAmountToPayByEmployee(Long employeeId) {
        List<Building> buildings = buildingDAO.findByEmployee(employeeId);
        BigDecimal total = BigDecimal.ZERO;
        for (Building building : buildings) {
            total = total.add(getTotalAmountToPayByBuilding(building.getId()));
        }
        return total;
    }

    // Reports for paid amounts
    public BigDecimal getTotalPaidByCompany(Long companyId) {
        return paymentService.getTotalPaidByCompany(companyId);
    }

    public BigDecimal getTotalPaidByBuilding(Long buildingId) {
        return paymentService.getTotalPaidByBuilding(buildingId);
    }

    public BigDecimal getTotalPaidByEmployee(Long employeeId) {
        return paymentService.getTotalPaidByEmployee(employeeId);
    }
}
