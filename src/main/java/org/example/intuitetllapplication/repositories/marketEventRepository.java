package org.example.intuitetllapplication.repositories;

import org.example.intuitetllapplication.model.MarketEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface marketEventRepository extends JpaRepository<MarketEvent, Long> {
}
