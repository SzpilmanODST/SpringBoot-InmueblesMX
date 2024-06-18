package com.InmueblesMX.model.property;

import com.InmueblesMX.model.photo.Photo;
import com.InmueblesMX.model.user.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "properties")
public class PropertyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String size;
    private int price;
    private String direction;
    private int rooms;
    private int bathrooms;
    private int parking;
    private LocalDate createAt;
    private String description;

    @OneToMany(mappedBy = "property",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Photo> photos = new ArrayList<>();

    @Column(name = "property_type")
    @Enumerated(EnumType.STRING)
    private PropertyTypeEnum propertyType;

    @Column(name = "rent_or_sell")
    @Enumerated(EnumType.STRING)
    private RentOrSellEnum rentOrSell;

    @ManyToOne
    @JoinColumn(name = "id_user")
    @JsonIgnore
    private UserEntity user;

}

