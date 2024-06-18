package com.InmueblesMX.service.photo;

import com.InmueblesMX.model.photo.Photo;

import java.util.List;
import java.util.Optional;

public interface PhotoService {

    List<Photo> findAll();

    Optional<Photo> findById(Long id);

    void save(Photo photo);

    void deleteById(Long id);

}
