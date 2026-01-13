package org.example.service;

import org.example.entity.Company;
import org.example.repository.CompanyRepository;
import org.example.util.ValidationUtil;

import java.util.List;

public class CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyService() {
        this.companyRepository = new CompanyRepository();
    }

    public Company createCompany(String name, String address, String phone, String email) {
        Company company = new Company(name, address, phone, email);
        String validationError = ValidationUtil.validate(company);
        if (validationError != null) {
            throw new IllegalArgumentException("Validation failed: " + validationError);
        }
        return companyRepository.save(company);
    }

    public Company updateCompany(Long id, String name, String address, String phone, String email) {
        Company company = companyRepository.findById(id);
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
        return companyRepository.update(company);
    }

    public void deleteCompany(Long id) {
        Company company = companyRepository.findById(id);
        if (company == null) {
            throw new IllegalArgumentException("Company not found with id: " + id);
        }
        companyRepository.delete(id);
    }

    public Company getCompanyById(Long id) {
        return companyRepository.findById(id);
    }

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public List<Company> getAllCompaniesSortedByRevenue() {
        return companyRepository.findAllSortedByRevenue();
    }
}
