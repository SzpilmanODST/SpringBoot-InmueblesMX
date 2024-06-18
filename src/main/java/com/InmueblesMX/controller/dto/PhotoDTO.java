package com.InmueblesMX.controller.dto;

import com.InmueblesMX.model.property.PropertyEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhotoDTO {

    private Long id;

    @NotBlank
    private String fileName;

    @NotBlank
    private PropertyEntity property;

}
