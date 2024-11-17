package com.team5.bms.web.rest;

import com.team5.bms.model.Building;
import com.team5.bms.repository.BuildingRepository;
import com.team5.bms.service.BuildingService;
import ccom.team5.bms.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.team5.bms.web.util.HeaderUtil;
import com.team5.bms.web.util.PaginationUtil;
import com.team5.bms.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.team5.bms.model.Building}.
 *
 * @author
 */
@RestController
@RequestMapping("/api/buildings")
public class BuildingResource {

    private static final Logger LOG = LoggerFactory.getLogger(BuildingResource.class);

    private static final String ENTITY_NAME = "building";

    private String applicationName = "bmsApp";

    private final BuildingService buildingService;

    private final BuildingRepository buildingRepository;

    public BuildingResource(BuildingService buildingService, BuildingRepository buildingRepository) {
        this.buildingService = buildingService;
        this.buildingRepository = buildingRepository;
    }

    /**
     * {@code POST  /buildings} : Create a new building.
     *
     * @param building the building to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new building, or with status {@code 400 (Bad Request)} if the building has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Building> createBuilding(@Valid @RequestBody Building building) throws URISyntaxException {
		
        LOG.debug("REST request to save Building : {}", building);
        if (building.getId() != null) {
            throw new BadRequestAlertException("A new building cannot already have an ID", ENTITY_NAME, "idexists");
        }
        building = buildingService.save(building);
        return ResponseEntity.created(new URI("/api/buildings/" + building.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, building.getId().toString()))
            .body(building);
			
    }

    /**
     * {@code PUT  /buildings/:id} : Updates an existing building.
     *
     * @param id the id of the building to save.
     * @param building the building to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated building,
     * or with status {@code 400 (Bad Request)} if the building is not valid,
     * or with status {@code 500 (Internal Server Error)} if the building couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Building> updateBuilding(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Building building) throws URISyntaxException {
		
        LOG.debug("REST request to update Building : {}, {}", id, building);
        if (building.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, building.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!buildingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        building = buildingService.update(building);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, building.getId().toString()))
            .body(building);
			
    }

    /**
     * {@code PATCH  /buildings/:id} : Partial updates given fields of an existing building, field will ignore if it is null
     *
     * @param id the id of the building to save.
     * @param building the building to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated building,
     * or with status {@code 400 (Bad Request)} if the building is not valid,
     * or with status {@code 404 (Not Found)} if the building is not found,
     * or with status {@code 500 (Internal Server Error)} if the building couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Building> partialUpdateBuilding(@PathVariable(value = "id", required = false) final Long id, @NotNull @RequestBody Building building) throws URISyntaxException {
        
		LOG.debug("REST request to partial update Building partially : {}, {}", id, building);
        if (building.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, building.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!buildingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Building> result = buildingService.partialUpdate(building);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, building.getId().toString())
        );
		
    }

    /**
     * {@code GET  /buildings} : get all the buildings.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of buildings in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Building>> getAllBuildings(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
		
        LOG.debug("REST request to get a page of Buildings");
        Page<Building> page = buildingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
		
    }

    /**
     * {@code GET  /buildings/:id} : get the "id" building.
     *
     * @param id the id of the building to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the building, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Building> getBuilding(@PathVariable("id") Long id) {
		
        LOG.debug("REST request to get Building : {}", id);
        Optional<Building> building = buildingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(building);
		
    }

    /**
     * {@code DELETE  /buildings/:id} : delete the "id" building.
     *
     * @param id the id of the building to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBuilding(@PathVariable("id") Long id) {
		
        LOG.debug("REST request to delete Building : {}", id);
        buildingService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
			
    }
	
}
