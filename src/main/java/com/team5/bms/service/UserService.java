package com.team5.bms.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.team5.bms.model.User;

/**
* Service Interface for managing {@link com.team5.bms.domain.User}.
*
* @author Leandro "The Project Manager" Mananquil
*/

public interface UserService {
	
    /**
     * Save a user.
     *
     * @param user the entity to save.
     * @return the persisted entity.
     */
    User save(User user);
 
    /**
     * Updates a User.
     *
     * @param user the entity to update.
     * @return the persisted entity.
     */
    User update(User user);
 
    /**
     * Partially updates a user.
     *
     * @param user the entity to update partially.
     * @return the persisted entity.
     */
    Optional<User> partialUpdate(User user);
 
    /**
     * Get all the users.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<User> findAll(Pageable pageable);
 
    /**
     * Get the "id" user.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<User> findOne(Long id);
 
    /**
     * Delete the "id" user.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);	

    /**
     * Get the "username" and "password" user.
     *
     * @param username the username of the entity.
     * @param password the password of the entity.
     * @return the entity.
     */
    Optional<User> findByUsernameAndPassword(String username, String password);

}
