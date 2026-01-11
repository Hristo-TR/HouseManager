package org.example.service;

import org.example.dao.CompanyDAO;
import org.example.entity.Company;
import org.example.util.ValidationUtil;

import java.util.List;

public class CompanyService {
    private final CompanyDAO companyDAO;

    public CompanyService() {
        this.companyDAO = new CompanyDAO();
    }

    public Company createCompany(String name, String address, String phone, String email) {
        Company company = new Company(name, address, phone, email);
        String validationError = ValidationUtil.validate(company);
        if (validationError != null) {
            throw new IllegalArgumentException("Validation failed: " + validationError);
        }
        return companyDAO.save(company);
    }

    public Company updateCompany(Long id, String name, String address, String phone, String email) {
        Company company = companyDAO.findById(id);
        if (company == null) {
            throw new IllegalArgumentException("Company not found with id: " + id);
        }
        company.setName(name);
        company.setAddress(address);
        company.setPhone(phone);
        company.setEmail(email);
        String validationError = ValidationUtil.validate(company);
        if (validationError != null) {
            throw new IllegalArgumentException("Validation failed: " + validationError);
        }
        return companyDAO.update(company);
    }

    public void deleteCompany(Long id) {
        Company company = companyDAO.findById(id);
        if (company == null) {
            throw new IllegalArgumentException("Company not found with id: " + id);
        }
        companyDAO.delete(id);
    }

    public Company getCompanyById(Long id) {
        return companyDAO.findById(id);
    }

    public List<Company> getAllCompanies() {
        return companyDAO.findAll();
    }

    public List<Company> getAllCompaniesSortedByRevenue() {
        return companyDAO.findAllSortedByRevenue();
    }
}
