package com.InmueblesMX.service.user;

import com.InmueblesMX.model.user.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserEntity> findAll();

    Optional<UserEntity> findById(Long id);

    UserEntity findByUsername(String username);

    void save(UserEntity userEntity);

    void deleteById(Long id);
}
