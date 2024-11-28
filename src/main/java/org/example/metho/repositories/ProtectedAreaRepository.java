package org.example.metho.repositories;

import org.example.metho.models.ProtectedArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProtectedAreaRepository extends JpaRepository<ProtectedArea, Integer> {
    List<ProtectedArea> findAll();
}
