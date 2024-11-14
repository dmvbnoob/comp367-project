package com.team5.bms.repository;

import com.team5.bms.model.Building;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
* Spring Data JPA repository for the Request entity.
*
* @author Alicia "Product Owner, Subject Matter Expert (SME), Software Quality" Singca
* @author Jasper "Principal Engineer and Software Architect" Belenzo
*/
@SuppressWarnings("unused")
@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {}