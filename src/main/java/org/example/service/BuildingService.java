package org.example.service;

import org.example.dao.BuildingDAO;
import org.example.dao.CompanyDAO;
import org.example.dao.EmployeeDAO;
import org.example.entity.Building;
import org.example.entity.Company;
import org.example.entity.Employee;
import org.example.util.ValidationUtil;

import java.math.BigDecimal;
import java.util.List;

public class BuildingService {
    private final BuildingDAO buildingDAO;
    private final CompanyDAO companyDAO;
    private final EmployeeDAO employeeDAO;

    public BuildingService() {
        this.buildingDAO = new BuildingDAO();
        this.companyDAO = new CompanyDAO();
        this.employeeDAO = new EmployeeDAO();
    }

    public Building createBuilding(String address, Integer floors, Integer apartmentCount,
                                   BigDecimal builtArea, BigDecimal commonArea, Long companyId) {
        Company company = companyDAO.findById(companyId);
        if (company == null) {
            throw new IllegalArgumentException("Company not found with id: " + companyId);
        }

        Employee employee = employeeDAO.findEmployeeWithFewestBuildings(companyId);
        if (employee == null) {
            throw new IllegalArgumentException("No employees found for company with id: " + companyId);
        }

        Building building = new Building(address, floors, apartmentCount, builtArea, commonArea, company, employee);
        String validationError = ValidationUtil.validate(building);
        if (validationError != null) {
            throw new IllegalArgumentException("Validation failed: " + validationError);
        }
        return buildingDAO.save(building);
    }

    public Building updateBuilding(Long id, String address, Integer floors, Integer apartmentCount,
                                   BigDecimal builtArea, BigDecimal commonArea) {
        Building building = buildingDAO.findById(id);
        if (building == null) {
            throw new IllegalArgumentException("Building not found with id: " + id);
        }
        building.setAddress(address);
        building.setFloors(floors);
        building.setApartmentCount(apartmentCount);
        building.setBuiltArea(builtArea);
        building.setCommonArea(commonArea);
        String validationError = ValidationUtil.validate(building);
        if (validationError != null) {
            throw new IllegalArgumentException("Validation failed: " + validationError);
        }
        return buildingDAO.update(building);
    }

    public void deleteBuilding(Long id) {
        Building building = buildingDAO.findById(id);
        if (building == null) {
            throw new IllegalArgumentException("Building not found with id: " + id);
        }
        buildingDAO.delete(id);
    }

    public Building getBuildingById(Long id) {
        return buildingDAO.findById(id);
    }

    public List<Building> getAllBuildings() {
        return buildingDAO.findAll();
    }

    public List<Building> getBuildingsByCompany(Long companyId) {
        return buildingDAO.findByCompany(companyId);
    }

    public List<Building> getBuildingsByEmployee(Long employeeId) {
        return buildingDAO.findByEmployee(employeeId);
    }
}
