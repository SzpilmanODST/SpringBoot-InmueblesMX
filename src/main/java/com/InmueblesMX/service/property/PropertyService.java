package com.InmueblesMX.service.property;

import com.InmueblesMX.model.property.PropertyEntity;

import java.util.List;
import java.util.Optional;

public interface PropertyService {

    List<PropertyEntity> findAll();

    Optional<PropertyEntity> findById(Long id);

    void saveProperty(PropertyEntity propertyEntity);

    void deleteById(Long id);

}
