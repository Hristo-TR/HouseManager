package org.example.service;

import org.example.entity.Building;
import org.example.entity.Employee;
import org.example.repository.BuildingRepository;
import org.example.repository.EmployeeRepository;
import org.example.util.ValidationUtil;

import java.util.List;

public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final BuildingRepository buildingRepository;

    public EmployeeService() {
        this.employeeRepository = new EmployeeRepository();
        this.buildingRepository = new BuildingRepository();
    }

    public Employee createEmployee(String firstName, String lastName, String phone, Long companyId) {
        CompanyService companyService = new CompanyService();
        org.example.entity.Company company = companyService.getCompanyById(companyId);
        if (company == null) {
            throw new IllegalArgumentException("Company not found with id: " + companyId);
        }

        Employee employee = new Employee(firstName, lastName, phone, company);
        String validationError = ValidationUtil.validate(employee);
        if (validationError != null) {
            throw new IllegalArgumentException("Validation failed: " + validationError);
        }
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Long id, String firstName, String lastName, String phone) {
        Employee employee = employeeRepository.findById(id);
        if (employee == null) {
            throw new IllegalArgumentException("Employee not found with id: " + id);
        }
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setPhone(phone);
        String validationError = ValidationUtil.validate(employee);
        if (validationError != null) {
            throw new IllegalArgumentException("Validation failed: " + validationError);
        }
        return employeeRepository.update(employee);
    }

    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id);
        if (employee == null) {
            throw new IllegalArgumentException("Employee not found with id: " + id);
        }

        List<Building> buildings = employee.getBuildings();
        Long companyId = employee.getCompany().getId();

        employeeRepository.delete(id);

        redistributeBuildings(buildings, companyId);
    }

    private void redistributeBuildings(List<Building> buildings, Long companyId) {
        for (Building building : buildings) {
            Employee newEmployee = findEmployeeWithFewestBuildings(companyId);
            if (newEmployee != null) {
                building.setEmployee(newEmployee);
                buildingRepository.update(building);
            }
        }
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public List<Employee> getEmployeesByCompany(Long companyId) {
        return employeeRepository.findByCompany(companyId);
    }

    public List<Employee> getAllEmployeesSortedByName() {
        return employeeRepository.findAllSortedByName();
    }

    public List<Employee> getAllEmployeesSortedByBuildingCount() {
        return employeeRepository.findAllSortedByBuildingCount();
    }

    public Employee findEmployeeWithFewestBuildings(Long companyId) {
        return employeeRepository.findEmployeeWithFewestBuildings(companyId);
    }
}
