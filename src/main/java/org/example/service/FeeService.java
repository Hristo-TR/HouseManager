package org.example.service;

import org.example.dao.ApartmentDAO;
import org.example.dao.BuildingDAO;
import org.example.dao.FeeConfigurationDAO;
import org.example.entity.Apartment;
import org.example.entity.FeeConfiguration;
import org.example.entity.Pet;
import org.example.entity.Resident;

import java.math.BigDecimal;

public class FeeService {
    private final FeeConfigurationDAO feeConfigurationDAO;
    private final ApartmentDAO apartmentDAO;
    private final BuildingDAO buildingDAO;

    public FeeService() {
        this.feeConfigurationDAO = new FeeConfigurationDAO();
        this.apartmentDAO = new ApartmentDAO();
        this.buildingDAO = new BuildingDAO();
    }

    public void setFeeConfiguration(Long buildingId, BigDecimal baseRatePerSqm,
                                    BigDecimal elevatorFeePerPerson, BigDecimal petFee) {
        org.example.entity.Building building = buildingDAO.findById(buildingId);
        if (building == null) {
            throw new IllegalArgumentException("Building not found with id: " + buildingId);
        }

        FeeConfiguration feeConfig = feeConfigurationDAO.findByBuilding(buildingId);
        if (feeConfig == null) {
            feeConfig = new FeeConfiguration(baseRatePerSqm, elevatorFeePerPerson, petFee, building);
            feeConfigurationDAO.save(feeConfig);
        } else {
            feeConfig.setBaseRatePerSqm(baseRatePerSqm);
            feeConfig.setElevatorFeePerPerson(elevatorFeePerPerson);
            feeConfig.setPetFee(petFee);
            feeConfigurationDAO.update(feeConfig);
        }
    }

    public BigDecimal calculateMonthlyFee(Long apartmentId) {
        Apartment apartment = apartmentDAO.findById(apartmentId);
        if (apartment == null) {
            throw new IllegalArgumentException("Apartment not found with id: " + apartmentId);
        }

        FeeConfiguration feeConfig = feeConfigurationDAO.findByBuilding(apartment.getBuilding().getId());
        if (feeConfig == null) {
            throw new IllegalStateException("Fee configuration not set for building: " + apartment.getBuilding().getId());
        }

        BigDecimal totalFee = BigDecimal.ZERO;

        // Base fee: apartment area * base rate per sqm
        totalFee = totalFee.add(apartment.getArea().multiply(feeConfig.getBaseRatePerSqm()));

        // Elevator fee: residents over 7 years old using elevator
        int residentsOver7UsingElevator = 0;
        for (Resident resident : apartment.getResidents()) {
            if (resident.isOver7YearsOld() && resident.getUsesElevator()) {
                residentsOver7UsingElevator++;
            }
        }
        totalFee = totalFee.add(feeConfig.getElevatorFeePerPerson()
                .multiply(BigDecimal.valueOf(residentsOver7UsingElevator)));

        // Pet fee: pets using common areas
        int petsUsingCommonAreas = 0;
        for (Pet pet : apartment.getPets()) {
            if (pet.getUsesCommonAreas()) {
                petsUsingCommonAreas++;
            }
        }
        totalFee = totalFee.add(feeConfig.getPetFee()
                .multiply(BigDecimal.valueOf(petsUsingCommonAreas)));

        return totalFee;
    }

    public FeeConfiguration getFeeConfiguration(Long buildingId) {
        return feeConfigurationDAO.findByBuilding(buildingId);
    }
}
