package com.calculadora_carbono.backend.repositories;

import com.calculadora_carbono.backend.entities.Category;
import com.calculadora_carbono.backend.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByNameAndUsers(String name, Users users);

    Optional<Category> findByIdAndUsers(Long id, Users users);

    List<Category> findByUsersId(Long usersId);

}
