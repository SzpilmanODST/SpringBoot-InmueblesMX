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
public class PropertyIdDTO {

    private Long id;
    private String size;
    private int price;
    private String direction;
    private int rooms;
    private int bathrooms;
    private int parking;
    private LocalDate createAt;
    private String description;
    private List<Photo> photos;
    private PropertyTypeEnum propertyType;
    private RentOrSellEnum rentOrSell;

}
