package com.team5.bms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.team5.bms.repository.*;
import com.team5.bms.model.*;
import com.team5.bms.model.enumeration.Roles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import java.util.Optional;

@SpringBootApplication
public class BmsApplication implements CommandLineRunner {
	
	private static final Logger logger = LoggerFactory.getLogger(BmsApplication.class);

	@Autowired
	private BuildingRepository buildingRepository;

	@Autowired
	private CardRepository cardRepository;
	
	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		logger.info("Hello BMS!");
		SpringApplication.run(BmsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		// Add TEAM 5 TOWER Building
		Building team5Tower = new Building();
		team5Tower.setBuildingName("Team 5 Tower");
		team5Tower.setAddress("300 Borough Drive, Scarborough, ON, M1P 4P5");
		team5Tower.setBuildingPhone("+19876543210");
		buildingRepository.save(team5Tower);
		Optional<Building> optionalTeam5Tower = buildingRepository.findById(Long.valueOf(1L));
		System.out.println("BmsApplication - run - team5Tower - id -> " + optionalTeam5Tower.get().getId());

		User buildingOwner = new User();
		buildingOwner.setUsername("AliciaSingca");
		buildingOwner.setPassword("password");
		buildingOwner.setEmail("alicia@sing.ca");
		buildingOwner.setFirstname("Alicia");
		buildingOwner.setLastname("Singca");
		buildingOwner.setRole(Roles.OWNER);
		buildingOwner.setBuilding(team5Tower);
		userRepository.save(buildingOwner);
		Optional<User> optionalBuildingOwner = userRepository.findById(Long.valueOf(1L));
		System.out.println("BmsApplication - run - optionalBuildingOwner - id -> " + optionalBuildingOwner.get().getId());

		// Add Credit Card of Building Owner Alicia Singca
		Card cardOfBuildingOwner = new Card();
		cardOfBuildingOwner.setCardName("Alicia Singca");
		cardOfBuildingOwner.setExpiry("2099-12");
		cardOfBuildingOwner.setCvv("777");
		cardOfBuildingOwner.setNumber("4321567890321");
		cardOfBuildingOwner.setUser(buildingOwner);
		cardRepository.save(cardOfBuildingOwner);
		Optional<Card> optionalCardOfBuildingOwner = cardRepository.findById(Long.valueOf(1L));
		System.out.println("BmsApplication - run - cardOfBuildingOwner - id -> " + optionalCardOfBuildingOwner.get().getId());
	
	}

}
