package org.example.service;

import org.example.dao.ApartmentDAO;
import org.example.dao.PetDAO;
import org.example.entity.Apartment;
import org.example.entity.Pet;
import org.example.util.ValidationUtil;

import java.util.List;

public class PetService {
    private final PetDAO petDAO;
    private final ApartmentDAO apartmentDAO;

    public PetService() {
        this.petDAO = new PetDAO();
        this.apartmentDAO = new ApartmentDAO();
    }

    public Pet createPet(String name, String type, Boolean usesCommonAreas, Long apartmentId) {
        Apartment apartment = apartmentDAO.findById(apartmentId);
        if (apartment == null) {
            throw new IllegalArgumentException("Apartment not found with id: " + apartmentId);
        }

        Pet pet = new Pet(name, type, usesCommonAreas, apartment);
        String validationError = ValidationUtil.validate(pet);
        if (validationError != null) {
            throw new IllegalArgumentException("Validation failed: " + validationError);
        }
        return petDAO.save(pet);
    }

    public Pet updatePet(Long id, String name, String type, Boolean usesCommonAreas) {
        Pet pet = petDAO.findById(id);
        if (pet == null) {
            throw new IllegalArgumentException("Pet not found with id: " + id);
        }
        pet.setName(name);
        pet.setType(type);
        pet.setUsesCommonAreas(usesCommonAreas);
        String validationError = ValidationUtil.validate(pet);
        if (validationError != null) {
            throw new IllegalArgumentException("Validation failed: " + validationError);
        }
        return petDAO.update(pet);
    }

    public void deletePet(Long id) {
        Pet pet = petDAO.findById(id);
        if (pet == null) {
            throw new IllegalArgumentException("Pet not found with id: " + id);
        }
        petDAO.delete(id);
    }

    public Pet getPetById(Long id) {
        return petDAO.findById(id);
    }

    public List<Pet> getAllPets() {
        return petDAO.findAll();
    }

    public List<Pet> getPetsByApartment(Long apartmentId) {
        return petDAO.findByApartment(apartmentId);
    }
}
