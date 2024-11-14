package com.team5.bms.service;
 
import com.team5.bms.model.Building;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
 
/**
* Service Interface for managing {@link com.team5.bms.model.Building}.
*
* @author Stephanie "Business Analysts, Scrum Master, Software Quality Manager" Santos
*/
public interface BuildingService {
    /**
     * Save a building.
     *
     * @param building the entity to save.
     * @return the persisted entity.
     */
    Building save(Building building);
 
    /**
     * Updates a building.
     *
     * @param building the entity to update.
     * @return the persisted entity.
     */
    Building update(Building building);
 
    /**
     * Partially updates a building.
     *
     * @param building the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Building> partialUpdate(Building building);
 
    /**
     * Get all the buildings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Building> findAll(Pageable pageable);
 
    /**
     * Get the "id" building.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Building> findOne(Long id);
 
    /**
     * Delete the "id" building.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}