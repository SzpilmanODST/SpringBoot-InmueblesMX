package com.InmueblesMX.controller.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record AuthCreateUserRequest(@NotBlank String username,
                                    @NotBlank String password,
                                    @Valid AuthCreateRoleRequest roleRequest,
                                    @NotBlank String name,
                                    @NotBlank String lastName,
                                    @NotBlank String email,
                                    @NotBlank String cellphone) {
}
