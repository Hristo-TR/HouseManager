package org.example.service;

import org.example.dao.BuildingDAO;
import org.example.dao.EmployeeDAO;
import org.example.entity.Building;
import org.example.entity.Employee;
import org.example.util.ValidationUtil;

import java.util.List;

public class EmployeeService {
    private final EmployeeDAO employeeDAO;
    private final BuildingDAO buildingDAO;

    public EmployeeService() {
        this.employeeDAO = new EmployeeDAO();
        this.buildingDAO = new BuildingDAO();
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
        return employeeDAO.save(employee);
    }

    public Employee updateEmployee(Long id, String firstName, String lastName, String phone) {
        Employee employee = employeeDAO.findById(id);
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
        return employeeDAO.update(employee);
    }

    public void deleteEmployee(Long id) {
        Employee employee = employeeDAO.findById(id);
        if (employee == null) {
            throw new IllegalArgumentException("Employee not found with id: " + id);
        }

        List<Building> buildings = employee.getBuildings();
        Long companyId = employee.getCompany().getId();

        employeeDAO.delete(id);

        // Redistribute buildings to other employees
        redistributeBuildings(buildings, companyId);
    }

    private void redistributeBuildings(List<Building> buildings, Long companyId) {
        for (Building building : buildings) {
            Employee newEmployee = findEmployeeWithFewestBuildings(companyId);
            if (newEmployee != null) {
                building.setEmployee(newEmployee);
                buildingDAO.update(building);
            }
        }
    }

    public Employee getEmployeeById(Long id) {
        return employeeDAO.findById(id);
    }

    public List<Employee> getAllEmployees() {
        return employeeDAO.findAll();
    }

    public List<Employee> getEmployeesByCompany(Long companyId) {
        return employeeDAO.findByCompany(companyId);
    }

    public List<Employee> getAllEmployeesSortedByName() {
        return employeeDAO.findAllSortedByName();
    }

    public List<Employee> getAllEmployeesSortedByBuildingCount() {
        return employeeDAO.findAllSortedByBuildingCount();
    }

    public Employee findEmployeeWithFewestBuildings(Long companyId) {
        return employeeDAO.findEmployeeWithFewestBuildings(companyId);
    }
}
