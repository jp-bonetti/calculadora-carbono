package com.calculadora_carbono.backend.repositories;

import com.calculadora_carbono.backend.entities.EmissionActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmissionActivityRepository extends JpaRepository<EmissionActivity, Long> {

    List<EmissionActivity> findByUsersId(Long usersId);

    List<EmissionActivity> findByUsersIdAndCategoryId(Long usersId, Long categoryId);
}
