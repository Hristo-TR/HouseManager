package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

@Entity
@Table(name = "fee_configurations")
public class FeeConfiguration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Positive
    @Column(name = "base_rate_per_sqm", nullable = false, precision = 10, scale = 2)
    private BigDecimal baseRatePerSqm;

    @NotNull
    @Positive
    @Column(name = "elevator_fee_per_person", nullable = false, precision = 10, scale = 2)
    private BigDecimal elevatorFeePerPerson;

    @NotNull
    @Positive
    @Column(name = "pet_fee", nullable = false, precision = 10, scale = 2)
    private BigDecimal petFee;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "building_id", nullable = false, unique = true)
    private Building building;

    public FeeConfiguration() {
    }

    public FeeConfiguration(BigDecimal baseRatePerSqm, BigDecimal elevatorFeePerPerson,
                            BigDecimal petFee, Building building) {
        this.baseRatePerSqm = baseRatePerSqm;
        this.elevatorFeePerPerson = elevatorFeePerPerson;
        this.petFee = petFee;
        this.building = building;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBaseRatePerSqm() {
        return baseRatePerSqm;
    }

    public void setBaseRatePerSqm(BigDecimal baseRatePerSqm) {
        this.baseRatePerSqm = baseRatePerSqm;
    }

    public BigDecimal getElevatorFeePerPerson() {
        return elevatorFeePerPerson;
    }

    public void setElevatorFeePerPerson(BigDecimal elevatorFeePerPerson) {
        this.elevatorFeePerPerson = elevatorFeePerPerson;
    }

    public BigDecimal getPetFee() {
        return petFee;
    }

    public void setPetFee(BigDecimal petFee) {
        this.petFee = petFee;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    @Override
    public String toString() {
        return "FeeConfiguration{" +
                "id=" + id +
                ", baseRatePerSqm=" + baseRatePerSqm +
                ", elevatorFeePerPerson=" + elevatorFeePerPerson +
                ", petFee=" + petFee +
                '}';
    }
}
