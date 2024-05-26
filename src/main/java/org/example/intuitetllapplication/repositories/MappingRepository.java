package org.example.intuitetllapplication.repositories;

import org.example.intuitetllapplication.model.Mapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MappingRepository extends JpaRepository<Mapping, Long> {
}
