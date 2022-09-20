package com.road3144.followtrip.repository;

import com.road3144.followtrip.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
