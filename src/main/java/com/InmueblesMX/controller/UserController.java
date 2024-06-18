package com.InmueblesMX.controller;

import com.InmueblesMX.controller.dto.UserDTO;
import com.InmueblesMX.model.user.UserEntity;
import com.InmueblesMX.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll() {
        List<UserDTO> userDTOList = userService.findAll()
                .stream()
                .map(user -> UserDTO.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .name(user.getName())
                        .lastName(user.getLastName())
                        .cellphone(user.getCellphone())
                        .email(user.getEmail())
                        .roles(user.getRoles())
                        .properties(user.getProperties())
                        .build())
                .toList();

        return ResponseEntity.ok(userDTOList);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<UserEntity> userOptional = userService.findById(id);

        if(userOptional.isPresent()) {
            UserEntity userEntity = userOptional.get();

            UserDTO userDTO = UserDTO.builder()
                    .id(userEntity.getId())
                    .username(userEntity.getUsername())
                    .name(userEntity.getName())
                    .lastName(userEntity.getLastName())
                    .cellphone(userEntity.getCellphone())
                    .email(userEntity.getEmail())
                    .roles(userEntity.getRoles())
                    .properties(userEntity.getProperties())
                    .build();

            return ResponseEntity.ok(userDTO);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO, Authentication authentication) {
        Optional<UserEntity> userOptional = userService.findById(id);
        String currentUsername = authentication.getName();

        UserEntity curretUser = userService.findByUsername(currentUsername);

        if(userOptional.isPresent() && curretUser.getId().equals(id)) {
            UserEntity userEntityDb = userOptional.get();

            userEntityDb.setName(userDTO.getName());
            userEntityDb.setLastName(userDTO.getLastName());
            userEntityDb.setCellphone(userDTO.getCellphone());
            userEntityDb.setEmail(userDTO.getEmail());

            userService.save(userEntityDb);

            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id, Authentication authentication) {
        Optional<UserEntity> userOptional = userService.findById(id);
        String currentUsername = authentication.getName();

        UserEntity currentUser = userService.findByUsername(currentUsername);

        boolean hasAdminRole = authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));

        if(userOptional.isPresent() && currentUser.getId().equals(id) || hasAdminRole) {
            userService.deleteById(id);

            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}

