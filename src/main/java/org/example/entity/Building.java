package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "buildings")
public class Building {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String address;

    @NotNull
    @Positive(message = "Number of floors must be positive")
    @Column(nullable = false)
    private Integer floors;

    @NotNull
    @Positive
    @Column(name = "apartment_count", nullable = false)
    private Integer apartmentCount;

    @NotNull
    @Positive
    @Column(name = "built_area", nullable = false, precision = 10, scale = 2)
    private BigDecimal builtArea;

    @NotNull
    @Positive
    @Column(name = "common_area", nullable = false, precision = 10, scale = 2)
    private BigDecimal commonArea;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Apartment> apartments = new ArrayList<>();

    @OneToOne(mappedBy = "building", cascade = CascadeType.ALL, orphanRemoval = true)
    private FeeConfiguration feeConfiguration;

    public Building() {
    }

    public Building(String address, Integer floors, Integer apartmentCount,
                    BigDecimal builtArea, BigDecimal commonArea, Company company, Employee employee) {
        this.address = address;
        this.floors = floors;
        this.apartmentCount = apartmentCount;
        this.builtArea = builtArea;
        this.commonArea = commonArea;
        this.company = company;
        this.employee = employee;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getFloors() {
        return floors;
    }

    public void setFloors(Integer floors) {
        this.floors = floors;
    }

    public Integer getApartmentCount() {
        return apartmentCount;
    }

    public void setApartmentCount(Integer apartmentCount) {
        this.apartmentCount = apartmentCount;
    }

    public BigDecimal getBuiltArea() {
        return builtArea;
    }

    public void setBuiltArea(BigDecimal builtArea) {
        this.builtArea = builtArea;
    }

    public BigDecimal getCommonArea() {
        return commonArea;
    }

    public void setCommonArea(BigDecimal commonArea) {
        this.commonArea = commonArea;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public List<Apartment> getApartments() {
        return apartments;
    }

    public void setApartments(List<Apartment> apartments) {
        this.apartments = apartments;
    }

    public FeeConfiguration getFeeConfiguration() {
        return feeConfiguration;
    }

    public void setFeeConfiguration(FeeConfiguration feeConfiguration) {
        this.feeConfiguration = feeConfiguration;
    }

    @Override
    public String toString() {
        return "Building{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", floors=" + floors +
                ", apartmentCount=" + apartmentCount +
                ", builtArea=" + builtArea +
                ", commonArea=" + commonArea +
                '}';
    }
}
