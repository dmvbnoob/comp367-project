package com.team5.bms.repository;

import com.team5.bms.model.Building;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Building entity.
 *
 * @author Jasper "Principal Engineer and Software Architect" Belenzo 
 */
@SuppressWarnings("unused")
@Repository
public interface BuildingRepository extends JpaRepository<Building, Long> {}