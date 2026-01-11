package org.example.service;

import org.example.dao.ApartmentDAO;
import org.example.dao.BuildingDAO;
import org.example.dao.OwnerDAO;
import org.example.entity.Apartment;
import org.example.entity.Building;
import org.example.entity.Owner;
import org.example.util.ValidationUtil;

import java.math.BigDecimal;
import java.util.List;

public class ApartmentService {
    private final ApartmentDAO apartmentDAO;
    private final BuildingDAO buildingDAO;
    private final OwnerDAO ownerDAO;

    public ApartmentService() {
        this.apartmentDAO = new ApartmentDAO();
        this.buildingDAO = new BuildingDAO();
        this.ownerDAO = new OwnerDAO();
    }

    public Apartment createApartment(String number, Integer floor, BigDecimal area, Long buildingId, Long ownerId) {
        Building building = buildingDAO.findById(buildingId);
        if (building == null) {
            throw new IllegalArgumentException("Building not found with id: " + buildingId);
        }

        Owner owner = ownerDAO.findById(ownerId);
        if (owner == null) {
            throw new IllegalArgumentException("Owner not found with id: " + ownerId);
        }

        Apartment apartment = new Apartment(number, floor, area, building, owner);
        String validationError = ValidationUtil.validate(apartment);
        if (validationError != null) {
            throw new IllegalArgumentException("Validation failed: " + validationError);
        }
        return apartmentDAO.save(apartment);
    }

    public Apartment updateApartment(Long id, String number, Integer floor, BigDecimal area) {
        Apartment apartment = apartmentDAO.findById(id);
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
        return apartmentDAO.update(apartment);
    }

    public void deleteApartment(Long id) {
        Apartment apartment = apartmentDAO.findById(id);
        if (apartment == null) {
            throw new IllegalArgumentException("Apartment not found with id: " + id);
        }
        apartmentDAO.delete(id);
    }

    public Apartment getApartmentById(Long id) {
        return apartmentDAO.findById(id);
    }

    public List<Apartment> getAllApartments() {
        return apartmentDAO.findAll();
    }

    public List<Apartment> getApartmentsByBuilding(Long buildingId) {
        return apartmentDAO.findByBuilding(buildingId);
    }

    public List<Apartment> getApartmentsByOwner(Long ownerId) {
        return apartmentDAO.findByOwner(ownerId);
    }
}
