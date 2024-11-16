package com.team5.bms.repository;
 
import com.team5.bms.model.Request;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
 
/**
* Spring Data JPA repository for the Request entity.
*
* @author Jophiel John "FullStack ChatGPT Summoner" Serrano
* @author Leandro Mananquil "The Project Manager"
*/
@SuppressWarnings("unused")
@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {}