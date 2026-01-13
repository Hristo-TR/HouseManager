package org.example.service;

import org.example.entity.Apartment;
import org.example.entity.Pet;
import org.example.repository.ApartmentRepository;
import org.example.repository.PetRepository;
import org.example.util.ValidationUtil;

import java.util.List;

public class PetService {
    private final PetRepository petRepository;
    private final ApartmentRepository apartmentRepository;

    public PetService() {
        this.petRepository = new PetRepository();
        this.apartmentRepository = new ApartmentRepository();
    }

    public Pet createPet(String name, String type, Boolean usesCommonAreas, Long apartmentId) {
        Apartment apartment = apartmentRepository.findById(apartmentId);
        if (apartment == null) {
            throw new IllegalArgumentException("Apartment not found with id: " + apartmentId);
        }

        Pet pet = new Pet(name, type, usesCommonAreas, apartment);
        String validationError = ValidationUtil.validate(pet);
        if (validationError != null) {
            throw new IllegalArgumentException("Validation failed: " + validationError);
        }
        return petRepository.save(pet);
    }

    public Pet updatePet(Long id, String name, String type, Boolean usesCommonAreas) {
        Pet pet = petRepository.findById(id);
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
        return petRepository.update(pet);
    }

    public void deletePet(Long id) {
        Pet pet = petRepository.findById(id);
        if (pet == null) {
            throw new IllegalArgumentException("Pet not found with id: " + id);
        }
        petRepository.delete(id);
    }

    public Pet getPetById(Long id) {
        return petRepository.findById(id);
    }

    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    public List<Pet> getPetsByApartment(Long apartmentId) {
        return petRepository.findByApartment(apartmentId);
    }
}
