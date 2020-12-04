package com.example.ibs.repositories;

import com.example.ibs.entities.DocumentSide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentSideRepository extends JpaRepository<DocumentSide, Long> {
}
