package com.asphalt.gamingsystem;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/admin_users")
public class admin_usersControllers {

    @Autowired
    private admin_usersRepository repo;

    @PostMapping
    public admin_users create(@RequestBody admin_users adminUsers) {
        adminUsers.setId(null);
        admin_users savedAdmin = repo.save(adminUsers);
        return savedAdmin;
    }

    @GetMapping
    public List<admin_users> findAll() {
        return repo.findAll();
    }

    @GetMapping(path="/{id}")
    public admin_users findById(@PathVariable String id) {
        return repo.findById(id).orElse(null);
    }

    @PutMapping(path="/{id}")
    public admin_users update(@PathVariable String id, @RequestBody admin_users adminUsers) {
        admin_users oldAdmin = repo.findById(id).orElse(null);
        if (oldAdmin != null) {
            oldAdmin.setUsername(adminUsers.getUsername());
            oldAdmin.setPassword(adminUsers.getPassword());
            return repo.save(oldAdmin);
        }
        return null;
    }

    @DeleteMapping(path="/{id}")
    public boolean delete(@PathVariable String id) {
        Optional<admin_users> optionalAdmin = repo.findById(id);
        if(optionalAdmin.isEmpty()) {
            return false;
        }
        repo.deleteById(id);
        return true;
    }
}
