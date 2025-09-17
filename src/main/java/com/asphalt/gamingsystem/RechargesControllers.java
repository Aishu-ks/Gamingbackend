package com.asphalt.gamingsystem;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/recharges")
public class RechargesControllers {

    @Autowired
    private RechargesRepository repo;

    @PostMapping
    public Recharges create(@RequestBody Recharges recharge) {
        recharge.setId(null);
        Recharges savedRecharge = repo.save(recharge);
        return savedRecharge;
    }

    @GetMapping
    public List<Recharges> findAll() {
        return repo.findAll();
    }

    @GetMapping(path="/{id}")
    public Recharges findById(@PathVariable String id) {
        return repo.findById(id).orElse(null);
    }

    @PutMapping(path="/{id}")
    public Recharges update(@PathVariable String id, @RequestBody Recharges recharge) {
        Recharges oldRecharge = repo.findById(id).orElse(null);
        if (oldRecharge != null) {
            oldRecharge.setMemberId(recharge.getMemberId());
            oldRecharge.setAmount(recharge.getAmount());
            oldRecharge.setDateTime(recharge.getDateTime());
            return repo.save(oldRecharge);
        }
        return null;
    }

    @DeleteMapping(path="/{id}")
    public boolean delete(@PathVariable String id) {
        Optional<Recharges> optionalRecharge = repo.findById(id);
        if(optionalRecharge.isEmpty()) {
            return false;
        }
        repo.deleteById(id);
        return true;
    }
}
