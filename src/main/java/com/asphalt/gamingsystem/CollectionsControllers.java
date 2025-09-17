package com.asphalt.gamingsystem;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/collections")
public class CollectionsControllers {

    @Autowired
    private CollectionsRepository repo;

    @PostMapping
    public Collections create(@RequestBody Collections collections) {
        collections.setId(null);
        Collections savedCollections = repo.save(collections);
        return savedCollections;
    }

    @GetMapping
    public List<Collections> findAll() {
        return repo.findAll();
    }

    @GetMapping(path="/{id}")
    public Collections findById(@PathVariable String id) {
        return repo.findById(id).orElse(null);
    }

    @PutMapping(path="/{id}")
    public Collections update(@PathVariable String id, @RequestBody Collections collections) {
        Collections oldCollections = repo.findById(id).orElse(null);
        if (oldCollections != null) {
            oldCollections.setDate(collections.getDate());
            oldCollections.setAmount(collections.getAmount());
            return repo.save(oldCollections);
        }
        return null;
    }

    @DeleteMapping(path="/{id}")
    public boolean delete(@PathVariable String id) {
        Optional<Collections> optionalCollections = repo.findById(id);
        if(optionalCollections.isEmpty()) {
            return false;
        }
        repo.deleteById(id);
        return true;
    }
}
