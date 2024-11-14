package com.team5.bms.repository;

import com.team5.bms.model.Card;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Card entity.
 *
 * @author Jasper "Principal Engineer and Software Architect" Belenzo
 */
@SuppressWarnings("unused")
@Repository
public interface CardRepository extends JpaRepository<Card, Long> {}