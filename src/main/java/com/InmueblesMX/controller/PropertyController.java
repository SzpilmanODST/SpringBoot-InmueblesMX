package com.InmueblesMX.controller;

import com.InmueblesMX.controller.dto.PropertyAllDTO;
import com.InmueblesMX.controller.dto.PropertyDTO;
import com.InmueblesMX.controller.dto.PropertyIdAuthDTO;
import com.InmueblesMX.controller.dto.PropertyIdDTO;
import com.InmueblesMX.model.property.PropertyEntity;
import com.InmueblesMX.model.user.UserEntity;
import com.InmueblesMX.repository.PropertyRepository;
import com.InmueblesMX.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/properties")
public class PropertyController {

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll() {
        List<PropertyAllDTO> propertyList = propertyRepository.findAll()
                .stream()
                .map(property -> PropertyAllDTO.builder()
                        .id(property.getId())
                        .price(property.getPrice())
                        .createAt(property.getCreateAt())
                        .photos(property.getPhotos())
                        .propertyType(property.getPropertyType())
                        .rentOrSell(property.getRentOrSell())
                        .build())
                .toList();

        return ResponseEntity.ok(propertyList);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        Optional<PropertyEntity> propertyOptional = propertyRepository.findById(id);

        if(propertyOptional.isPresent()) {
            PropertyEntity propertyDb = propertyOptional.get();
            PropertyIdDTO propertyIdDTO = PropertyIdDTO.builder()
                    .id(propertyDb.getId())
                    .size(propertyDb.getSize())
                    .price(propertyDb.getPrice())
                    .direction(propertyDb.getDirection())
                    .rooms(propertyDb.getRooms())
                    .bathrooms(propertyDb.getBathrooms())
                    .parking(propertyDb.getParking())
                    .createAt(propertyDb.getCreateAt())
                    .description(propertyDb.getDescription())
                    .photos(propertyDb.getPhotos())
                    .propertyType(propertyDb.getPropertyType())
                    .rentOrSell(propertyDb.getRentOrSell())
                    .build();

            return ResponseEntity.ok(propertyIdDTO);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/find/{id}/auth")
    public ResponseEntity findByIdAuth(@PathVariable Long id) {
        Optional<PropertyEntity> propertyOptional = propertyRepository.findById(id);

        if(propertyOptional.isPresent()) {
            PropertyEntity propertyDb = propertyOptional.get();
            PropertyIdAuthDTO propertyIdAuthDTO = PropertyIdAuthDTO.builder()
                    .id(propertyDb.getId())
                    .size(propertyDb.getSize())
                    .price(propertyDb.getPrice())
                    .direction(propertyDb.getDirection())
                    .rooms(propertyDb.getRooms())
                    .bathrooms(propertyDb.getBathrooms())
                    .parking(propertyDb.getParking())
                    .createAt(propertyDb.getCreateAt())
                    .description(propertyDb.getDescription())
                    .photos(propertyDb.getPhotos())
                    .propertyType(propertyDb.getPropertyType())
                    .rentOrSell(propertyDb.getRentOrSell())
                    .userId(propertyDb.getUser().getId())
                    .userName(propertyDb.getUser().getName())
                    .userLastName(propertyDb.getUser().getLastName())
                    .email(propertyDb.getUser().getEmail())
                    .cellphone(propertyDb.getUser().getCellphone())
                    .build();

            return ResponseEntity.ok(propertyIdAuthDTO);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveProperty(@RequestBody @Valid PropertyDTO propertyDTO, Authentication authentication) {
        String currentUsername = authentication.getName();
        UserEntity currentUser = userService.findByUsername(currentUsername);

        PropertyEntity property = PropertyEntity.builder()
                .size(propertyDTO.getSize())
                .price(propertyDTO.getPrice())
                .direction(propertyDTO.getDirection())
                .rooms(propertyDTO.getRooms())
                .bathrooms(propertyDTO.getBathrooms())
                .parking(propertyDTO.getParking())
                .createAt(LocalDate.now())
                .description(propertyDTO.getDescription())
                .photos(propertyDTO.getPhotos())
                .propertyType(propertyDTO.getPropertyType())
                .rentOrSell(propertyDTO.getRentOrSell())
                .user(currentUser)
                .build();

        propertyRepository.save(property);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProperty(@PathVariable Long id, @RequestBody PropertyDTO propertyDTO, Authentication authentication) {
        Optional<PropertyEntity> propertyOptional = propertyRepository.findById(id);

        String currentUsername = authentication.getName();
        UserEntity currentUser = userService.findByUsername(currentUsername);

        if(propertyOptional.isPresent() && currentUser.getId().equals(propertyOptional.get().getUser().getId())) {
            PropertyEntity propertyDb = propertyOptional.get();

            propertyDb.setSize(propertyDTO.getSize());
            propertyDb.setPrice(propertyDTO.getPrice());
            propertyDb.setDirection(propertyDTO.getDirection());
            propertyDb.setRooms(propertyDTO.getRooms());
            propertyDb.setBathrooms(propertyDTO.getBathrooms());
            propertyDb.setParking(propertyDTO.getParking());
            propertyDb.setDescription(propertyDTO.getDescription());
            propertyDb.setPropertyType(propertyDTO.getPropertyType());
            propertyDb.setRentOrSell(propertyDTO.getRentOrSell());

            propertyRepository.save(propertyDb);

            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id, Authentication authentication) {
        Optional<PropertyEntity> propertyOptional = propertyRepository.findById(id);

        String currentUsername = authentication.getName();
        UserEntity currentUser = userService.findByUsername(currentUsername);

        boolean hasAdminRole = authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));

        if(propertyOptional.isPresent() && currentUser.getId().equals(propertyOptional.get().getUser().getId()) || hasAdminRole) {
            propertyRepository.deleteById(id);

            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.notFound().build();
    }

}
