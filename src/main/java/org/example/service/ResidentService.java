package org.example.service;

import org.example.dao.ApartmentDAO;
import org.example.dao.ResidentDAO;
import org.example.entity.Apartment;
import org.example.entity.Resident;
import org.example.util.ValidationUtil;

import java.time.LocalDate;
import java.util.List;

public class ResidentService {
    private final ResidentDAO residentDAO;
    private final ApartmentDAO apartmentDAO;

    public ResidentService() {
        this.residentDAO = new ResidentDAO();
        this.apartmentDAO = new ApartmentDAO();
    }

    public Resident createResident(String firstName, String lastName, LocalDate birthDate,
                                   Boolean usesElevator, Long apartmentId) {
        Apartment apartment = apartmentDAO.findById(apartmentId);
        if (apartment == null) {
            throw new IllegalArgumentException("Apartment not found with id: " + apartmentId);
        }

        Resident resident = new Resident(firstName, lastName, birthDate, usesElevator, apartment);
        String validationError = ValidationUtil.validate(resident);
        if (validationError != null) {
            throw new IllegalArgumentException("Validation failed: " + validationError);
        }
        return residentDAO.save(resident);
    }

    public Resident updateResident(Long id, String firstName, String lastName, LocalDate birthDate,
                                   Boolean usesElevator) {
        Resident resident = residentDAO.findById(id);
        if (resident == null) {
            throw new IllegalArgumentException("Resident not found with id: " + id);
        }
        resident.setFirstName(firstName);
        resident.setLastName(lastName);
        resident.setBirthDate(birthDate);
        resident.setUsesElevator(usesElevator);
        String validationError = ValidationUtil.validate(resident);
        if (validationError != null) {
            throw new IllegalArgumentException("Validation failed: " + validationError);
        }
        return residentDAO.update(resident);
    }

    public void deleteResident(Long id) {
        Resident resident = residentDAO.findById(id);
        if (resident == null) {
            throw new IllegalArgumentException("Resident not found with id: " + id);
        }
        residentDAO.delete(id);
    }

    public Resident getResidentById(Long id) {
        return residentDAO.findById(id);
    }

    public List<Resident> getAllResidents() {
        return residentDAO.findAll();
    }

    public List<Resident> getResidentsByApartment(Long apartmentId) {
        return residentDAO.findByApartment(apartmentId);
    }

    public List<Resident> getResidentsByBuilding(Long buildingId) {
        return residentDAO.findByBuilding(buildingId);
    }

    public List<Resident> getAllResidentsSortedByName() {
        return residentDAO.findAllSortedByName();
    }

    public List<Resident> getAllResidentsSortedByAge() {
        return residentDAO.findAllSortedByAge();
    }
}
