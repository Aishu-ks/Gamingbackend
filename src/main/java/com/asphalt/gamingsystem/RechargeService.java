package com.asphalt.gamingsystem;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RechargeService {
    private static final Logger log = LoggerFactory.getLogger(RechargeService.class);

    @Autowired
    private RechargesRepository repo;

    public Recharges create(Recharges recharge) {
        log.info("Creating recharge for memberId: {}", recharge.getMemberId());
        recharge.setId(null); // let MongoDB assign ObjectId
        if (recharge.getDateTime() == null) {
            recharge.setDateTime(new Date()); // default now()
        }
        validate(recharge);
        return repo.save(recharge);
    }

    public List<Recharges> findAll() {
        log.info("Finding All Recharges");
        return repo.findAll();
    }

    public Recharges findById(String id) {
        log.info("Finding Recharge By id {}", id);
        Optional<Recharges> optionalRecharge = repo.findById(id);
        if (optionalRecharge.isEmpty()) {
            log.error("Attempted to find non-existing recharge id: {}", id);
            throw new ResourceNotFoundException("Recharge not found: " + id);
        }
        return optionalRecharge.get();
    }

    public Recharges update(String id, Recharges recharge) {
        Optional<Recharges> optionalRecharge = repo.findById(id);
        if (optionalRecharge.isEmpty()) {
            log.error("Attempted to update non-existing recharge id: {}", id);
            throw new ResourceNotFoundException("Recharge not found: " + id);
        }
        log.info("Updating Recharge By id {}", id);
        Recharges oldRecharge = optionalRecharge.get();
        oldRecharge.setMemberId(recharge.getMemberId());
        oldRecharge.setAmount(recharge.getAmount());
        oldRecharge.setDateTime(recharge.getDateTime() != null ? recharge.getDateTime() : oldRecharge.getDateTime());

        return repo.save(oldRecharge);
    }

    public boolean delete(String id) {
        Optional<Recharges> optionalRecharge = repo.findById(id);
        if (optionalRecharge.isEmpty()) {
            log.error("Attempted to delete non-existing recharge id: {}", id);
            throw new ResourceNotFoundException("Recharge not found: " + id);
        }
        log.info("Deleting Recharge By id {}", id);
        repo.deleteById(id);
        return true;
    }

    private void validate(Recharges recharge) {
        if (recharge.getAmount() == null || recharge.getAmount() <= 0) {
            log.error("Amount must be greater than zero");
            throw new BusinessException("Amount must be greater than zero");
        }
        if (recharge.getMemberId() == null) {
            log.error("MemberId cannot be null");
            throw new BusinessException("MemberId cannot be null");
        }
    }
}
