package com.jeremy.h2.web.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.jeremy.h2.web.entity.Users;

@RepositoryRestResource
public interface UserService extends JpaRepository<Users, Long> {
    @Override
    Users getOne(Long id);

    @Override
    Page<Users> findAll(Pageable pageable);

    @Override
    Users save(Users s);

    // Prevents DELETE /books/:id
    @Override
    @RestResource(exported = false)
    void delete(Users t);
}
