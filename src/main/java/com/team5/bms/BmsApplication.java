package com.team5.bms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.team5.bms.repository.*;
import com.team5.bms.model.*;
import com.team5.bms.model.enumeration.Priorities;
import com.team5.bms.model.enumeration.Roles;
import com.team5.bms.model.enumeration.Statuses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import java.time.Instant;
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
	
	@Autowired
	private RequestRepository requestRepository;

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

		// Add ALICIA SINGCA Building Owner
		User buildingOwner = new User();
		buildingOwner.setUsername("AliciaSingca");
		buildingOwner.setPassword("password");
		buildingOwner.setEmail("alicia@sing.ca");
		buildingOwner.setFirstname("Alicia");
		buildingOwner.setLastname("Singca");
		buildingOwner.setUserPhone("+12345678901");
		buildingOwner.setRole(Roles.OWNER);
		buildingOwner.setBuilding(team5Tower);
		userRepository.save(buildingOwner);
		Optional<User> optionalBuildingOwner = userRepository.findById(Long.valueOf(1L));
		System.out.println("BmsApplication - run - buildingOwner - id -> " + optionalBuildingOwner.get().getId());

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

		// Add DONALDA ZHUGA Building Administrator
		User buildingAdministrator = new User();
		buildingAdministrator.setUsername("DonaldaZhuga");
		buildingAdministrator.setPassword("password");
		buildingAdministrator.setEmail("donalda@zhu.ga");
		buildingAdministrator.setFirstname("Donalda");
		buildingAdministrator.setLastname("Zhuga");
		buildingAdministrator.setUserPhone("+19876543210");
		buildingAdministrator.setRole(Roles.ADMINISTRATOR);
		buildingAdministrator.setBuilding(team5Tower);
		userRepository.save(buildingAdministrator);
		Optional<User> optionalBuildingAdministrator = userRepository.findById(Long.valueOf(2L));
		System.out.println("BmsApplication - run - buildingAdministrator - id -> " + optionalBuildingAdministrator.get().getId());

		// Add STEPHANIE SANTOS Tenant
		User tenantUser = new User();
		tenantUser.setUsername("StepanieSantos");
		tenantUser.setPassword("password");
		tenantUser.setEmail("santos@stepha.nie");
		tenantUser.setFirstname("Stephanie");
		tenantUser.setLastname("Santos");
		tenantUser.setUserPhone("+12345678910");
		tenantUser.setRole(Roles.TENANT);
		tenantUser.setBuilding(team5Tower);
		userRepository.save(tenantUser);
		Optional<User> optionalTenantUser = userRepository.findById(Long.valueOf(3L));
		System.out.println("BmsApplication - run - tenantUser - id -> " + optionalTenantUser.get().getId());

		// Add JOPHIEL SERRANO SuperIntendent
		User superIntendent = new User();
		superIntendent.setUsername("JophielSerrano");
		superIntendent.setPassword("password");
		superIntendent.setEmail("jophiel@serr.ano");
		superIntendent.setFirstname("Jophiel");
		superIntendent.setLastname("Serrano");
		superIntendent.setUserPhone("+19876543219");
		superIntendent.setRole(Roles.SUPERINTENDENT);
		superIntendent.setBuilding(team5Tower);
		userRepository.save(superIntendent);
		Optional<User> optionalSuperIntendent = userRepository.findById(Long.valueOf(4L));
		System.out.println("BmsApplication - run - superIntendent - id -> " + optionalSuperIntendent.get().getId());

		// Add LEANDRO MANANQUIL Tenant
		User tenantUser2 = new User();
		tenantUser2.setUsername("LeandroMananquil");
		tenantUser2.setPassword("password");
		tenantUser2.setEmail("mananquil@lean.dro");
		tenantUser2.setFirstname("Leandro");
		tenantUser2.setLastname("Mananquil");
		tenantUser2.setUserPhone("+13876543213");
		tenantUser2.setRole(Roles.TENANT);
		tenantUser2.setBuilding(team5Tower);
		userRepository.save(tenantUser2);
		Optional<User> optionalTenantUser2 = userRepository.findById(Long.valueOf(5L));
		System.out.println("BmsApplication - run - tenantUser2 - id -> " + optionalTenantUser2.get().getId());

		// Add JASPER BELENZO SuperIntendent
		User superIntendent2 = new User();
		superIntendent2.setUsername("JasperBelenzo");
		superIntendent2.setPassword("password");
		superIntendent2.setEmail("jasper@bele.nzo");
		superIntendent2.setFirstname("Jasper");
		superIntendent2.setLastname("Belenzo");
		superIntendent2.setUserPhone("+19876587219");
		superIntendent2.setRole(Roles.SUPERINTENDENT);
		superIntendent2.setBuilding(team5Tower);
		userRepository.save(superIntendent2);
		Optional<User> optionalSuperIntendent2 = userRepository.findById(Long.valueOf(6L));
		System.out.println("BmsApplication - run - superIntendent2 - id -> " + optionalSuperIntendent2.get().getId());

		// TODOs Create Requests
		Request request1 = new Request();
		request1.setUser(tenantUser2);
		request1.setTitle("Kitchen Sink Repair");
		request1.setDescription("Kitchen Sink needs repair as soon as possible. Water is leaking.");
		request1.setCreateDate(Instant.now());
		request1.setStatus(Statuses.CREATED);
		request1.setPriority(Priorities.LOW);
		requestRepository.save(request1);
		Optional<Request> optionalRequest1 = requestRepository.findById(Long.valueOf(1L));
		System.out.println("BmsApplication - run - tenantUser1 - request1 - id -> " + optionalRequest1.get().getId());
	
	}

}
