package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "pets")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Pet name is required")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Pet type is required")
    @Column(nullable = false)
    private String type;

    @NotNull(message = "Common areas usage flag is required")
    @Column(name = "uses_common_areas", nullable = false)
    private Boolean usesCommonAreas;

    @NotNull(message = "Apartment is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apartment_id", nullable = false)
    private Apartment apartment;

    public Pet() {
    }

    public Pet(String name, String type, Boolean usesCommonAreas, Apartment apartment) {
        this.name = name;
        this.type = type;
        this.usesCommonAreas = usesCommonAreas;
        this.apartment = apartment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getUsesCommonAreas() {
        return usesCommonAreas;
    }

    public void setUsesCommonAreas(Boolean usesCommonAreas) {
        this.usesCommonAreas = usesCommonAreas;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", usesCommonAreas=" + usesCommonAreas +
                '}';
    }
}
