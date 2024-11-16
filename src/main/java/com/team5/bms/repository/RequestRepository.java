package com.team5.bms.repository;
 
import com.team5.bms.Request;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
 
/**
* Spring Data JPA repository for the Request entity.
*
* @author Jophiel John "FullStack ChatGPT Summoner" Serrano
*/
@SuppressWarnings("unused")
@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {