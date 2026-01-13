package org.example.service;

import org.example.entity.Apartment;
import org.example.entity.FeeConfiguration;
import org.example.entity.Pet;
import org.example.entity.Resident;
import org.example.repository.ApartmentRepository;
import org.example.repository.BuildingRepository;
import org.example.repository.FeeConfigurationRepository;

import java.math.BigDecimal;

public class FeeService {
    private final FeeConfigurationRepository feeConfigurationRepository;
    private final ApartmentRepository apartmentRepository;
    private final BuildingRepository buildingRepository;

    public FeeService() {
        this.feeConfigurationRepository = new FeeConfigurationRepository();
        this.apartmentRepository = new ApartmentRepository();
        this.buildingRepository = new BuildingRepository();
    }

    public void setFeeConfiguration(Long buildingId, BigDecimal baseRatePerSqm,
                                    BigDecimal elevatorFeePerPerson, BigDecimal petFee) {
        org.example.entity.Building building = buildingRepository.findById(buildingId);
        if (building == null) {
            throw new IllegalArgumentException("Building not found with id: " + buildingId);
        }

        FeeConfiguration feeConfig = feeConfigurationRepository.findByBuilding(buildingId);
        if (feeConfig == null) {
            feeConfig = new FeeConfiguration(baseRatePerSqm, elevatorFeePerPerson, petFee, building);
            feeConfigurationRepository.save(feeConfig);
        } else {
            feeConfig.setBaseRatePerSqm(baseRatePerSqm);
            feeConfig.setElevatorFeePerPerson(elevatorFeePerPerson);
            feeConfig.setPetFee(petFee);
            feeConfigurationRepository.update(feeConfig);
        }
    }

    public BigDecimal calculateMonthlyFee(Long apartmentId) {
        Apartment apartment = apartmentRepository.findById(apartmentId);
        if (apartment == null) {
            throw new IllegalArgumentException("Apartment not found with id: " + apartmentId);
        }

        FeeConfiguration feeConfig = feeConfigurationRepository.findByBuilding(apartment.getBuilding().getId());
        if (feeConfig == null) {
            throw new IllegalStateException("Fee configuration not set for building: " + apartment.getBuilding().getId());
        }

        BigDecimal totalFee = BigDecimal.ZERO;

        totalFee = totalFee.add(apartment.getArea().multiply(feeConfig.getBaseRatePerSqm()));

        int residentsOver7UsingElevator = 0;
        for (Resident resident : apartment.getResidents()) {
            if (resident.isOver7YearsOld() && resident.getUsesElevator()) {
                residentsOver7UsingElevator++;
            }
        }
        totalFee = totalFee.add(feeConfig.getElevatorFeePerPerson()
                .multiply(BigDecimal.valueOf(residentsOver7UsingElevator)));

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
        return feeConfigurationRepository.findByBuilding(buildingId);
    }
}
