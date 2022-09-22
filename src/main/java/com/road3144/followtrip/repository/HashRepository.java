package com.road3144.followtrip.repository;

import com.road3144.followtrip.domain.Hash;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HashRepository extends JpaRepository<Hash, Long> {
    Optional<Hash> findByName(String name);

}
