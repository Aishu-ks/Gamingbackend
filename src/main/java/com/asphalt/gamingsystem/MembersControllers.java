package com.asphalt.gamingsystem;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/members")
public class MembersControllers {

    @Autowired
    private MembersRepository repo;

    @PostMapping
    public Members create(@RequestBody Members members) {
        members.setId(null);
        Members savedMembers = repo.save(members);
        return savedMembers;
    }

    @GetMapping
    public List<Members> findAll() {
        return repo.findAll();
    }

    @GetMapping(path="/{id}")
    public Members findById(@PathVariable String id) {
        return repo.findById(id).orElse(null);
    }

    @PutMapping(path="/{id}")
    public Members update(@PathVariable String id, @RequestBody Members members) {
        Members oldMembers = repo.findById(id).orElse(null);
        if (oldMembers != null) {
            oldMembers.setName(members.getName());
            oldMembers.setphone(members.getphone());
            oldMembers.setbalance(members.getbalance());
            return repo.save(oldMembers);
        }
        return null;
    }

    @DeleteMapping(path="/{id}")
    public boolean delete(@PathVariable String id) {
        Optional<Members> optionalMembers = repo.findById(id);
        if(optionalMembers.isEmpty()) {
            return false;
        }
        repo.deleteById(id);
        return true;
    }
}
