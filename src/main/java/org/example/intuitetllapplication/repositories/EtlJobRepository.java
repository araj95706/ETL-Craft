package org.example.intuitetllapplication.repositories;

import org.example.intuitetllapplication.model.EtlJob;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EtlJobRepository extends JpaRepository<EtlJob, Long> {
}
