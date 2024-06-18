package com.InmueblesMX.controller.dto;

import com.InmueblesMX.model.property.PropertyEntity;
import com.InmueblesMX.model.user.RoleEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    private Long id;

    @NotBlank
    private String username;

    @NotBlank
    private String name;

    @NotBlank
    private String lastName;

    @NotBlank
    private String cellphone;

    @NotBlank
    private String email;

    private Set<RoleEntity> roles;

    private List<PropertyEntity> properties;

}
