package com.team5.bms.service.impl;

import com.team5.bms.model.Building;
import com.team5.bms.repository.BuildingRepository;
import com.team5.bms.service.BuildingService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.team5.bms.model.Building}.
 *
 * @author Jasper Belenzo
 */
@Service
@Transactional
public class BuildingServiceImpl implements BuildingService {

    private static final Logger LOG = LoggerFactory.getLogger(BuildingServiceImpl.class);

    private final BuildingRepository buildingRepository;

    public BuildingServiceImpl(BuildingRepository buildingRepository) {
        this.buildingRepository = buildingRepository;
    }

    @Override
    public Building save(Building building) {
        LOG.debug("Request to save Building : {}", building);
        return buildingRepository.save(building);
    }

    @Override
    public Building update(Building building) {
        LOG.debug("Request to update Building : {}", building);
        return buildingRepository.save(building);
    }

    @Override
    public Optional<Building> partialUpdate(Building building) {
        LOG.debug("Request to partially update Building : {}", building);

        return buildingRepository
            .findById(building.getId())
            .map(existingBuilding -> {
                if (building.getBuildingName() != null) {
                    existingBuilding.setBuildingName(building.getBuildingName());
                }
                if (building.getAddress() != null) {
                    existingBuilding.setAddress(building.getAddress());
                }
                if (building.getBuildingPhone() != null) {
                    existingBuilding.setBuildingPhone(building.getBuildingPhone());
                }

                return existingBuilding;
            })
            .map(buildingRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Building> findAll(Pageable pageable) {
        LOG.debug("Request to get all Buildings");
        return buildingRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Building> findOne(Long id) {
        LOG.debug("Request to get Building : {}", id);
        return buildingRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Building : {}", id);
        buildingRepository.deleteById(id);
    }
	
}
