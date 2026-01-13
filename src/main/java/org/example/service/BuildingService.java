package org.example.service;

import org.example.entity.Building;
import org.example.entity.Company;
import org.example.entity.Employee;
import org.example.repository.BuildingRepository;
import org.example.repository.CompanyRepository;
import org.example.repository.EmployeeRepository;
import org.example.util.ValidationUtil;

import java.math.BigDecimal;
import java.util.List;

public class BuildingService {
    private final BuildingRepository buildingRepository;
    private final CompanyRepository companyRepository;
    private final EmployeeRepository employeeRepository;

    public BuildingService() {
        this.buildingRepository = new BuildingRepository();
        this.companyRepository = new CompanyRepository();
        this.employeeRepository = new EmployeeRepository();
    }

    public Building createBuilding(String address, Integer floors, Integer apartmentCount,
                                   BigDecimal builtArea, BigDecimal commonArea, Long companyId) {
        Company company = companyRepository.findById(companyId);
        if (company == null) {
            throw new IllegalArgumentException("Company not found with id: " + companyId);
        }

        Employee employee = employeeRepository.findEmployeeWithFewestBuildings(companyId);
        if (employee == null) {
            throw new IllegalArgumentException("No employees found for company with id: " + companyId);
        }

        Building building = new Building(address, floors, apartmentCount, builtArea, commonArea, company, employee);
        String validationError = ValidationUtil.validate(building);
        if (validationError != null) {
            throw new IllegalArgumentException("Validation failed: " + validationError);
        }
        return buildingRepository.save(building);
    }

    public Building updateBuilding(Long id, String address, Integer floors, Integer apartmentCount,
                                   BigDecimal builtArea, BigDecimal commonArea) {
        Building building = buildingRepository.findById(id);
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
        return buildingRepository.update(building);
    }

    public void deleteBuilding(Long id) {
        Building building = buildingRepository.findById(id);
        if (building == null) {
            throw new IllegalArgumentException("Building not found with id: " + id);
        }
        buildingRepository.delete(id);
    }

    public Building getBuildingById(Long id) {
        return buildingRepository.findById(id);
    }

    public List<Building> getAllBuildings() {
        return buildingRepository.findAll();
    }

    public List<Building> getBuildingsByCompany(Long companyId) {
        return buildingRepository.findByCompany(companyId);
    }

    public List<Building> getBuildingsByEmployee(Long employeeId) {
        return buildingRepository.findByEmployee(employeeId);
    }
}
