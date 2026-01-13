package org.example.service;

import org.example.entity.Owner;
import org.example.repository.OwnerRepository;
import org.example.util.ValidationUtil;

import java.util.List;

public class OwnerService {
    private final OwnerRepository ownerRepository;

    public OwnerService() {
        this.ownerRepository = new OwnerRepository();
    }

    public Owner createOwner(String firstName, String lastName, String phone, String email) {
        Owner owner = new Owner(firstName, lastName, phone, email);
        String validationError = ValidationUtil.validate(owner);
        if (validationError != null) {
            throw new IllegalArgumentException("Validation failed: " + validationError);
        }
        return ownerRepository.save(owner);
    }

    public Owner updateOwner(Long id, String firstName, String lastName, String phone, String email) {
        Owner owner = ownerRepository.findById(id);
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
        return ownerRepository.update(owner);
    }

    public void deleteOwner(Long id) {
        Owner owner = ownerRepository.findById(id);
        if (owner == null) {
            throw new IllegalArgumentException("Owner not found with id: " + id);
        }
        ownerRepository.delete(id);
    }

    public Owner getOwnerById(Long id) {
        return ownerRepository.findById(id);
    }

    public List<Owner> getAllOwners() {
        return ownerRepository.findAll();
    }
}
