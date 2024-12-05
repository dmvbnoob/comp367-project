package com.team5.bms.repository;
 
import com.team5.bms.model.Request;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
 
/**
* Spring Data JPA repository for the Request entity.
*
* @author Jophiel John "FullStack ChatGPT Summoner" Serrano
* @author Leandro Mananquil "The Project Manager"
* @author Jasper Belenzo
*
*/
@SuppressWarnings("unused")
@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    @Query("SELECT r FROM Request r WHERE r.user.building.id = :buildingId AND r.user.id = :userId")
    List<Request> findByBuildingIdAndUserId(Long buildingId, Long userId);

    @Query("SELECT r FROM Request r WHERE r.user.building.id = :buildingId")
    List<Request> findByBuildingId(Long buildingId);

}