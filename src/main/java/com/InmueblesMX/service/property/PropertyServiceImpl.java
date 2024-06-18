package com.InmueblesMX.service.property;

import com.InmueblesMX.model.property.PropertyEntity;
import com.InmueblesMX.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    private PropertyRepository repository;

    @Override
    public List<PropertyEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<PropertyEntity> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void saveProperty(PropertyEntity propertyEntity) {
        repository.save(propertyEntity);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

}
