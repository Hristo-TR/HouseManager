package org.example.service;

import org.example.entity.Apartment;
import org.example.entity.Resident;
import org.example.repository.ApartmentRepository;
import org.example.repository.ResidentRepository;
import org.example.util.ValidationUtil;

import java.time.LocalDate;
import java.util.List;

public class ResidentService {
    private final ResidentRepository residentRepository;
    private final ApartmentRepository apartmentRepository;

    public ResidentService() {
        this.residentRepository = new ResidentRepository();
        this.apartmentRepository = new ApartmentRepository();
    }

    public Resident createResident(String firstName, String lastName, LocalDate birthDate,
                                   Boolean usesElevator, Long apartmentId) {
        Apartment apartment = apartmentRepository.findById(apartmentId);
        if (apartment == null) {
            throw new IllegalArgumentException("Apartment not found with id: " + apartmentId);
        }

        Resident resident = new Resident(firstName, lastName, birthDate, usesElevator, apartment);
        String validationError = ValidationUtil.validate(resident);
        if (validationError != null) {
            throw new IllegalArgumentException("Validation failed: " + validationError);
        }
        return residentRepository.save(resident);
    }

    public Resident updateResident(Long id, String firstName, String lastName, LocalDate birthDate,
                                   Boolean usesElevator) {
        Resident resident = residentRepository.findById(id);
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
        return residentRepository.update(resident);
    }

    public void deleteResident(Long id) {
        Resident resident = residentRepository.findById(id);
        if (resident == null) {
            throw new IllegalArgumentException("Resident not found with id: " + id);
        }
        residentRepository.delete(id);
    }

    public Resident getResidentById(Long id) {
        return residentRepository.findById(id);
    }

    public List<Resident> getAllResidents() {
        return residentRepository.findAll();
    }

    public List<Resident> getResidentsByApartment(Long apartmentId) {
        return residentRepository.findByApartment(apartmentId);
    }

    public List<Resident> getResidentsByBuilding(Long buildingId) {
        return residentRepository.findByBuilding(buildingId);
    }

    public List<Resident> getAllResidentsSortedByName() {
        return residentRepository.findAllSortedByName();
    }

    public List<Resident> getAllResidentsSortedByAge() {
        return residentRepository.findAllSortedByAge();
    }
}
