package com.InmueblesMX.controller;

import com.InmueblesMX.controller.dto.PhotoDTO;
import com.InmueblesMX.model.photo.Photo;
import com.InmueblesMX.model.property.PropertyEntity;
import com.InmueblesMX.model.user.UserEntity;
import com.InmueblesMX.service.photo.PhotoService;
import com.InmueblesMX.service.property.PropertyService;
import com.InmueblesMX.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/photos")
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private UserService userService;

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll() {
        List<PhotoDTO> photoList = photoService.findAll()
                .stream()
                .map(photo -> PhotoDTO.builder()
                        .id(photo.getId())
                        .fileName(photo.getFileName())
                        .property(photo.getProperty())
                        .build())
                .toList();

        return ResponseEntity.ok(photoList);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Photo> photoOptional = photoService.findById(id);

        if(photoOptional.isPresent()) {
            Photo photoDb = photoOptional.get();

            PhotoDTO photoDTO = PhotoDTO.builder()
                    .id(photoDb.getId())
                    .fileName(photoDb.getFileName())
                    .property(photoDb.getProperty())
                    .build();

            return ResponseEntity.ok(photoDTO);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/view/{photoName}")
    public ResponseEntity<?> viewPhoto(@PathVariable String photoName) {
        Path filePath = Paths.get("uploads").resolve(photoName).toAbsolutePath();
        Resource resource = null;

        try {
            resource = new UrlResource(filePath.toUri());

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);

        }

        if(!resource.exists() && !resource.isReadable()) {
            throw new RuntimeException("Error, no se pudo cargar la foto");
        }

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");

        return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);

    }

    @PostMapping("/save")
    public ResponseEntity<?> savePhoto(@RequestParam("file") MultipartFile file, @RequestParam("id") Long id, Authentication authentication) {

        Optional<PropertyEntity> propertyOptional = propertyService.findById(id);
        Optional<UserEntity> userOptional = userService.findById(propertyOptional.get().getUser().getId());

        String currentUsername = authentication.getName();
        UserEntity currentUser = userService.findByUsername(currentUsername);

        if(!file.isEmpty()) {
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename().replace(" ", "");
            Path filePath = Paths.get("uploads").resolve(fileName).toAbsolutePath();

            try {
                Files.copy(file.getInputStream(), filePath);

            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());

            }

            if(propertyOptional.isPresent() && currentUser.getId().equals(userOptional.get().getId())) {

                Photo photo = Photo.builder()
                        .fileName(fileName)
                        .property(propertyOptional.get())
                        .build();

                photoService.save(photo);

                return ResponseEntity.status(HttpStatus.CREATED).build();
            }

            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id, Authentication authentication) {
        Optional<Photo> optionalPhoto = photoService.findById(id);
        Optional<UserEntity> optionalUser = userService.findById(optionalPhoto.get().getProperty().getUser().getId());

        String currentUsername = authentication.getName();
        UserEntity currentUser = userService.findByUsername(currentUsername);

        boolean hasAdminRole = authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));

        if(optionalPhoto.isPresent() && currentUser.getId().equals(optionalUser.get().getId()) || hasAdminRole) {
            String photoName = optionalPhoto.get().getFileName();

            Path photoPath = Paths.get("uploads").resolve(photoName).toAbsolutePath();
            File photoFile = photoPath.toFile();

            photoFile.delete();
            photoService.deleteById(id);

            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.notFound().build();
    }
}

