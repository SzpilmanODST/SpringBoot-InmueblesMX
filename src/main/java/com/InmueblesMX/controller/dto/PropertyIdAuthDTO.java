package com.InmueblesMX.controller.dto;

import com.InmueblesMX.model.photo.Photo;
import com.InmueblesMX.model.property.PropertyTypeEnum;
import com.InmueblesMX.model.property.RentOrSellEnum;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PropertyIdAuthDTO {

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

    // User
    private Long userId;
    private String userName;
    private String userLastName;
    private String email;
    private String cellphone;

}
