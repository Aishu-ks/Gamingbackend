package com.asphalt.gamingsystem;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/transactions")
public class TransactionsControllers {

    @Autowired
    private TransactionsRepository repo;

    @PostMapping
    public Transactions create(@RequestBody Transactions transaction) {
        transaction.setId(null);
        Transactions savedTransaction = repo.save(transaction);
        return savedTransaction;
    }

    @GetMapping
    public List<Transactions> findAll() {
        return repo.findAll();
    }

    @GetMapping(path="/{id}")
    public Transactions findById(@PathVariable String id) {
        return repo.findById(id).orElse(null);
    }

    @PutMapping(path="/{id}")
    public Transactions update(@PathVariable String id, @RequestBody Transactions transaction) {
        Transactions oldTransaction = repo.findById(id).orElse(null);
        if (oldTransaction != null) {
            oldTransaction.setMemberId(transaction.getMemberId());
            oldTransaction.setGameId(transaction.getGameId());
            oldTransaction.setAmount(transaction.getAmount());
            oldTransaction.setDateTime(transaction.getDateTime());
            return repo.save(oldTransaction);
        }
        return null;
    }

    @DeleteMapping(path="/{id}")
    public boolean delete(@PathVariable String id) {
        Optional<Transactions> optionalTransaction = repo.findById(id);
        if(optionalTransaction.isEmpty()) {
            return false;
        }
        repo.deleteById(id);
        return true;
    }
}
