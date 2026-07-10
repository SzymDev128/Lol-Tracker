package com.example.loltracker.repository;

import com.example.loltracker.entity.RankEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RankEntityRepository extends JpaRepository<RankEntity, Long> {
    void deleteByPlayerId(Long playerId);
}
