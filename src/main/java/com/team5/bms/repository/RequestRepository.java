package com.team5.bms.repository;
 
import com.team5.bms.model.Request;
import com.team5.bms.model.User;
import com.team5.bms.model.enumeration.Roles;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
 
/**
* Spring Data JPA repository for the Request entity.
*
* @author Jophiel John Serrano
* @author Leandro Mananquil "The Project Manager"
* @author Jasper Belenzo
*
*/
@SuppressWarnings("unused")
@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
	
    @Query("SELECT r FROM Request r WHERE r.user.building.id = :buildingId AND r.assigneeId = :assigneeId")
    List<Request> findByBuildingIdAndAssigneeId(Long buildingId, Long assigneeId);

    @Query("SELECT r FROM Request r WHERE r.user.building.id = :buildingId AND r.user.id = :userId")
    List<Request> findByBuildingIdAndUserId(Long buildingId, Long userId);

    @Query("SELECT r FROM Request r WHERE r.user.building.id = :buildingId")
    List<Request> findByBuildingId(Long buildingId);

}