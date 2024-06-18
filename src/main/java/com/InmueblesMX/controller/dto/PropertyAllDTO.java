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
public class PropertyAllDTO {

    private Long id;
    private int price;
    private LocalDate createAt;
    private List<Photo> photos;
    private PropertyTypeEnum propertyType;
    private RentOrSellEnum rentOrSell;

}
