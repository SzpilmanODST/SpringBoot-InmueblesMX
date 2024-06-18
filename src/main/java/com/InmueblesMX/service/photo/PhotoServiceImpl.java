package com.InmueblesMX.service.photo;

import com.InmueblesMX.model.photo.Photo;
import com.InmueblesMX.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhotoServiceImpl implements PhotoService {

    @Autowired
    private PhotoRepository repository;

    @Override
    public List<Photo> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Photo> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void save(Photo photo) {
        repository.save(photo);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
