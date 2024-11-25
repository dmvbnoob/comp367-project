package com.team5.bms.service.impl;
 
import com.team5.bms.model.User;
import com.team5.bms.repository.UserRepository;
import com.team5.bms.service.UserService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
/**
* Service Implementation for managing {@link com.team5.bms.model.User}.
*
* @author Leandro "The Project Manager" Mananquil
* @author Jasper Belenzo
*/
@Service
@Transactional
public class UserServiceImpl implements UserService {
 
    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);
 
    private final UserRepository userRepository;
 
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
 
    @Override
    public User save(User user) {
        LOG.debug("Request to save User : {}", user);
        return userRepository.save(user);
    }
 
    @Override
    public User update(User user) {
        LOG.debug("Request to update User : {}", user);
        return userRepository.save(user);
    }
 
    @Override
    public Optional<User> partialUpdate(User user) {
        LOG.debug("Request to partially update User : {}", user);
 
        return userRepository
            .findById(user.getId())
            .map(existingUser -> {
                if (user.getUsername() != null) {
                    existingUser.setUsername(user.getUsername());
                }
                if (user.getPassword() != null) {
                    existingUser.setPassword(user.getPassword());
                }
                if (user.getFirstname() != null) {
                    existingUser.setFirstname(user.getFirstname());
                }
                if (user.getLastname() != null) {
                    existingUser.setLastname(user.getLastname());
                }
                if (user.getEmail() != null) {
                    existingUser.setEmail(user.getEmail());
                }
                if (user.getUserPhone() != null) {
                    existingUser.setUserPhone(user.getUserPhone());
                }
                if (user.getRole() != null) {
                    existingUser.setRole(user.getRole());
                }
 
                return existingUser;
            })
            .map(userRepository::save);
    }
 
    @Override
    @Transactional(readOnly = true)
    public Page<User> findAll(Pageable pageable) {
        LOG.debug("Request to get all Users");
        return userRepository.findAll(pageable);
    }
 
    @Override
    @Transactional(readOnly = true)
    public Optional<User> findOne(Long id) {
        LOG.debug("Request to get User : {}", id);
        return userRepository.findById(id);
    }
 
    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete User : {}", id);
        userRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByUsernameAndPassword(String username, String password) {
        LOG.debug("Request to find User by username and password : {} / {}", username, password);
        return userRepository.findByUsernameAndPassword(username, password);
    }
}