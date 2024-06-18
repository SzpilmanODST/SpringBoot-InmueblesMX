package com.InmueblesMX.controller.dto;

import com.InmueblesMX.model.photo.Photo;
import com.InmueblesMX.model.property.PropertyTypeEnum;
import com.InmueblesMX.model.property.RentOrSellEnum;
import com.InmueblesMX.model.user.UserEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PropertyDTO {

    private Long id;

    @NotBlank
    private String size;

    @NotNull
    private int price;

    @NotBlank
    private String direction;

    private int rooms;

    private int bathrooms;

    private int parking;

    private LocalDate createAt;

    @NotBlank
    private String description;

    private List<Photo> photos;

    @NotNull
    private PropertyTypeEnum propertyType;

    @NotNull
    private RentOrSellEnum rentOrSell;

}
