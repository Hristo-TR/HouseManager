package org.example.service;

import org.example.dao.OwnerDAO;
import org.example.entity.Owner;
import org.example.util.ValidationUtil;

import java.util.List;

public class OwnerService {
    private final OwnerDAO ownerDAO;

    public OwnerService() {
        this.ownerDAO = new OwnerDAO();
    }

    public Owner createOwner(String firstName, String lastName, String phone, String email) {
        Owner owner = new Owner(firstName, lastName, phone, email);
        String validationError = ValidationUtil.validate(owner);
        if (validationError != null) {
            throw new IllegalArgumentException("Validation failed: " + validationError);
        }
        return ownerDAO.save(owner);
    }

    public Owner updateOwner(Long id, String firstName, String lastName, String phone, String email) {
        Owner owner = ownerDAO.findById(id);
        if (owner == null) {
            throw new IllegalArgumentException("Owner not found with id: " + id);
        }
        owner.setFirstName(firstName);
        owner.setLastName(lastName);
        owner.setPhone(phone);
        owner.setEmail(email);
        String validationError = ValidationUtil.validate(owner);
        if (validationError != null) {
            throw new IllegalArgumentException("Validation failed: " + validationError);
        }
        return ownerDAO.update(owner);
    }

    public void deleteOwner(Long id) {
        Owner owner = ownerDAO.findById(id);
        if (owner == null) {
            throw new IllegalArgumentException("Owner not found with id: " + id);
        }
        ownerDAO.delete(id);
    }

    public Owner getOwnerById(Long id) {
        return ownerDAO.findById(id);
    }

    public List<Owner> getAllOwners() {
        return ownerDAO.findAll();
    }
}
