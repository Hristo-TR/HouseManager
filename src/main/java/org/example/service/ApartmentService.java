package org.example.service;

import org.example.entity.Apartment;
import org.example.entity.Building;
import org.example.entity.Owner;
import org.example.repository.ApartmentRepository;
import org.example.repository.BuildingRepository;
import org.example.repository.OwnerRepository;
import org.example.util.ValidationUtil;

import java.math.BigDecimal;
import java.util.List;

public class ApartmentService {
    private final ApartmentRepository apartmentRepository;
    private final BuildingRepository buildingRepository;
    private final OwnerRepository ownerRepository;

    public ApartmentService() {
        this.apartmentRepository = new ApartmentRepository();
        this.buildingRepository = new BuildingRepository();
        this.ownerRepository = new OwnerRepository();
    }

    public Apartment createApartment(String number, Integer floor, BigDecimal area, Long buildingId, Long ownerId) {
        Building building = buildingRepository.findById(buildingId);
        if (building == null) {
            throw new IllegalArgumentException("Building not found with id: " + buildingId);
        }

        Owner owner = ownerRepository.findById(ownerId);
        if (owner == null) {
            throw new IllegalArgumentException("Owner not found with id: " + ownerId);
        }

        Apartment apartment = new Apartment(number, floor, area, building, owner);
        String validationError = ValidationUtil.validate(apartment);
        if (validationError != null) {
            throw new IllegalArgumentException("Validation failed: " + validationError);
        }
        return apartmentRepository.save(apartment);
    }

    public Apartment updateApartment(Long id, String number, Integer floor, BigDecimal area) {
        Apartment apartment = apartmentRepository.findById(id);
        if (apartment == null) {
            throw new IllegalArgumentException("Apartment not found with id: " + id);
        }
        apartment.setNumber(number);
        apartment.setFloor(floor);
        apartment.setArea(area);
        String validationError = ValidationUtil.validate(apartment);
        if (validationError != null) {
            throw new IllegalArgumentException("Validation failed: " + validationError);
        }
        return apartmentRepository.update(apartment);
    }

    public void deleteApartment(Long id) {
        Apartment apartment = apartmentRepository.findById(id);
        if (apartment == null) {
            throw new IllegalArgumentException("Apartment not found with id: " + id);
        }
        apartmentRepository.delete(id);
    }

    public Apartment getApartmentById(Long id) {
        return apartmentRepository.findById(id);
    }

    public List<Apartment> getAllApartments() {
        return apartmentRepository.findAll();
    }

    public List<Apartment> getApartmentsByBuilding(Long buildingId) {
        return apartmentRepository.findByBuilding(buildingId);
    }

    public List<Apartment> getApartmentsByOwner(Long ownerId) {
        return apartmentRepository.findByOwner(ownerId);
    }
}
