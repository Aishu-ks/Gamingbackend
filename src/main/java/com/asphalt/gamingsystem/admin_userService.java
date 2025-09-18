package com.asphalt.gamingsystem;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class admin_userService {
    private static final Logger log = LoggerFactory.getLogger(admin_userService.class);

    @Autowired
    private admin_usersRepository repo;

    // CREATE
    public admin_users create(admin_users adminUser) {
        log.info("Creating admin user: {}", adminUser.getUsername());
        adminUser.setId(null); // let MongoDB assign ObjectId
        validate(adminUser);

        // check unique username
        if (repo.findByUsername(adminUser.getUsername()).isPresent()) {
            log.error("Username already exists: {}", adminUser.getUsername());
            throw new BusinessException("Username already exists: " + adminUser.getUsername());
        }

        return repo.save(adminUser);
    }

    // READ all
    public List<admin_users> findAll() {
        log.info("Finding all admin users");
        return repo.findAll();
    }

    // READ by ID
    public admin_users findById(String id) {
        log.info("Finding admin user by id {}", id);
        Optional<admin_users> optionalAdmin = repo.findById(id);
        if (optionalAdmin.isEmpty()) {
            log.error("Attempted to find non-existing admin user id: {}", id);
            throw new ResourceNotFoundException("Admin user not found: " + id);
        }
        return optionalAdmin.get();
    }

    // UPDATE
    public admin_users update(String id, admin_users adminUser) {
        Optional<admin_users> optionalAdmin = repo.findById(id);
        if (optionalAdmin.isEmpty()) {
            log.error("Attempted to update non-existing admin user id: {}", id);
            throw new ResourceNotFoundException("Admin user not found: " + id);
        }

        log.info("Updating admin user by id {}", id);
        admin_users oldAdmin = optionalAdmin.get();

        // update fields
        if (adminUser.getUsername() != null && !adminUser.getUsername().equals(oldAdmin.getUsername())) {
            // check unique username
            if (repo.findByUsername(adminUser.getUsername()).isPresent()) {
                log.error("Username already exists: {}", adminUser.getUsername());
                throw new BusinessException("Username already exists: " + adminUser.getUsername());
            }
            oldAdmin.setUsername(adminUser.getUsername());
        }

        if (adminUser.getPassword() != null && !adminUser.getPassword().trim().isEmpty()) {
            oldAdmin.setPassword(adminUser.getPassword());
        }

        validate(oldAdmin);
        return repo.save(oldAdmin);
    }

    // DELETE
    public boolean delete(String id) {
        Optional<admin_users> optionalAdmin = repo.findById(id);
        if (optionalAdmin.isEmpty()) {
            log.error("Attempted to delete non-existing admin user id: {}", id);
            throw new ResourceNotFoundException("Admin user not found: " + id);
        }
        log.info("Deleting admin user by id {}", id);
        repo.deleteById(id);
        return true;
    }

    // VALIDATION
    private void validate(admin_users adminUser) {
        if (adminUser.getUsername() == null || adminUser.getUsername().trim().isEmpty()) {
            log.error("Username cannot be null or empty");
            throw new BusinessException("Username cannot be null or empty");
        }
        if (adminUser.getPassword() == null || adminUser.getPassword().trim().isEmpty()) {
            log.error("Password cannot be null or empty");
            throw new BusinessException("Password cannot be null or empty");
        }
    }
}
